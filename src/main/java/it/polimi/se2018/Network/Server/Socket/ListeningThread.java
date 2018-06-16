package it.polimi.se2018.Network.Server.Socket;

import it.polimi.se2018.Model.Event.MVEvent;
import it.polimi.se2018.Model.InvalidConnectionException;
import it.polimi.se2018.Model.InvalidViewException;
import it.polimi.se2018.Model.WindowCardAssociationException;
import it.polimi.se2018.Network.Client.NetworkHandler;

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
            } catch (WindowCardAssociationException e) {
                e.printStackTrace();
            } catch (InvalidConnectionException e) {
                e.printStackTrace();
            } catch (InvalidViewException e) {
                e.printStackTrace();
            }
        }

    }
}
