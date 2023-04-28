package fr.benril.localmailserver;

public class Main {

    public static void main(String[] args){
        Server server = new Server();
        server.acceptClient();
        server.verifyConnectedClients();
    }
}
