package fr.benril.localmailserver.request;

import fr.benril.localmailserver.database.DataBase;

import java.io.DataOutputStream;
import java.io.IOException;

public class Request {
    private DataBase db = DataBase.getInstance();
    private final RequestType type;
    private final String command;
    private final DataOutputStream writer;

    public Request(RequestAction action, RequestType type, String command, DataOutputStream writer){
        this.type = type;
        this.command = command;
        this.writer = writer;
        switch (action) {
            case GET:
                get();
                break;
            case CREATE:
                create();
                break;
            case DELETE:
                delete();
                break;
            case MODIFY:
                modify();
                break;
        }
    }

    private void get(){
        String[] infos = (command.contains(" ") ? command.split(" ") : null);
        String way;
        switch (type){
            case LANG:
                sendMessage(db.getLang());
                break;
            case MAIL:
                assert infos != null;
                String uuid = infos[0];
                sendMessage(db.getMail(uuid));
                break;
            case USER:
                assert infos != null;
                way = infos[0].toUpperCase();

                if(way.equals("NAME")){
                    if(infos.length != 3){
                        sendMessage("ERROR");
                        return;
                    }
                    sendMessage(db.getUserByName(infos[1], infos[2]));
                    return;
                }
                if(way.equals("UUID")){
                    if(infos.length != 2){
                        sendMessage("ERROR");
                        return;
                    }
                    sendMessage(db.getUserByUUID(infos[1]));
                    return;
                }

                sendMessage("ERROR");
                break;
            case GROUPS:
                sendMessage(db.getGroups());
                break;
            case MAILS:
                assert infos != null;
                if(infos.length != 2 || (!infos[0].equalsIgnoreCase("SENDER") && !infos[0].equalsIgnoreCase("RECEIVER"))){
                    sendMessage("ERROR");
                    return;
                }

                sendMessage(db.getMails(infos[1], (infos[0].equalsIgnoreCase("SENDER"))));
                break;
            case USERS:
                assert infos != null;
                if(infos.length != 1){
                    sendMessage("ERROR");
                    return;
                }
                sendMessage(db.getUsers(infos[0]));
                break;
            case DATABASE:
                sendMessage(db.getDbURL());
                break;
            default:
                sendMessage("ERROR");
                break;
        }
    }

    private void create() {
        String[] infos = command.split(" +<->+ ");
        if(infos.length == 0){
            sendMessage("ERROR");
            return;
        }
        switch (type){
            case USER:
                if(infos.length != 5){return;}
                db.createUser(infos[0], infos[1], infos[2], infos[3], infos[4].equalsIgnoreCase("true"));
                break;
            case GROUP:
                if(infos.length != 1){return;}
                db.createGroup(infos[0]);
                break;
            case MAIL:
                if(infos.length != 6){return;}
                db.createMail(infos[0], infos[1].split("<-->"), infos[2], infos[3], infos[4], infos[5].split("<-->"));
                break;
            default:
                sendMessage("ERROR");
                break;
        }
    }

    private void delete() {
        String[] infos = command.split(" ");
        if(infos.length != 1){return;}
        switch (type){
            case USER:
                db.deleteUser(infos[0]);
                break;
            case GROUP:
                db.deleteGroup(infos[0]);
                break;

        }
    }

    private void modify() {
        String[] infos = command.split(" ");
        if(infos.length != 1){return;}
        switch (type){
            case LANG:
                if(infos[0].length() != 2){return;}
                db.setLang(infos[0]);
                break;
            case USER:
                String[] userInfo = infos[0].split(" +<->+ ");
                if(userInfo.length != 6){return;}
                db.modifyUser(userInfo[0], userInfo[1], userInfo[2], userInfo[3], userInfo[4], userInfo[5].equalsIgnoreCase("true"));
                break;
            case DATABASE:

                break;
        }
    }

    private void sendMessage(String message){
        try {
            writer.writeUTF(message);
            writer.flush();
        }catch (IOException ignored){}
    }
}
