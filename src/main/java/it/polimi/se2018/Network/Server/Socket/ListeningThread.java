package it.polimi.se2018.Network.Server.Socket;

import it.polimi.se2018.Model.Event.MVEvent;

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
            try {
                MVEvent receivedEvent = (MVEvent) fromServer.readObject();
                System.out.println("ricevuto evento, " + receivedEvent.getUsername());
            } catch (IOException | ClassNotFoundException | NullPointerException e) {
                //e.printStackTrace();
                System.out.println("stream chiuso");
            }
        }

    }
}
