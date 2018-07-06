package it.polimi.se2018.network.server.socket;

import it.polimi.se2018.model.InvalidConnectionException;
import it.polimi.se2018.model.InvalidViewException;
import it.polimi.se2018.model.event.MVEvent;
import it.polimi.se2018.model.event.UpdateGameEvent;
import it.polimi.se2018.network.client.NetworkHandler;
import it.polimi.se2018.org.json.simple.parser.ParseException;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Gregorio Galletti
 */
public class ListeningThread extends Thread{

    private MVEvent receivedEvent;
    private Socket socket;
    private NetworkHandler networkHandler;
    private static final Logger LOGGER = Logger.getLogger( ListeningThread.class.getName() );
    private ObjectInputStream fromServer;

    public ListeningThread(Socket socket, NetworkHandler networkHandler){
        this.socket = socket;
        this.networkHandler = networkHandler;
    }

    @Override
    public void run() {
        boolean loop = true;
        try {
            fromServer = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, e.toString(), e);
        }

        while(loop) {
            try {
                receivedEvent = (MVEvent) fromServer.readObject();
                networkHandler.receiveMVEvent(receivedEvent);
            } catch (IOException | NullPointerException | InvalidConnectionException | InvalidViewException | ParseException | ClassNotFoundException e) {
                LOGGER.log(Level.SEVERE, e.toString(), e);
            }
        }

    }
}
