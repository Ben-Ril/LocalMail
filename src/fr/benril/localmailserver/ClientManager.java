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
            if(client.isClosed()){
                Server.getInstance().removeClient(this);
                return;
            }

            DataOutputStream writer = new DataOutputStream(client.getOutputStream());
            DataInputStream reader = new DataInputStream(client.getInputStream());

            if(!DataBase.getInstance().isConnected()){
                writer.writeUTF("noDB\n");
                writer.flush();
                writer.close();
                client.close();
                return;
            }
            String message = reader.readUTF();
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
        }catch (IOException ignore){}
        run();
    }

    public boolean isConnected(){
        return !client.isClosed() && client.isConnected();
    }
}
