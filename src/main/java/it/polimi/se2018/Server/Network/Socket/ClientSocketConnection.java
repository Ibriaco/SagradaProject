package it.polimi.se2018.Server.Network.Socket;

import it.polimi.se2018.Client.ClientInterface;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ClientSocketConnection extends Thread implements ClientInterface{

    private final SocketServer server;

    private Socket clientConnection;

    public ClientSocketConnection( SocketServer server, Socket clientConnection) {

        this.server = server;
        this.clientConnection = clientConnection;
    }

    @Override
    public void run(){
        System.out.println("CLIENT SOCKET CONNECTION RUNNING.");

        try {

            BufferedReader is = new BufferedReader(new InputStreamReader(clientConnection.getInputStream()));

            boolean loop = true;

            while(loop) {

                System.out.println("Waiting for messages.");

                String message = is.readLine();
                if ( message == null ) {
                    loop = false;
                } else {
                    System.out.println(message);
                }

            }

            clientConnection.close();

            System.out.println("Connection closed.");


        } catch (IOException e) {
            e.printStackTrace();
        }

    }



    }

