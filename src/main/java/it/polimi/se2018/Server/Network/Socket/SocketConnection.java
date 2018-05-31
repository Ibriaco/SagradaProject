package it.polimi.se2018.Server.Network.Socket;

import it.polimi.se2018.Server.Network.Socket.SocketServer;

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
