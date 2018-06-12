package it.polimi.se2018.Network.Client.RMI;

import it.polimi.se2018.Model.Event.MVEvent;
import it.polimi.se2018.Model.InvalidConnectionException;
import it.polimi.se2018.Model.InvalidViewException;
import it.polimi.se2018.MyObservable;
import it.polimi.se2018.MyObserver;
import it.polimi.se2018.Network.Client.NetworkHandler;
import it.polimi.se2018.Network.Server.RMI.RMIServerInterface;
import it.polimi.se2018.View.ViewEvents.VCEvent;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

/**
 * Implementation of the interface RMIClientInterface
 * @author Ibrahim El Shemy
 * @author Marco Gasperini
 */
public class RMIClient implements RMIClientInterface {

    private RMIClientInterface remoteRef;
    private RMIServerInterface server;
    private NetworkHandler networkHandler;
    private ArrayList<MyObserver> observerCollection = new ArrayList<>();
    private VCEvent event;

    public void notify(String message) {
        System.out.println(message);
    }

    public RMIClient(NetworkHandler nh) {
        networkHandler = nh;
        try {
            server = (RMIServerInterface) Naming.lookup("//localhost/MyServer");
            remoteRef = (RMIClientInterface) UnicastRemoteObject.exportObject(this, 0);

        } catch (MalformedURLException e) {
            System.err.println("URL not found!");
        } catch (RemoteException e) {
            System.err.println("Errore di connessione: " + e.getMessage() + "!");
        } catch (NotBoundException e) {
            System.err.println("Il riferimento passato non è associato a nulla!");
        }
    }

    /**
     * Sends an event from the Client invoking a method from the Server
     * @param event event sent to the Server
     * @throws InvalidConnectionException thrown exception
     * @throws RemoteException thrown exception
     * @throws InvalidViewException thrown exception
     */
    public void sendMVEvent (MVEvent event) throws InvalidConnectionException, RemoteException, InvalidViewException {
        networkHandler.getMVEvent(event);
    }

    //metodo per inviare VCEvent da Client a Server
    @Override
    public void sendEvent(VCEvent event) throws RemoteException, InvalidConnectionException, InvalidViewException {
        if (event.toString().equals("Login Event")) {
            String username = event.getUsername();
            System.out.println("Trying to authenticate " + username + " ...");
            server.sendUser(username , remoteRef);
        }

        server.vceTransport(event);
    }


    @Override
    public void registerObserver(MyObserver observer) throws RemoteException {
        observerCollection.add(observer);
    }

    @Override
    public void unregisterObserver(MyObserver observer) throws RemoteException {
        observerCollection.remove(observer);
    }

    @Override
    public void notifyObservers() throws RemoteException, InvalidConnectionException, InvalidViewException {
        for (MyObserver o: observerCollection) {
            o.update(this, "");
        }
    }

    @Override
    public void update(MyObservable o, Object arg) throws RemoteException {
        System.out.println(arg.toString());
    }

}
