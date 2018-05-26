package it.polimi.se2018.Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Client {

    public static void main( String[] args ) throws IOException {
        BufferedReader b = null;
        String in = "";
        String ip = "";
        int port = 0;
        boolean done = false;

        System.out.println("This is the client.");

        while(!in.equals("1") && !in.equals("2")) {
            System.out.println("Select the connection type you want to use:");
            System.out.println("1) Socket");
            System.out.println("2) RMI");
            b = new BufferedReader(new InputStreamReader(System.in));
            in = b.readLine();
        }

        //PARTE DEL CLIENT CONNESSO CON SOCKET
        if(in.equals("1")){
            System.out.println("You chose the Socket connection.");
            System.out.println("Insert the ip of the server:");
            ip = b.readLine();
            while(!done){

                System.out.println("Insert the port of the server:");
                try {
                    port = Integer.parseInt(b.readLine());
                    done = true;
                } catch (NumberFormatException e) {
                    System.err.println("Wrong port, retry please.");
                }
            }
            SocketClient client= new SocketClient(ip, port);
            client.connect();
        }



    }

}