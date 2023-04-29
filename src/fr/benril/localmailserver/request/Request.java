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
        switch (type){
            case LANG:
                this.client.sendMessage(new Config().getLang());
                break;
            case MAIL:
                String uuid = infos[0];
                this.client.sendMessage(new Mails().getMail(uuid));
                break;
            case USER:
                if(way.equals("NAME")){
                    if(infos.length != 3){
                        this.client.sendMessage("ERROR");
                        return;
                    }
                    String[] parts =  new Users().getUserByName(infos[1], infos[2]).split("//<->//");
                    for(String p : parts){this.client.sendMessage(p);}
                    return;
                }
                if(way.equals("UUID")){
                    if(infos.length != 2){
                        this.client.sendMessage("ERROR");
                        return;
                    }
                    String[] parts = new Users().getUserByUUID(infos[1]).split("//<->//");
                    for(String p : parts){this.client.sendMessage(p);}
                    return;
                }

                this.client.sendMessage("ERROR");
                break;
            case GROUPS:
                this.client.sendMessage(new Groups().getGroups());
                break;
            case MAILS:
                if(infos.length != 2 || (!infos[0].equalsIgnoreCase("SENDER") && !infos[0].equalsIgnoreCase("RECEIVER"))){
                    this.client.sendMessage("ERROR");
                    return;
                }

                this.client.sendMessage(new Mails().getMails(infos[1], (infos[0].equalsIgnoreCase("SENDER"))));
                break;
            case USERS:
                if(infos.length != 1 || !new Groups().getGroups().contains(infos[0])){
                    this.client.sendMessage("ERROR");
                    return;
                }
                String[] usersPart = new Users().getUsers(infos[0]).split("<-->");
                for(String user : usersPart){
                    for(String part : user.split("//<->//")){
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
            case GROUP:
                if(infos.length != 1){return;}
                new Groups().createGroup(infos[0]);
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
            case GROUP:
                new Groups().deleteGroup(infos[0]);
                break;

        }
    }

    private void modify() {
        String[] infos = (command.contains(" //<->// ") ? command.split(" //<->// ") : null);
        if(infos == null){return;}
        switch (type){
            case LANG:
                if(infos[0].length() != 2){return;}
                new Config().setLang(infos[0]);
                break;
            case USER:
                if(infos.length != 5){return;}
                new Users().modifyUser(infos[0], infos[1], infos[2], infos[3], infos[4]);
                break;
            case DATABASE:
                if(infos.length != 3){return;}
                new Config().modifyDataBase(infos[0], infos[1], infos[2]);
                break;
        }
    }
}
