package it.polimi.se2018.Network.server.socket;

import it.polimi.se2018.View.ViewEvents.VCEvent;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

/**
 * Class that refers to the socket connection between client and server
 * @author Gregorio Galletti
 */
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
        ObjectInputStream fromClient;
        boolean first = true;
        boolean loop = true;
        while(loop)
        try {
            fromClient = new ObjectInputStream(connectionSocket.getInputStream());
            VCEvent receivedEvent = (VCEvent) fromClient.readObject();

            if(first) {
                first = false;
                //commentata per runnare il server. questo metodo andra sistemato perchè su RMI non si usa
                //socketServer.loginUser(receivedEvent);
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public Socket getConnectionSocket() {
        return connectionSocket;
    }
}
