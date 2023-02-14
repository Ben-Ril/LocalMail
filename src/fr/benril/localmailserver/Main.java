package fr.benril.localmailserver;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Main {

    public static void main(String[] args){
        Server server = new Server();
        server.acceptClient();

        try {
            Socket client = new Socket("localhost", 15935);
            System.out.println("connecté");

            DataInputStream in = new DataInputStream(client.getInputStream());
            DataOutputStream out = new DataOutputStream(client.getOutputStream());
            out.writeUTF("GET LANG");
            out.flush();

            while(true){
                try{
                    String read = in.readUTF();
                    System.out.println(read);
                }catch (IOException ioe){

                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
