package it.polimi.se2018.Network.Server;


import it.polimi.se2018.Model.Event.LoggedUserEvent;
import it.polimi.se2018.Model.Event.MVEvent;
import it.polimi.se2018.Model.InvalidConnectionException;
import it.polimi.se2018.Model.InvalidViewException;
import it.polimi.se2018.Model.WindowCardAssociationException;
import it.polimi.se2018.MyObservable;
import it.polimi.se2018.MyObserver;
import it.polimi.se2018.Network.Client.ClientInterface;
import it.polimi.se2018.View.UI.ViewInterface;
import it.polimi.se2018.View.ViewEvents.VCEvent;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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

    public void addClientToMap(String u, ClientInterface c){
        clients.put(u,c);
    }

    public Map<String, ClientInterface> getClients() {

        return clients;
    }

    /**
     * Receives an event from the Server
     * @param event event received
     * @throws InvalidConnectionException thrown exception
     * @throws RemoteException thrown exception
     * @throws InvalidViewException thrown exception
     */
    public void receiveEvent(VCEvent event) throws InvalidConnectionException, RemoteException, InvalidViewException, WindowCardAssociationException {
        this.event = event;
        notifyObservers();
    }

    @Override
    public void loginScreen() throws RemoteException {
        /*Intentionally left void, not used in this class*/
    }

    @Override
    public void handleMVEvent(LoggedUserEvent event) {

    }

    /**
     * Shows the user interface
     */
    @Override
    public void showUI() {
        /*Intentionally left void, not used in this class*/
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
    public void notifyObservers() throws InvalidConnectionException, RemoteException, InvalidViewException, WindowCardAssociationException {
        for (MyObserver o: observerCollection) {
            o.update(this, event);
        }
    }

    @Override
    public void update(MyObservable o, VCEvent arg) throws RemoteException, InvalidConnectionException, InvalidViewException {

    }

    //se lo username dell'evento è ALL
    //inoltro l'evento a tutti i Client
    //alrimenti solo al Client corrispondente allo username
    @Override
    public void update(MyObservable o, MVEvent arg) throws RemoteException, InvalidConnectionException, InvalidViewException {

        if (event.getUsername().equals("ALL")) {
            for (String user : clients.keySet()) {
                clients.get(user).sendMVEvent(arg);
            }
        }
        else{
            clients.get(event.getUsername()).sendMVEvent(arg);
        }
    }
}
