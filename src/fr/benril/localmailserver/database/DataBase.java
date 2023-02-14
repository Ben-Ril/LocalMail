package fr.benril.localmailserver.database;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.util.Scanner;

public class DataBase {
    private String dbURL;
    public String getDbURL() {return dbURL;}

    private String dbUser;
    private String dbPassword;
    private static DataBase instance;
    public static DataBase getInstance() {return instance;}

    private Connection con;
    private boolean connected;

    public DataBase(){
        try{
            File configDBFile = new File("./db");
            if(!configDBFile.exists()){
                try {
                    configDBFile.createNewFile();
                    FileWriter writer = new FileWriter(configDBFile);
                    writer.append("DataBase URL\n").append("DataBase Username\n").append("DataBase Password");
                    writer.flush();
                }catch (IOException ioe){
                    ioe.printStackTrace();
                }
            }
            Scanner scan = new Scanner(configDBFile);
            this.dbURL = scan.nextLine();
            this.dbUser = scan.nextLine();
            this.dbPassword = scan.nextLine();

            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(dbURL, dbUser, dbPassword);
            System.out.println("Connecté DB");
            connected = init();
            instance = (isConnected() ? this : null);
        }catch (SQLException sqle){
            connected = false;
            instance = null;
            sqle.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean init(){
        String request = "CREATE TABLE IF NOT EXISTS TABLEINFO;";
        String configTable = "config (name TEXT, val TEXT)";
        String userTable = "users (uuid CHAR(9), name TEXT, firstname TEXT, password TEXT, grp TEXT, fistConnection BOOL, admin BOOL)";
        String mailTable = "mails (uuid CHAR(9), senderUUID CHAR(9), receiversUUID TEXT, object TEXT, content TEXT, date TEXT, attachment TEXT)";
        String groupTable = "grps (name TEXT)";

        boolean allGood = executeStatement(request.replace("TABLEINFO", configTable));
        allGood = (allGood && executeStatement(request.replace("TABLEINFO", userTable)));
        allGood = (allGood && executeStatement(request.replace("TABLEINFO", mailTable)));
        allGood = (allGood && executeStatement(request.replace("TABLEINFO", groupTable)));
        return allGood;
    }

    private boolean executeStatement(String toExecute){
        try {
            con.createStatement().executeUpdate(toExecute);
            return true;
        }catch (SQLException sqle){
            sqle.printStackTrace();
            return false;
        }
    }

    private ResultSet executeQuery(String table){
        try{
            Statement statement = con.createStatement();
            return statement.executeQuery("SELECT * FROM " + table);
        }catch (SQLException sqle){
            sqle.printStackTrace();
            return null;
        }
    }

    private ResultSet executeQueryCond(String table, String column, String value){
        try{
            Statement statement = con.createStatement();
            return statement.executeQuery("SELECT * FROM " + table + " WHERE " + column + "='" + value + "'");
        }catch (SQLException sqle){
            sqle.printStackTrace();
            return null;
        }
    }

    private void updateValue(String table, String keyCond, String valueCond, String keyModify, String valueModify){
        executeStatement("UPDATE " + table + " SET " + keyModify + "='" + valueModify + "' WHERE " + keyCond + "='" + valueCond + "'");
    }

    public void setLang(String newLang){
        if(getLang() .equals(newLang)){return;}
        updateValue("config", "name", "lang", "val", newLang);
    }
    public String getLang(){
        ResultSet resultSet = executeQueryCond("config", "name", "lang");
        try {
            if(resultSet == null || !resultSet.next()){
                executeStatement("INSERT INTO config VALUES ('lang', 'en')");
                return "en";
            }

            return resultSet.getString("val");
        }catch (SQLException sqle){
            sqle.printStackTrace();
            return "ERROR";
        }
    }

    public String getMail(String uuid){
        ResultSet resultSet = executeQueryCond("mails", "uuid", uuid);
        StringBuilder inlineMailBuilder = new StringBuilder();
        try {
            inlineMailBuilder.append(resultSet.getString("uuid\n"));
            inlineMailBuilder.append(resultSet.getString("receivers\n"));
            inlineMailBuilder.append(resultSet.getString("object\n"));
            inlineMailBuilder.append(resultSet.getString("content\n"));
            inlineMailBuilder.append(resultSet.getString("date\n"));
            inlineMailBuilder.append(resultSet.getString("attachment"));
            return inlineMailBuilder.toString();
        }catch (SQLException sqle){
            return "ERROR";
        }
    }

    public String getUserByUUID(String uuid){
        ResultSet resultSet = executeQueryCond("users", "uuid", uuid);
        StringBuilder userInlineBuilder = new StringBuilder();
        try{
            userInlineBuilder.append(resultSet.getString("uuid\n"));
            userInlineBuilder.append(resultSet.getString("name\n"));
            userInlineBuilder.append(resultSet.getString("firstname\n"));
            userInlineBuilder.append(resultSet.getString("grps\n"));
            userInlineBuilder.append(resultSet.getBoolean("admin"));
            return userInlineBuilder.toString();
        }catch (SQLException sqle){
            return "ERROR";
        }
    }

    public String getUserByName(String name, String firstName){
        ResultSet resultSet = executeQueryCond("users", "name", name);
        try{
            assert resultSet != null;
            while(resultSet.next()){
                if(resultSet.getString("firstname").equals(firstName)){
                    return getUserByUUID(resultSet.getString("uuid"));
                }
            }
            return "ERROR";
        }catch (SQLException sqle){
            return "ERROR";
        }
    }

    public String getGroups(){
        ResultSet resultSet = executeQuery("grps");
        StringBuilder builder = new StringBuilder();
        assert resultSet != null;
        try{
            while(resultSet.next()){
                builder.append(resultSet.getString("name")).append("\n");
            }
            String allGroups = builder.toString();
            return allGroups.substring(0, allGroups.length()-5);
        }catch (SQLException sqle){
            return "ERROR";
        }
    }

    public String getMails(String userUUID, boolean isSended){
        if(getUserByUUID(userUUID).equals("ERROR")){return "ERROR";}

        ResultSet resultSet = executeQueryCond(userUUID + "Mails", "isSended", String.valueOf(isSended));
        StringBuilder allMails = new StringBuilder();
        assert resultSet != null;
        try {
            while (resultSet.next()) {
                allMails.append(getMail(resultSet.getString("uuid"))).append("\n");
            }
            return allMails.toString();
        }catch (SQLException sqle){
            return "ERROR";
        }
    }

    public String getUsers(String group){
        if(!getGroups().contains(group)){
            return "ERROR";
        }
        ResultSet resultSet = executeQueryCond("users", "grp", group);
        StringBuilder allUsers = new StringBuilder();
        assert resultSet != null;
        try{
            while (resultSet.next()){
                allUsers.append(getUserByUUID(resultSet.getString("uuid"))).append("\n");
            }
            return allUsers.toString();
        }catch (SQLException sqle){
            return "ERROR";
        }
    }

    public boolean isConnected() {return connected;}
}
