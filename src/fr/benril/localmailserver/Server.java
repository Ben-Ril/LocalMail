package fr.benril.localmailserver;

import fr.benril.localmailserver.database.DataBase;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

public class Server {
    private static Server instance;
    public static Server getInstance(){return instance;}
    private ServerSocket serverSocket;
    private final HashMap<ClientManager, Thread> clientList = new HashMap<>();
    public HashMap<ClientManager, Thread> getClientList() {return clientList;}
    public void addClient(ClientManager clientManager, Thread clientThread) {clientList.put(clientManager, clientThread);}
    public void removeClient(ClientManager clientManager) {clientList.remove(clientManager);}

    public Server(){
        instance = this;
        new DataBase();
        try {
            serverSocket = new ServerSocket(15935);
        }catch (IOException ioe){
            ioe.printStackTrace();
        }
    }

    public void acceptClient(){
        new Thread(() -> {
            try {
                Socket client = serverSocket.accept();

                if(!client.getInetAddress().getHostAddress().equals("127.0.0.1")){
                    client.close();
                    return;
                }

                ClientManager clientManager = new ClientManager(client);
                Thread clientManagerThread = new Thread(clientManager);
                clientManagerThread.start();
                addClient(clientManager, clientManagerThread);
            }catch (IOException ioe){
                ioe.printStackTrace();
            }
        }).start();
    }

    public void verifyConnectedClients(){
        new Thread(() -> {
            for(ClientManager clientManager : getClientList().keySet()){
                if(!clientManager.isConnected()){
                    getClientList().get(clientManager).stop();
                    getClientList().remove(clientManager);
                }
            }
        });
    }
}
