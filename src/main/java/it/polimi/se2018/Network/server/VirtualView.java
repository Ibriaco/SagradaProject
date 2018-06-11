package it.polimi.se2018.Network.server;


import it.polimi.se2018.Controller.EventsController;
import it.polimi.se2018.Model.Event.MVEvent;
import it.polimi.se2018.Model.InvalidConnectionException;
import it.polimi.se2018.Model.InvalidViewException;
import it.polimi.se2018.MyObservable;
import it.polimi.se2018.MyObserver;
import it.polimi.se2018.Network.client.Client;
import it.polimi.se2018.Network.client.ClientInterface;
import it.polimi.se2018.View.UI.ViewInterface;
import it.polimi.se2018.View.ViewEvents.VCEvent;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Class that works as a Proxy on the Server side
 * It is observed by the EventsController
 * It observes the EventsController
 * @author Ibrahim El Shemy
 */
public class VirtualView implements ViewInterface {

    private ArrayList<MyObserver> observerCollection = new ArrayList<>();
    private HashMap<String, ClientInterface> clients;
    private VCEvent event;

    public VirtualView(){

        clients = new HashMap<>();
    }

    /**
     * Updates a window card
     */
    @Override
    public void updateWindowCard() {

    }

    @Override
    public void registerObserver(MyObserver observer) {
        observerCollection.add(observer);
    }

    @Override
    public void unregisterObserver(MyObserver observer) {
        observerCollection.remove(observer);
    }

    @Override
    public void notifyObservers() throws InvalidConnectionException, RemoteException, InvalidViewException {
        for (MyObserver o: observerCollection) {
            o.update(this, event);
        }
    }

    //se lo username dell'evento è ALL
    //inoltro l'evento a tutti i client
    //alrimenti solo al client corrispondente allo username
    @Override
    public void update(MyObservable o, Object arg) throws RemoteException, InvalidConnectionException, InvalidViewException {
        MVEvent event = (MVEvent) arg;
        if (event.getUsername().equals("ALL")) {
            for (String user : clients.keySet()) {
                clients.get(user).sendMVEvent((MVEvent) arg);
            }
        }
        else{
            clients.get(event.getUsername()).sendMVEvent((MVEvent) arg);
        }
    }

    public void addClientToMap(String u, ClientInterface c){
        clients.put(u,c);
    }

    public HashMap<String, ClientInterface> getClients() {
        return clients;
    }

    /**
     * Receives an event from the server
     * @param event event received
     * @throws InvalidConnectionException thrown exception
     * @throws RemoteException thrown exception
     * @throws InvalidViewException thrown exception
     */
    public void receiveEvent(VCEvent event) throws InvalidConnectionException, RemoteException, InvalidViewException {
        this.event = event;
        notifyObservers();
    }

    @Override
    public void loginScreen() throws RemoteException {
        /*Intentionally left void, not used in this class*/
    }

    /**
     * Shows the user interface
     */
    @Override
    public void showUI() {
        /*Intentionally left void, not used in this class*/
    }
}
