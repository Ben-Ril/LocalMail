package fr.benril.localmailserver;

import fr.benril.localmailserver.database.DataBase;
import fr.benril.localmailserver.request.Request;
import fr.benril.localmailserver.request.RequestAction;
import fr.benril.localmailserver.request.RequestType;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class ClientManager implements Runnable {
    private final Socket client;
    private BufferedWriter writer;

    public ClientManager(Socket client){
        this.client = client;
    }

    @Override
    public void run() {
        try{
            if(!isConnected()){
                Server.getInstance().removeClient(this);
                return;
            }

            BufferedReader reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
            writer = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));

            String message;
            int i = 0;
            while((message = reader.readLine()) == null){i++;}
            dbVerif(message);
            System.out.println(message);

            if(message.equalsIgnoreCase("IS DB CONNECTED")){
                String response = (DataBase.getInstance().isConnected() ? "YES" : "NO");
                sendMessage(response);
            }else {
                if(DataBase.getInstance().isConnected()){
                    String[] request = message.split(" ");
                    if(request.length > 1){
                        String action = request[0].toUpperCase();
                        String type = request[1].toUpperCase();
                        String info = (request.length == 2 ? "" : message.replace(action + " " + type + " ", ""));
                        new Request(RequestAction.valueOf(action), RequestType.valueOf(type), info, this);
                    }
                }
            }

            /*if(!reader.readUTF().equals("")) {
                boolean isDbGood = dbVerif(writer, reader);
                System.out.println(isDbGood);
                if (isDbGood) {
                    System.out.println(":3");
                    String message = reader.readUTF();

                    if (message.equalsIgnoreCase("IS DB CONNECTED")) {
                        writer.writeUTF((DataBase.getInstance().isConnected() ? "YES" : "NO"));
                        writer.flush();
                    } else if (message.equalsIgnoreCase("RECONNECT")) {
                        new DataBase();
                    } else {
                        String[] request = message.split(" ");

                        if (request.length > 1) {
                            String action = request[0].toUpperCase();
                            String type = request[1].toUpperCase();
                            String info;
                            if (request.length == 2) {
                                info = "";
                            } else {
                                info = message.replace(type + " " + action + " ", "");
                            }
                            new Request(RequestAction.valueOf(action), RequestType.valueOf(type), info, writer);
                        }
                    }
                }
            }*/
        }catch (IOException ignored){Server.getInstance().removeClient(this);return;}
        run();
    }

    private boolean dbVerif(String message) throws IOException {
        if(!DataBase.getInstance().isConnected()){
            if(message.length() != 0){
                if(message.toUpperCase().startsWith("MODIFY DATABASE") && message.split(" ").length == 5){
                    String[] explode = message.split(" ");
                    String info = message.replace(explode[0] + " ", "").replace(explode[1] + " ", "");
                    new Request(RequestAction.MODIFY, RequestType.DATABASE, info, this);
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

    public void sendMessage(String message) {
        try {
            writer.write(message);
            writer.flush();
        }catch (IOException ignored){}
    }

    public boolean isConnected(){
        return !client.isClosed() && client.isConnected();
    }
}
