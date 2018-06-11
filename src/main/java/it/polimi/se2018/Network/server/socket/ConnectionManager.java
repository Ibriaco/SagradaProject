package it.polimi.se2018.Network.server.socket;

import it.polimi.se2018.Network.server.VirtualView;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author Gregorio Galetti
 */
public class ConnectionManager extends Thread{
    private ServerSocket serverSocket;
    private SocketServer socketServer;
    private VirtualView virtualView;

    public ConnectionManager(ServerSocket server, SocketServer s, VirtualView v) {
        serverSocket = server;
        socketServer = s;
        virtualView = v;
    }

    @Override
    public void run() {
        boolean loop = true;
        while(loop) {
            try {
                Socket socket = serverSocket.accept();
                SocketConnection connection = new SocketConnection(socket, socketServer, virtualView);
                connection.start();
                socketServer.addSocketConnection(connection);
            } catch(IOException e) {
                 loop = false;
            }
        }
    }
}
