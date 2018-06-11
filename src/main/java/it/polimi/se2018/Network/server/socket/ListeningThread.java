package it.polimi.se2018.Network.server.socket;

import it.polimi.se2018.Message;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

/**
 * @author Gregorio Galletti
 */
public class ListeningThread extends Thread{

    private Socket socket;

    public ListeningThread(Socket socket){
        this.socket = socket;
    }

    @Override
    public void run() {
        boolean loop = true;

        ObjectInputStream fromServer = null;
        try {
            fromServer = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

        while(loop) {
            Message receivedE = null;
            try {
                assert fromServer != null;
                receivedE = (Message) fromServer.readObject();
            } catch (IOException | ClassNotFoundException | NullPointerException e) {

            }
            if (receivedE == null)
                loop = false;
            else
                System.out.println(receivedE.getMessage());
        }
    }
}
