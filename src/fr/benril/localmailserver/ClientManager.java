package fr.benril.localmailserver;

import fr.benril.localmailserver.database.DataBase;
import fr.benril.localmailserver.request.Request;
import fr.benril.localmailserver.request.RequestAction;
import fr.benril.localmailserver.request.RequestType;

import java.io.*;
import java.net.Socket;

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
                client.close();
                Server.getInstance().removeClient(this);
                Thread.currentThread().stop();
                return;
            }

            BufferedReader reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
            writer = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));

            String message;
            int i = 0;
            while((message = reader.readLine()) == null){i++;}
            dbVerif(message);

            if(message.equalsIgnoreCase("RECONNECT")){
                if(DataBase.getInstance().isConnected()){DataBase.getInstance().closeDBCon();}
                new DataBase();
            }else if(message.equalsIgnoreCase("IS DB CONNECTED")){
                String response = (DataBase.getInstance().isConnected() ? "YES" : "NO");
                sendMessage(response);
            }else if(message.equalsIgnoreCase("CLOSE")) {
                client.close();
                Server.getInstance().removeClient(this);
                Thread.currentThread().stop();
                return;
            }else{
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
        }catch (IOException ignored){Server.getInstance().removeClient(this);return;}
        run();
    }

    private void dbVerif(String message) throws IOException {
        if(!DataBase.getInstance().isConnected()){
            if(message.length() != 0){
                if(message.equalsIgnoreCase("RECONNECT")){
                    new DataBase();
                }
            }
        }
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
