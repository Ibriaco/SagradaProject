package it.polimi.se2018.Network.client.socket;

import it.polimi.se2018.Model.Event.MVEvent;
import it.polimi.se2018.Model.InvalidConnectionException;
import it.polimi.se2018.Model.InvalidViewException;
import it.polimi.se2018.Model.WindowCardAssociationException;
import it.polimi.se2018.MyObservable;
import it.polimi.se2018.MyObserver;
import it.polimi.se2018.Network.client.ClientInterface;
import it.polimi.se2018.Network.client.NetworkHandler;
import it.polimi.se2018.Network.server.socket.ListeningThread;
import it.polimi.se2018.View.ViewEvents.VCEvent;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.rmi.RemoteException;

public class SocketClient implements ClientInterface{
    private static Socket socket;
    private ListeningThread listeningThread;
    private ObjectOutputStream toServer;
    private NetworkHandler networkHandler;

    public SocketClient(String serverIP, Integer port, NetworkHandler nh) {
        try {
            networkHandler = nh;
            socket = new Socket(serverIP, port);
            toServer = new ObjectOutputStream(socket.getOutputStream());
        } catch (IOException e) {
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
            e.printStackTrace();
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
    public void update(MyObservable o, VCEvent arg) throws RemoteException, InvalidConnectionException, InvalidViewException, WindowCardAssociationException {

    }

    @Override
    public void update(MyObservable o, MVEvent arg) throws RemoteException, InvalidConnectionException, InvalidViewException {

    }
}
