package it.polimi.se2018.network.client.rmi;

import it.polimi.se2018.MyObservable;
import it.polimi.se2018.MyObserver;
import it.polimi.se2018.model.InvalidConnectionException;
import it.polimi.se2018.model.InvalidDieException;
import it.polimi.se2018.model.InvalidViewException;
import it.polimi.se2018.model.event.MVEvent;
import it.polimi.se2018.network.client.NetworkHandler;
import it.polimi.se2018.network.server.rmi.RMIServerInterface;
import it.polimi.se2018.org.json.simple.parser.ParseException;
import it.polimi.se2018.view.viewevents.VCEvent;

import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import static it.polimi.se2018.ClientConfig.*;


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
    private static final Logger LOGGER = Logger.getGlobal();


    public RMIClient(NetworkHandler nh) {
        networkHandler = nh;
        try {
            server = (RMIServerInterface) Naming.lookup(RMI_CLIENT_ADDRESS);
            remoteRef = (RMIClientInterface) UnicastRemoteObject.exportObject(this, 0);

        } catch (MalformedURLException e) {
            System.err.println(URL_NOT_FOUND);
        } catch (RemoteException e) {
            System.err.println(CONNECTION_ERROR + e.getMessage() + "!");
        } catch (NotBoundException e) {
            System.err.println(NOT_BOUND_MESSAGE);
        }

    }

    public void ping() throws RemoteException{
        /*intentionally left empty*/
    }

    /**
     * Sends an event from the client invoking a method from the server
     * @param event event sent to the server
     * @throws InvalidConnectionException thrown exception
     * @throws RemoteException thrown exception
     * @throws InvalidViewException thrown exception
     */
    public void sendMVEvent (MVEvent event) throws InvalidConnectionException, IOException, InvalidViewException, ParseException {
        networkHandler.receiveMVEvent(event);
    }

    @Override
    public void sendEvent(VCEvent event) {

        new Thread(()->{
            if (event.toString().equals(LOGIN_EVENT)) {
                String username = event.getUsername();
                System.out.println(AUTENTICATE + username + " ...");
                try {
                    server.sendUser(remoteRef);
                } catch (RemoteException | InvalidViewException e) {
                    LOGGER.log(Level.SEVERE, e.toString(), e);
                }
            }

            try {
                server.vceTransport(event);
            } catch (IOException | InvalidViewException | ParseException | InvalidConnectionException | InvalidDieException e) {
                LOGGER.log(Level.SEVERE, e.toString(), e);
            }
            Thread.currentThread().interrupt();
        }).start();
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

    }

    @Override
    public void update(MyObservable o, VCEvent arg) throws RemoteException {
        System.out.println(arg.toString());
    }

    @Override
    public void update(MyObservable o, MVEvent arg) throws RemoteException, InvalidConnectionException, InvalidViewException {

    }

    public void broadcast() throws RemoteException{
        server.broadcast();
    }
}
