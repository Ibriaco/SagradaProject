package it.polimi.se2018.Network.server;


import it.polimi.se2018.Controller.EventsController;
import it.polimi.se2018.Model.InvalidConnectionException;
import it.polimi.se2018.Model.InvalidViewException;
import it.polimi.se2018.MyObservable;
import it.polimi.se2018.MyObserver;
import it.polimi.se2018.Network.client.ClientInterface;
import it.polimi.se2018.View.UI.ViewInterface;
import it.polimi.se2018.View.ViewEvents.VCEvent;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;

public class VirtualView implements ViewInterface {

    private EventsController eventsController;
    private ArrayList<MyObserver> observerCollection = new ArrayList<>();
    private HashMap<String, ClientInterface> clients = new HashMap<>();
    private VCEvent event;

    @Override
    public void updateWindowCard() {

    }

    @Override
    public void showUI() {

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

    @Override
    public void update(MyObservable o, Object arg) {
        System.out.println(arg.toString());
    }

    public HashMap<String, ClientInterface> getClients() {
        return clients;
    }

    /*public void sendLoginEvent(String username) throws RemoteException {

        update(this, username);
        lobbyController.handleLogin(username);
    }*/

    public void getEvent(VCEvent event) throws InvalidConnectionException, RemoteException, InvalidViewException {
        this.event = event;
        notifyObservers();
    }

    @Override
    public void loginScreen() throws RemoteException {
    }
}
