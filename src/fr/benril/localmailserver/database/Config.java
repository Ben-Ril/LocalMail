package fr.benril.localmailserver.database;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Config {
    private final DataBase db;

    public Config(){this.db = DataBase.getInstance();}

    public void setLang(String newLang){
        if(getLang() .equals(newLang)){return;}
        db.updateValue("config", "name", "lang", "val", newLang);
    }
    public String getLang(){
        ResultSet resultSet = db.executeQueryCond("config", "name", "lang");
        try {
            if(resultSet == null || !resultSet.next()){
                db.executeStatement("INSERT INTO config VALUES ('lang', 'en')");
                return "en";
            }

            return resultSet.getString("val");
        }catch (SQLException sqle){
            sqle.printStackTrace();
            return "ERROR";
        }
    }

    private boolean areDBInfoCorrect(String url, String user, String password){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(url, user, password);
            return true;
        }catch (ClassNotFoundException | RuntimeException | SQLException e) {
            return false;
        }
    }

    public void modifyDataBase(String url, String username, String password){
        try {
            if(!areDBInfoCorrect(url, username, password)){return;}
            File configDBFile = new File("./db");
            if(configDBFile.exists()){configDBFile.delete();}
            configDBFile.createNewFile();
            FileWriter writer = new FileWriter(configDBFile);
            writer.write(url + "\n" + username + "\n" + password);
            writer.flush();
            db.closeDBCon();
            new DataBase();
        }catch (IOException | SQLException ignored){}
    }
}
