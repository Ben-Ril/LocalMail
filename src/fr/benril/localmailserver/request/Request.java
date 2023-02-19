package fr.benril.localmailserver.request;

import fr.benril.localmailserver.database.*;

import java.io.DataOutputStream;
import java.io.IOException;

public class Request {
    private final DataBase db = DataBase.getInstance();
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
        String[] infos = (command.contains(" +<->+ ") ? command.split(" +<->+ ") : null);
        String way;
        switch (type){
            case LANG:
                sendMessage(new Config().getLang());
                break;
            case MAIL:
                assert infos != null;
                String uuid = infos[0];
                sendMessage(new Mails().getMail(uuid));
                break;
            case USER:
                assert infos != null;
                way = infos[0].toUpperCase();

                if(way.equals("NAME")){
                    if(infos.length != 3){
                        sendMessage("ERROR");
                        return;
                    }
                    sendMessage(new Users().getUserByName(infos[1], infos[2]));
                    return;
                }
                if(way.equals("UUID")){
                    if(infos.length != 2){
                        sendMessage("ERROR");
                        return;
                    }
                    sendMessage(new Users().getUserByUUID(infos[1]));
                    return;
                }

                sendMessage("ERROR");
                break;
            case GROUPS:
                sendMessage(new Groups().getGroups());
                break;
            case MAILS:
                assert infos != null;
                if(infos.length != 2 || (!infos[0].equalsIgnoreCase("SENDER") && !infos[0].equalsIgnoreCase("RECEIVER"))){
                    sendMessage("ERROR");
                    return;
                }

                sendMessage(new Mails().getMails(infos[1], (infos[0].equalsIgnoreCase("SENDER"))));
                break;
            case USERS:
                assert infos != null;
                if(infos.length != 1 || !new Groups().getGroups().contains(infos[0])){
                    sendMessage("ERROR");
                    return;
                }
                sendMessage(new Users().getUsers(infos[0]));
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
        String[] infos = (command.contains(" +<->+ ") ? command.split(" +<->+ ") : null);
        if(infos == null || infos.length == 0){
            sendMessage("ERROR");
            return;
        }
        switch (type){
            case USER:
                if(infos.length != 5){return;}
                new Users().createUser(infos[0], infos[1], infos[2], infos[3], infos[4].equalsIgnoreCase("true"));
                break;
            case GROUP:
                if(infos.length != 1){return;}
                new Groups().createGroup(infos[0]);
                break;
            case MAIL:
                if(infos.length != 6){return;}
                sendMessage(new Mails().createMail(
                        infos[0],
                        (infos[1].contains("<-->") ? infos[1].split("<-->") : new String[]{infos[1]}),
                        infos[2],
                        infos[3],
                        infos[4],
                        (infos[5].contains("<-->") ? infos[5].split("<-->") : new String[]{infos[5]})
                ));
                break;
            default:
                sendMessage("ERROR");
                break;
        }
    }

    private void delete() {
        String[] infos = (command.contains(" +<->+ ") ? command.split(" +<->+ ") : null);
        if(infos == null || infos.length != 1){return;}
        switch (type){
            case USER:
                new Users().deleteUser(infos[0]);
                break;
            case GROUP:
                new Groups().deleteGroup(infos[0]);
                break;

        }
    }

    private void modify() {
        String[] infos = (command.contains(" +<->+ ") ? command.split(" +<->+ ") : null);
        if(infos == null){return;}
        switch (type){
            case LANG:
                if(infos[0].length() != 2){return;}
                new Config().setLang(infos[0]);
                break;
            case USER:
                if(infos.length != 6){return;}
                new Users().modifyUser(infos[0], infos[1], infos[2], infos[3], infos[4], infos[5].equalsIgnoreCase("true"));
                break;
            case DATABASE:
                if(infos.length != 3){return;}
                new Config().modifyDataBase(infos[0], infos[1], infos[2]);
                break;
        }
    }

    private void sendMessage(String message){
        try {
            writer.writeUTF(message + "\n");
            writer.flush();
        }catch (IOException ignored){}
    }
}
