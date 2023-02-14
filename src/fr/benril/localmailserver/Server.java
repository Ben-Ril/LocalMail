package fr.benril.localmailserver;

import fr.benril.localmailserver.database.DataBase;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {
    private static Server instance;
    public static Server getInstance(){return instance;};
    private ServerSocket serverSocket;
    private List<Thread> clientList = new ArrayList<>();
    public List<Thread> getClientList() {return clientList;}
    public boolean addClient(Thread clientThread) {return clientList.add(clientThread);}
    public void removeClient(Thread clientThread) {clientList.remove(clientThread);}

    public Server(){
        instance = this;
        try {
            serverSocket = new ServerSocket(15935);
            DataBase db = new DataBase();
        }catch (IOException ioe){
            ioe.printStackTrace();
        }
    }

    public void acceptClient(){
        new Thread(() -> {
            while(!serverSocket.isClosed()){
                try {
                    Socket client = serverSocket.accept();

                    if(!client.getInetAddress().getHostAddress().equals("127.0.0.1")){
                        client.close();
                        continue;
                    }

                    Thread clientManagerThread = new Thread(new ClientManager(client));
                    clientManagerThread.start();
                    addClient(clientManagerThread);
                }catch (IOException ioe){
                    ioe.printStackTrace();
                }
            }
        }).start();
    }
}
