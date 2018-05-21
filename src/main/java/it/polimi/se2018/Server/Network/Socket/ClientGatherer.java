package it.polimi.se2018.Server.Network.Socket;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class ClientGatherer implements Runnable{
    private final SocketServer server;
    private int port;
    private ServerSocket serverSocket;

    public ClientGatherer(SocketServer server, int port) {
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
        //Attendo la connessione di nuovi client

        System.out.println("Waiting for clients.");

        while(true) {

            Socket newClientConnection;

            try {

                newClientConnection = serverSocket.accept();
                System.out.println("A new client connected.");

                server.addClient(newClientConnection.toString());

                InputStreamReader reader = new InputStreamReader(newClientConnection.getInputStream());
                System.out.println(reader.read());


            } catch (IOException e) {
                e.printStackTrace();
            }
            if(server.getClientList().size() == 4)
                break;

        }


    }

}
