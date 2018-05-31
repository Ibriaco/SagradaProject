package it.polimi.se2018.Server.Network.Socket;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;

public class SocketServer {
    private static ServerSocket serverSocket;
    private static ConnectionManager connectionsHandler;
    private static List<SocketConnection> socketConnections;

    public SocketServer(int port){
        try {
            serverSocket = new ServerSocket(port);
            connectionsHandler = new ConnectionManager(serverSocket, this);
            socketConnections = new ArrayList<>();
            connectionsHandler.start();
        } catch(IOException ex) {
        }
    }

    public List<SocketConnection> getSocketConnections() {
        return socketConnections;
    }

    public void addSocketConnection(SocketConnection sc){
        socketConnections.add(sc);
    }
}
