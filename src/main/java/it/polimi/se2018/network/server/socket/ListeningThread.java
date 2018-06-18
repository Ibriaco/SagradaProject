package it.polimi.se2018.network.server.socket;

import it.polimi.se2018.model.event.MVEvent;
import it.polimi.se2018.model.InvalidConnectionException;
import it.polimi.se2018.model.InvalidViewException;
import it.polimi.se2018.network.client.NetworkHandler;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

/**
 * @author Gregorio Galletti
 */
public class ListeningThread extends Thread{

    private Socket socket;
    private NetworkHandler networkHandler;

    public ListeningThread(Socket socket, NetworkHandler networkHandler){
        this.socket = socket;
        this.networkHandler = networkHandler;
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
                networkHandler.receciveMVEvent(receivedEvent);
                System.out.println("ricevuto evento, " + receivedEvent.getUsername());
            } catch (IOException | ClassNotFoundException | NullPointerException e) {
                //e.printStackTrace();
                System.out.println("stream chiuso");
            } catch (InvalidConnectionException | InvalidViewException e) {
                e.printStackTrace();
            }
        }

    }
}
