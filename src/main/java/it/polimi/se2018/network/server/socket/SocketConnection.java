package it.polimi.se2018.network.server.socket;

import it.polimi.se2018.MyObservable;
import it.polimi.se2018.MyObserver;
import it.polimi.se2018.model.InvalidConnectionException;
import it.polimi.se2018.model.InvalidDieException;
import it.polimi.se2018.model.InvalidViewException;
import it.polimi.se2018.model.event.MVEvent;
import it.polimi.se2018.network.client.ClientInterface;
import it.polimi.se2018.network.server.VirtualView;
import it.polimi.se2018.org.json.simple.parser.ParseException;
import it.polimi.se2018.view.viewevents.VCEvent;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Gregorio Galletti
 */
public class SocketConnection extends Thread implements ClientInterface {
    private SocketServer socketServer;
    private Socket connectionSocket;
    private VCEvent receivedEvent;
    private VirtualView virtualView;
    private ObjectInputStream fromClient;
    private ObjectOutputStream toClient;
    private static final Logger LOGGER = Logger.getGlobal();

    public SocketConnection(Socket socket, SocketServer socketServer, VirtualView v){
        connectionSocket = socket;
        this.socketServer = socketServer;
        virtualView = v;
    }

    @Override
    public void run(){
        System.out.println("Client connesso!");
        virtualView.setClientTemp(socketServer.getSocketConnections().get(socketServer.getSocketConnections().indexOf(this)));
        try {
            fromClient = new ObjectInputStream(this.getConnectionSocket().getInputStream());
            toClient = new ObjectOutputStream(this.getConnectionSocket().getOutputStream());
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, e.toString(), e);
        }

        boolean loop = true;
        while(loop)
        try {
            receivedEvent = (VCEvent) fromClient.readObject();
            virtualView.receiveEvent(receivedEvent);


        } catch (IOException | InvalidConnectionException | InvalidViewException | NullPointerException | ParseException | ClassNotFoundException | InvalidDieException e) {
            LOGGER.log(Level.SEVERE, e.toString(), e);
        }
    }

    public Socket getConnectionSocket() {
        return connectionSocket;
    }



    //metodi implementati solo per correttezza

    @Override
    public void sendMVEvent(MVEvent event) throws RemoteException, InvalidConnectionException, InvalidViewException {
        try {
            toClient.writeObject(event);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, e.toString(), e);
        }
    }

    @Override
    public void sendEvent(VCEvent event) throws RemoteException, InvalidConnectionException, InvalidViewException {

    }

    @Override
    public void ping() throws RemoteException {
        try {
            connectionSocket.getOutputStream().write(0);
        } catch (IOException e) {
            LOGGER.log(Level.INFO, "Disconnected!");
        }

    }

    @Override
    public void registerObserver(MyObserver observer) throws RemoteException {

    }

    @Override
    public void unregisterObserver(MyObserver observer) throws RemoteException {

    }

    @Override
    public void notifyObservers() throws RemoteException, InvalidConnectionException, InvalidViewException {

    }


    @Override
    public void update(MyObservable o, VCEvent arg) throws RemoteException, InvalidConnectionException, InvalidViewException {

    }

    @Override
    public void update(MyObservable o, MVEvent arg) throws RemoteException, InvalidConnectionException, InvalidViewException {

    }
}
