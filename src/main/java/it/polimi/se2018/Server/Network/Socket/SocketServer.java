package it.polimi.se2018.Server.Network.Socket;

import java.io.IOException;
import java.net.ServerSocket;

public class SocketServer {

    private int port;
    private ServerSocket serverSocket;
    public SocketServer(int port) {
        this.port = port;
        System.out.println("This is the SOCKET server.");
    }

    public void start() throws IOException {
        serverSocket = new ServerSocket(port);
        System.out.println("Waiting for client to connect.");
        serverSocket.accept();
        System.out.println("A new client connected.");
    }
}
