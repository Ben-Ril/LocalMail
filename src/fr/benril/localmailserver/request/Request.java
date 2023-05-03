package fr.benril.localmailserver.request;

import fr.benril.localmailserver.ClientManager;
import fr.benril.localmailserver.database.*;

public class Request {
    private final DataBase db = DataBase.getInstance();
    private final RequestType type;
    private final String command;
    private final ClientManager client;

    public Request(RequestAction action, RequestType type, String command, ClientManager client){
        this.type = type;
        this.command = command;
        this.client = client;
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
        String[] infos = (command.contains(" //<->// ") ? command.split(" //<->// ") : command.split(" "));
        String way = infos[0].toUpperCase();
        String[] parts;
        switch (type){
            case MAIL:
                String uuid = infos[0];
                parts = new Mails().getMail(uuid).split("//<->//");
                for(String p : parts){this.client.sendMessage(p);}
                break;
            case USER:
                if(way.equals("NAME")){
                    if(infos.length != 3){
                        this.client.sendMessage("ERROR");
                        return;
                    }
                    parts =  new Users().getUserByName(infos[1], infos[2]).split("//<->//");
                    for(String p : parts){this.client.sendMessage(p);}
                    return;
                }
                if(way.equals("UUID")){
                    if(infos.length != 2){
                        this.client.sendMessage("ERROR");
                        return;
                    }
                    parts = new Users().getUserByUUID(infos[1]).split("//<->//");
                    for(String p : parts){this.client.sendMessage(p);}
                    return;
                }

                this.client.sendMessage("ERROR");
                break;
            case MAILS:
                if(infos.length != 2 || (!infos[0].equalsIgnoreCase("SENDER") && !infos[0].equalsIgnoreCase("RECEIVER"))){
                    this.client.sendMessage("ERROR");
                    return;
                }
                String[] mailsPart = new Mails().getMails(infos[1], (infos[0].equalsIgnoreCase("SENDER"))).split("<-->");
                this.client.sendMessage(mailsPart[mailsPart.length-1]);
                for(int i = 0 ; i != mailsPart.length-1 ; i++){
                    for(String part : mailsPart[i].split("//<->//")){
                        this.client.sendMessage(part);
                    }
                }
                break;
            case DATABASE:
                this.client.sendMessage(db.getDbURL());
                break;
            default:
                this.client.sendMessage("ERROR");
                break;
        }
    }

    private void create() {
        String[] infos = (command.contains(" //<->// ") ? command.split(" //<->// ") : null);
        if(infos == null || infos.length == 0){
            this.client.sendMessage("ERROR");
            return;
        }
        switch (type){
            case USER:
                if(infos.length != 4){return;}
                new Users().createUser(infos[0], infos[1], infos[2], infos[3]);
                break;
            case MAIL:
                if(infos.length != 6){return;}
                this.client.sendMessage(new Mails().createMail(
                        infos[0],
                        (infos[1].contains("<-->") ? infos[1].split("<-->") : new String[]{infos[1]}),
                        infos[2],
                        infos[3],
                        infos[4],
                        (infos[5].contains("<-->") ? infos[5].split("<-->") : new String[]{infos[5]})
                ));
                break;
            default:
                this.client.sendMessage("ERROR");
                break;
        }
    }

    private void delete() {
        String[] infos = (command.contains(" //<->// ") ? command.split(" //<->// ") : null);
        if(infos == null || infos.length != 1){return;}
        switch (type){
            case USER:
                new Users().deleteUser(infos[0]);
                break;
        }
    }

    private void modify() {
        String[] infos = (command.contains(" //<->// ") ? command.split(" //<->// ") : null);
        if(infos == null){return;}
        switch (type){
            case USER:
                if(infos.length != 5){return;}
                new Users().modifyUser(infos[0], infos[1], infos[2], infos[3], infos[4]);
                break;
            case DATABASE:
                if(infos.length != 3){return;}
                DataBase db = DataBase.getInstance();
                if(db.isConnected()){db.closeDBCon();}
                db.modifyDB(infos[0], infos[1], infos[2]);
                break;
        }
    }
}
