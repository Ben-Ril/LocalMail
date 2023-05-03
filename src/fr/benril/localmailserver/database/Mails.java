package fr.benril.localmailserver.database;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Mails {
    private final DataBase db;

    public Mails(){this.db = DataBase.getInstance();}

    public String getMail(String uuid){
        ResultSet resultSet = db.executeQueryCond("mails", "uuid", uuid);
        StringBuilder inlineMailBuilder = new StringBuilder();
        try {
            if(resultSet == null || !resultSet.next()){return "ERROR";}

            inlineMailBuilder.append(resultSet.getString("uuid")).append("//<->//");
            inlineMailBuilder.append(resultSet.getString("senderUUID")).append("//<->//");
            inlineMailBuilder.append(resultSet.getString("receiversUUID")).append("//<->//");
            inlineMailBuilder.append(resultSet.getString("object")).append("//<->//");
            inlineMailBuilder.append(resultSet.getString("content")).append("//<->//");
            inlineMailBuilder.append(resultSet.getString("date")).append("//<->//");
            inlineMailBuilder.append(resultSet.getString("attachment")).append("//<->//");
            return inlineMailBuilder.toString();
        }catch (SQLException sqle){
            return "ERROR";
        }
    }

    public String getMails(String userUUID, boolean isSender){
        if(new Users().getUserByUUID(userUUID).equals("ERROR")){return "ERROR";}

        ResultSet resultSet = db.executeQueryCond(userUUID + "mails", "sended", isSender?"1":"0");
        StringBuilder allMails = new StringBuilder();
        try {
            if(resultSet == null || !resultSet.next()){return "ERROR";}

            int mailNumber = 0;

            while (resultSet.next()) {
                allMails.append(getMail(resultSet.getString("uuid"))).append("<-->");
                mailNumber++;
            }
            return mailNumber + "\n" + allMails;
        }catch (SQLException sqle){
            return "ERROR";
        }
    }

    public String createMail(String senderUUID, String[] receiversUUIDs, String object, String content, String date, String[] attachment){
        if(new Users().getUserByUUID(senderUUID).equals("ERROR")){return "ERROR";}

        String uuid = generateMailUUID();

        db.executeStatement("INSERT INTO " + senderUUID + "mails VALUES ('" + date + "', '" + uuid + "', 1)");

        StringBuilder receivers = new StringBuilder();
        for(String receiver : receiversUUIDs){
            if(new Users().getUserByUUID(receiver).equals("ERRROR")){continue;}
            receivers.append(receiver).append("<->");
            db.executeStatement("INSERT INTO " + receiver + "mails VALUES ('" + date + "', '" + uuid + "', 0)");
        }
        StringBuilder attachments = new StringBuilder();
        for(String att : attachment){
            attachments.append(att).append("<->");
        }
        db.executeStatement("INSERT INTO mails VALUES('" + uuid + "', '" + senderUUID + "', '" + receivers + "', '" + object + "', '" + content + "', '" + date + "', '" + attachments + "')");
        return uuid;
    }

    private String generateMailUUID(){
        String uuid = db.generateUUID();
        while(!getMail(uuid).equalsIgnoreCase("ERROR")){uuid = db.generateUUID();}
        return uuid;
    }
}
