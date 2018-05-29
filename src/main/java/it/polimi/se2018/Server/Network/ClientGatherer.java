package it.polimi.se2018.Server.Network;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ClientGatherer extends Thread {

    private final Server server;
    private final int port;
    private ServerSocket serverSocket;


    public ClientGatherer( Server server, int port ) {
        this.server = server;
        this.port = port;

        // Inizializzo il server socket
        try {
            this.serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run(){

        // In loop attendo la connessione di nuovi client

        System.out.println("Waiting for clients.\n");

        while(true) {

            Socket newClientConnection;

            try {

                newClientConnection = serverSocket.accept();
                System.out.println("A new client connected.");

                // Aggiungo il client
                server.addClient(newClientConnection);

            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}
