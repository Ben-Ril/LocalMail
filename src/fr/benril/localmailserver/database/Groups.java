package fr.benril.localmailserver.database;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Groups {
    private final DataBase db;

    public Groups(){
        this.db = DataBase.getInstance();
    }

    public String getGroups(){
        ResultSet resultSet = db.executeQuery("grps");
        StringBuilder builder = new StringBuilder();

        try{
            if(resultSet == null || !resultSet.next()){return "ERROR";}

            while(resultSet.next()){
                String groupName = resultSet.getString("name");
                builder.append(groupName).append(" ").append(new Users().getUsers(groupName).split("\n").length).append(" +<->+ ");
            }
            String allGroups = builder.toString();
            return allGroups.substring(0, allGroups.length()-5);
        }catch (SQLException sqle){
            return "ERROR";
        }
    }

    public void createGroup(String name){
        db.executeStatement("INSERT INTO grps VALUES ('" + name + "')");
    }

    public void deleteGroup(String groupName){
        if(!getGroups().contains(groupName)){return;}
        db.executeStatement("DELETE FROM grps WHERE name='" + groupName + "'");
        db.executeStatement("UPDATE users SET grp='none' WHERE grp='" + groupName + "'");
    }
}
