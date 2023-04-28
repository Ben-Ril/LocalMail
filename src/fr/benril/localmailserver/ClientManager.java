package fr.benril.localmailserver;

import fr.benril.localmailserver.database.DataBase;
import fr.benril.localmailserver.request.Request;
import fr.benril.localmailserver.request.RequestAction;
import fr.benril.localmailserver.request.RequestType;

import java.io.*;
import java.net.Socket;

public class ClientManager implements Runnable {
    private final Socket client;

    public ClientManager(Socket client){
        this.client = client;
    }

    @Override
    public void run() {
        try{
            if(isConnected()){
                Server.getInstance().removeClient(this);
                return;
            }

            DataOutputStream writer = new DataOutputStream(client.getOutputStream());
            DataInputStream reader = new DataInputStream(client.getInputStream());

            boolean isDbGood = dbVerif(writer, reader);
            if(isDbGood){
                String message = reader.readUTF();

                if(message.equalsIgnoreCase("IS DB CONNECTED")){
                    writer.writeUTF((DataBase.getInstance().isConnected() ? "YES" : "NO"));
                    writer.flush();
                }else if(message.equalsIgnoreCase("RECONNECT")){
                    new DataBase();
                }else{
                    String[] request = message.split(" ");

                    if(request.length < 2){run();}

                    String action = request[0].toUpperCase();
                    String type = request[1].toUpperCase();
                    String info;
                    if(request.length == 2){
                        info = "";
                    }else{
                        info = message.replace(type + " " + action + " ", "");
                    }

                    new Request(RequestAction.valueOf(action), RequestType.valueOf(type), info, writer);
                }
            }
        }catch (IOException ignore){}
        run();
    }

    private boolean dbVerif(DataOutputStream writer, DataInputStream reader) throws IOException {
        if(!DataBase.getInstance().isConnected()){
            String message = reader.readUTF();
            if(message.length() != 0){
                if(message.toUpperCase().startsWith("MODIFY DATABASE") && message.split(" ").length == 5){
                    String[] explode = message.split(" ");
                    String info = message.replace(explode[0] + " ", "").replace(explode[1] + " ", "");
                    new Request(RequestAction.MODIFY, RequestType.DATABASE, info, writer);
                    return DataBase.getInstance().isConnected();
                }
                if(message.equalsIgnoreCase("RECONNECT")){
                    new DataBase();
                    return DataBase.getInstance().isConnected();
                }
                return false;
            }
        }
        return true;
    }

    public boolean isConnected(){
        return !client.isClosed() && client.isConnected();
    }
}
