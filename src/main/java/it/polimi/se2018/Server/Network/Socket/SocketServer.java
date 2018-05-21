package it.polimi.se2018.Server.Network.Socket;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class SocketServer {

    private int port;
    private List<Socket> clientList;

    public SocketServer(int port) {

        clientList = new ArrayList<>();
        this.port = port;
        System.out.println("SOCKET SERVER RUNNING.");

        System.out.println("This is the SOCKET server.");

        //qui devo creare il gatherer per gestire piu client
        (new ClientGatherer(this, port)).run();

        //serverSocket = new ServerSocket(port);
        //System.out.println("Waiting for client to connect.");
        //serverSocket.accept();
        //System.out.println("A new client connected.");
    }

    public synchronized void addClient(Socket clientConnection){
        ClientSocketConnection cm = new ClientSocketConnection(this, clientConnection);

        clientList.add(clientConnection);
        cm.run();
    }

    public List<Socket> getClientList(){
        return clientList;
    }
}
