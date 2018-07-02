package it.polimi.se2018.network.server.socket;

import it.polimi.se2018.model.event.MVEvent;
import it.polimi.se2018.model.InvalidConnectionException;
import it.polimi.se2018.model.InvalidViewException;
import it.polimi.se2018.network.client.NetworkHandler;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Gregorio Galletti
 */
public class ListeningThread extends Thread{

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
            e.printStackTrace();
        }

        while(loop) {
            try {

                Object receivedEvent = fromServer.readObject();
                networkHandler.receiveMVEvent((MVEvent)receivedEvent);
                //System.out.println("ricevuto evento, " + receivedEvent.getUsername());
            } catch (IOException | NullPointerException e) {
                //e.printStackTrace();
                System.out.println("stream chiuso");
            } catch (InvalidConnectionException | InvalidViewException | ParseException e) {
                LOGGER.log(Level.SEVERE, e.toString(), e);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

    }
}
