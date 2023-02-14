package fr.benril.localmailserver.request;

import fr.benril.localmailserver.database.DataBase;

import java.io.DataOutputStream;
import java.io.IOException;

public class Request {
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
        DataBase db = DataBase.getInstance();
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
        }
    }

    private void create() {
    }

    private void delete() {
    }

    private void modify() {
    }

    private void sendMessage(String message){
        try {
            writer.writeUTF(message);
            writer.flush();
        }catch (IOException ioe){
            return;
        }
    }
}
