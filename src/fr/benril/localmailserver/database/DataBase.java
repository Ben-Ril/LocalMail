package fr.benril.localmailserver.database;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.util.Random;
import java.util.Scanner;

public class DataBase {
    private String dbURL;
    public String getDbURL() {return dbURL;}

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
            String dbUser = scan.nextLine();
            String dbPassword = scan.nextLine();

            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(dbURL, dbUser, dbPassword);
            System.out.println("Connecté DB");
            connected = init();
            instance = (isConnected() ? this : null);
        }catch (SQLException sqle){
            connected = false;
            instance = null;
            sqle.printStackTrace();
        } catch (ClassNotFoundException | FileNotFoundException e) {
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

    public static boolean areDBInfoCorrect(String url, String user, String password){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(url, user, password);
            return true;
        }catch (ClassNotFoundException | RuntimeException | SQLException e) {
            return false;
        }
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
            if(resultSet == null || !resultSet.next()){return "ERROR";}

            inlineMailBuilder.append(resultSet.getString("uuid\n"));
            inlineMailBuilder.append(resultSet.getString("senderUUID"));
            inlineMailBuilder.append(resultSet.getString("receiversUUID\n"));
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
            if(resultSet == null || !resultSet.next()){return "ERROR";}

            userInlineBuilder.append(resultSet.getString("uuid")).append("\n");
            userInlineBuilder.append(resultSet.getString("name")).append("\n");
            userInlineBuilder.append(resultSet.getString("firstname")).append("\n");
            userInlineBuilder.append(resultSet.getString("password")).append("\n");
            userInlineBuilder.append(resultSet.getString("grps")).append("\n");
            userInlineBuilder.append(resultSet.getBoolean("admin"));
            return userInlineBuilder.toString();
        }catch (SQLException sqle){
            return "ERROR";
        }
    }

    public String getUserByName(String name, String firstName){
        ResultSet resultSet = executeQueryCond("users", "name", name);
        try{
            if(resultSet == null || !resultSet.next()){return "ERROR";}

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

        try{
            if(resultSet == null || !resultSet.next()){return "ERROR";}

            while(resultSet.next()){
                String groupName = resultSet.getString("name");
                builder.append(groupName).append(" ").append(getUsers(groupName).split("\n").length).append(" +<->+ ");
            }
            String allGroups = builder.toString();
            return allGroups.substring(0, allGroups.length()-5);
        }catch (SQLException sqle){
            return "ERROR";
        }
    }

    public String getMails(String userUUID, boolean isSended){
        if(getUserByUUID(userUUID).equals("ERROR")){return "ERROR";}

        ResultSet resultSet = executeQueryCond(userUUID + "Mails", "isSended", isSended?"1":"0");
        StringBuilder allMails = new StringBuilder();
        try {
            if(resultSet == null || !resultSet.next()){return "ERROR";}

            int mailNumber = 0;

            while (resultSet.next()) {
                allMails.append(getMail(resultSet.getString("uuid"))).append("\n");
                mailNumber++;
            }
            return mailNumber + "\n" + allMails;
        }catch (SQLException sqle){
            return "ERROR";
        }
    }

    public String getUsers(String group){
        ResultSet resultSet = executeQueryCond("users", "grp", group);
        StringBuilder allUsers = new StringBuilder();
        try{
            if(resultSet == null || !resultSet.next()){return "ERROR";}

            while (resultSet.next()){
                allUsers.append(getUserByUUID(resultSet.getString("uuid"))).append("\n");
            }
            return allUsers.toString();
        }catch (SQLException sqle){
            return "ERROR";
        }
    }

    public void createUser(String name, String firstname, String password, String group, boolean isAdmin){
        password = new Hasher().hash(password);
        String uuid = generateUserUUID();
        executeStatement("INSERT INTO users VALUES ('" + uuid + "', '" + name + "', '" + firstname + "', '" + password + "', '" + group + "', 1, " + (isAdmin?"1":"0") + ")");
        executeStatement("CREATE TABLE " + uuid + "Mails (date TEXT, mailUUID CHAR(9), sended BOOL)");
    }

    public void createGroup(String name){
        executeStatement("INSERT INTO grps VALUES ('" + name + "')");
    }

    public String createMail(String senderUUID, String[] receiversUUIDs, String object, String content, String date, String[] attachment){
        if(getUserByUUID(senderUUID).equals("ERROR")){return "ERROR";}

        String uuid = generateMailUUID();

        executeStatement("INSERT INTO " + senderUUID + "Mails VALUES ('" + date + "', '" + uuid + "', 1)");

        StringBuilder receivers = new StringBuilder();
        for(String receiver : receiversUUIDs){
            if(getUserByUUID(receiver).equals("ERRROR")){continue;}
            receivers.append(receiver).append("<->");
            executeStatement("INSERT INTO " + receiver + "Mails VALUES ('" + date + "', '" + uuid + "', 0)");
        }
        StringBuilder attachments = new StringBuilder();
        for(String att : attachment){
            attachments.append(att).append("<->");
        }
        executeStatement("INSERT INTO mails VALUES('" + uuid + "', '" + senderUUID + "', '" + receivers + "', '" + object + "', '" + content + "', '" + date + "', '" + attachments + "')");
        return uuid;
    }

    public void deleteUser(String uuid){
        if(getUserByUUID(uuid).equals("ERROR")){return;}
        executeStatement("DELETE FROM users WHERE uuid='" + uuid + "'");
        executeStatement("DROP TABLE " + uuid + "Mails");
    }

    public void deleteGroup(String groupName){
        if(!getGroups().contains(groupName)){return;}
        executeStatement("DELETE FROM grps WHERE name='" + groupName + "'");
        executeStatement("UPDATE users SET grp='none' WHERE grp='" + groupName + "'");
    }

    public void modifyUser(String uuid, String name, String firstName, String password, String group, boolean isAdmin){
        if(getUserByUUID(uuid).equalsIgnoreCase("ERROR")){return;}
        executeStatement("UPDATE users SET name='" + name  + "', fistname='" + firstName + "', password='" + new Hasher().hash(password) + "', grp='" + group + "', admin=" + (isAdmin?1:0));
    }

    public void modifyDataBase(String url, String username, String password){
        try {
            File configDBFile = new File("./db");
            if(configDBFile.exists()){configDBFile.delete();}
            configDBFile.createNewFile();
            FileWriter writer = new FileWriter(configDBFile);
            writer.write(url + "\n" + username + "\n" + password);
            writer.flush();
            con.close();
            new DataBase();
        }catch (IOException | SQLException ignored){}
    }

    private String generateUserUUID(){
        String uuid = generateUUID();
        while(!getUserByUUID(uuid).equalsIgnoreCase("ERROR")){uuid = generateUUID();}
        return uuid;
    }

    private String generateMailUUID(){
        String uuid = generateUUID();
        while(!getMail(uuid).equalsIgnoreCase("ERROR")){uuid = generateUUID();}
        return uuid;
    }

    private String generateUUID(){
        StringBuilder uuid = new StringBuilder();
        for(int i = 0 ; i < 9 ; i++){
            uuid.append(new Random().nextInt(10));
        }
        return uuid.toString();
    }

    public boolean isConnected() {return connected;}
}
