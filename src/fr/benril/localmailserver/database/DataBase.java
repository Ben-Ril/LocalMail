package fr.benril.localmailserver.database;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.Random;
import java.util.Scanner;

public class DataBase {
    private String dbURL = null;
    public String getDbURL() {return dbURL;}

    private static DataBase instance;
    public static DataBase getInstance() {return instance;}

    private Connection con;

    public DataBase(){
        instance = this;
        try{
            File configDBFile = new File("./config");
            if(!configDBFile.exists()){
                System.out.println("The installation of LocalMail is incomplete");
                System.exit(0);
            }
            Scanner scan = new Scanner(configDBFile);
            String dbUser = null;
            String dbPassword = "";
            while(scan.hasNextLine()){
                String line = scan.nextLine();
                if(line.startsWith("url=")){this.dbURL = line.replace("url=", "");}
                if(line.startsWith("dbusername=")){dbUser = line.replace("dbusername=", "");}
                if(line.startsWith("dbpassword=")){dbPassword = line.replace("dbpassword=", "");}
            }

            if(dbUser == null || this.dbURL == null || dbUser.equals("") || this.dbURL.equals("")){
                System.out.println("The installation of LocalMail is invalid");
            }

            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(dbURL, dbUser, dbPassword);
            System.out.println("Connected to the DB");
            init();
        }catch (SQLException | ClassNotFoundException | FileNotFoundException ignored){}
    }

    private void init() throws SQLException {
        String request = "CREATE TABLE IF NOT EXISTS TABLEINFO;";
        String userTable = "users (uuid CHAR(9), name TEXT, firstname TEXT, password TEXT, grp TEXT, fistConnection BOOL)";
        String mailTable = "mails (uuid CHAR(9), senderUUID CHAR(9), receiversUUID TEXT, object TEXT, content TEXT, date TEXT, attachment TEXT)";

        executeStatement(request.replace("TABLEINFO", userTable));
        executeStatement(request.replace("TABLEINFO", mailTable));
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
            return statement.executeQuery("SELECT * FROM " + table + ";");
        }catch (SQLException sqle){
            sqle.printStackTrace();
            return null;
        }
    }

    public ResultSet executeQueryCond(String table, String column, String value){
        try{
            Statement statement = con.createStatement();
            return statement.executeQuery("SELECT * FROM " + table + " WHERE " + column + "='" + value + "';");
        }catch (SQLException sqle){
            sqle.printStackTrace();
            return null;
        }
    }

    public void updateValue(String table, String keyCond, String valueCond, String keyModify, String valueModify){
        executeStatement("UPDATE " + table + " SET " + keyModify + "='" + valueModify + "' WHERE " + keyCond + "='" + valueCond + "';");
    }

    public String generateUUID(){
        StringBuilder uuid = new StringBuilder();
        for(int i = 0 ; i < 9 ; i++){
            uuid.append(new Random().nextInt(10));
        }
        return uuid.toString();
    }

    public boolean isConnected() {
        try{return !con.isClosed();}catch (SQLException ignored){return false;}
    }
    public void closeDBCon() {try{con.close();}catch (SQLException ignored){}}
}
