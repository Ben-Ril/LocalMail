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
        String userTable = "users (uuid CHAR(9), name TEXT, firstname TEXT, password TEXT, grp TEXT, fistConnection BOOL)";
        String mailTable = "mails (uuid CHAR(9), senderUUID CHAR(9), receiversUUID TEXT, object TEXT, content TEXT, date TEXT, attachment TEXT)";
        String groupTable = "grps (name TEXT)";

        boolean allGood = executeStatement(request.replace("TABLEINFO", configTable));
        allGood = (allGood && executeStatement(request.replace("TABLEINFO", userTable)));
        allGood = (allGood && executeStatement(request.replace("TABLEINFO", mailTable)));
        allGood = (allGood && executeStatement(request.replace("TABLEINFO", groupTable)));
        return allGood;
    }

    public boolean executeStatement(String toExecute){
        try {
            con.createStatement().executeUpdate(toExecute);
            return true;
        }catch (SQLException sqle){
            sqle.printStackTrace();
            return false;
        }
    }

    public ResultSet executeQuery(String table){
        try{
            Statement statement = con.createStatement();
            return statement.executeQuery("SELECT * FROM " + table);
        }catch (SQLException sqle){
            sqle.printStackTrace();
            return null;
        }
    }

    public ResultSet executeQueryCond(String table, String column, String value){
        try{
            Statement statement = con.createStatement();
            return statement.executeQuery("SELECT * FROM " + table + " WHERE " + column + "='" + value + "'");
        }catch (SQLException sqle){
            sqle.printStackTrace();
            return null;
        }
    }

    public void updateValue(String table, String keyCond, String valueCond, String keyModify, String valueModify){
        executeStatement("UPDATE " + table + " SET " + keyModify + "='" + valueModify + "' WHERE " + keyCond + "='" + valueCond + "'");
    }

    public String generateUUID(){
        StringBuilder uuid = new StringBuilder();
        for(int i = 0 ; i < 9 ; i++){
            uuid.append(new Random().nextInt(10));
        }
        return uuid.toString();
    }

    public boolean isConnected() {return connected;}
    public void closeDBCon() throws SQLException {con.close();}
}
