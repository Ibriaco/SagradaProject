package it.polimi.se2018.network.client.socket;

import it.polimi.se2018.model.event.MVEvent;
import it.polimi.se2018.model.InvalidConnectionException;
import it.polimi.se2018.model.InvalidViewException;
import it.polimi.se2018.MyObservable;
import it.polimi.se2018.MyObserver;
import it.polimi.se2018.network.client.ClientInterface;
import it.polimi.se2018.network.client.NetworkHandler;
import it.polimi.se2018.network.server.socket.ListeningThread;
import it.polimi.se2018.view.viewevents.VCEvent;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SocketClient implements ClientInterface{
    private static Socket socket;
    private ListeningThread listeningThread;
    private ObjectOutputStream toServer;
    private NetworkHandler networkHandler;
    private static final Logger LOGGER = Logger.getLogger(SocketClient.class.getName());

    public SocketClient(String serverIP, Integer port, NetworkHandler nh) {
        try {
            networkHandler = nh;
            socket = new Socket(serverIP, port);
            toServer = new ObjectOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, e.toString(), e);
        }

        listeningThread = new ListeningThread(socket, networkHandler);
        listeningThread.start();

    }

    @Override
    public void sendMVEvent(MVEvent event) {

    }

    @Override
    public void sendEvent(VCEvent event) throws RemoteException {
        try {
            toServer.writeObject(event);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, e.toString(), e);
        }
    }

    @Override
    public void registerObserver(MyObserver observer) {

    }

    @Override
    public void unregisterObserver(MyObserver observer) {

    }

    @Override
    public void notifyObservers() {

    }

    @Override
    public void update(MyObservable o, VCEvent arg) throws RemoteException, InvalidConnectionException, InvalidViewException {

    }

    @Override
    public void update(MyObservable o, MVEvent arg) throws RemoteException, InvalidConnectionException, InvalidViewException {

    }
}
