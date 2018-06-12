package it.polimi.se2018.Network.Client.Socket;

import it.polimi.se2018.Model.Event.MVEvent;
import it.polimi.se2018.MyObservable;
import it.polimi.se2018.MyObserver;
import it.polimi.se2018.Network.Client.ClientInterface;
import it.polimi.se2018.Network.Server.Socket.ListeningThread;
import it.polimi.se2018.View.ViewEvents.VCEvent;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.rmi.RemoteException;

public class SocketClient implements ClientInterface{
    private static Socket socket;
    private ListeningThread listeningThread;
    private ObjectOutputStream toServer;

    public SocketClient(String serverIP, Integer port) {
        try {
            socket = new Socket(serverIP, port);
            toServer = new ObjectOutputStream(socket.getOutputStream());
        } catch (IOException e) {
        }

        listeningThread = new ListeningThread(socket);
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
    public void update(MyObservable o, Object arg) {

    }
}