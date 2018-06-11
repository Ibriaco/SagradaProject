package it.polimi.se2018.Network.server.socket;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author Gregorio Galletti
 */
public class ConnectionManager extends Thread{
    private ServerSocket serverSocket;
    private SocketServer socketServer;

    public ConnectionManager(ServerSocket server, SocketServer s) {
        serverSocket = server;
        socketServer = s;
    }

    @Override
    public void run() {
        boolean loop = true;
        while(loop) {
            try {
                Socket socket = serverSocket.accept();
                SocketConnection connection = new SocketConnection(socket, socketServer);
                connection.start();
                socketServer.addSocketConnection(connection);
            } catch(IOException e) {
                 loop = false;
            }
        }
    }
}
