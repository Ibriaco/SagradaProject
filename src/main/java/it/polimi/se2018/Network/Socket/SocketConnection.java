package it.polimi.se2018.Network.Socket;

import java.net.Socket;

public class SocketConnection extends Thread {
    private SocketServer socketServer;
    private Socket connectionSocket;


    public SocketConnection(Socket socket, SocketServer socketServer){
        connectionSocket = socket;
        this.socketServer = socketServer;
    }

    @Override
    public void run(){
        System.out.println("Client connesso!");
    }

}
