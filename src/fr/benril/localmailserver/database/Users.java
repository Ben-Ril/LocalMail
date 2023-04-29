package fr.benril.localmailserver.database;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Users {
    private final DataBase db;

    public Users(){this.db = DataBase.getInstance();}

    public String getUserByUUID(String uuid){
        ResultSet resultSet = db.executeQueryCond("users", "uuid", uuid);
        StringBuilder userInlineBuilder = new StringBuilder();
        try{
            if(resultSet == null || !resultSet.next()){return "ERROR";}

            userInlineBuilder.append(resultSet.getString("uuid")).append("//<->//");
            userInlineBuilder.append(resultSet.getString("name")).append("//<->//");
            userInlineBuilder.append(resultSet.getString("firstname")).append("//<->//");
            userInlineBuilder.append(resultSet.getString("password")).append("//<->//");
            userInlineBuilder.append(resultSet.getString("grp"));
            return userInlineBuilder.toString();
        }catch (SQLException sqle){
            return "ERROR";
        }
    }

    public String getUserByName(String name, String firstName){
        ResultSet resultSet = db.executeQueryCond("users", "name", name);
        try{
            if(resultSet == null){return "ERROR";}
            while(resultSet.next()){
                if(resultSet.getString("firstname").equalsIgnoreCase(firstName)){
                    return getUserByUUID(resultSet.getString("uuid"));
                }
            }
            return "ERROR";
        }catch (SQLException sqle){
            sqle.printStackTrace();
            return "ERROR";
        }
    }

    public void createUser(String name, String firstname, String password, String group){
        String uuid = generateUserUUID();
        db.executeStatement("INSERT INTO users VALUES ('" + uuid + "', '" + name + "', '" + firstname + "', '" + password + "', '" + group + "', 1)");
        db.executeStatement("CREATE TABLE " + uuid + "Mails (date TEXT, mailUUID CHAR(9), sended BOOL)");
    }

    public void deleteUser(String uuid){
        if(getUserByUUID(uuid).equals("ERROR")){return;}
        db.executeStatement("DELETE FROM users WHERE uuid='" + uuid + "'");
        db.executeStatement("DROP TABLE " + uuid + "Mails");
    }

    public void modifyUser(String uuid, String name, String firstName, String password, String group){
        if(getUserByUUID(uuid).equalsIgnoreCase("ERROR")){return;}
        db.executeStatement("UPDATE users SET name='" + name  + "', fistname='" + firstName + "', password='" + password + "', grp='" + group + "'");
    }

    private String generateUserUUID(){
        String uuid = db.generateUUID();
        while(!getUserByUUID(uuid).equalsIgnoreCase("ERROR")){uuid = db.generateUUID();}
        return uuid;
    }
}
