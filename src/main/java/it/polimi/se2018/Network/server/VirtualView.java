package it.polimi.se2018.Network.server;


import it.polimi.se2018.MyObservable;
import it.polimi.se2018.MyObserver;
import it.polimi.se2018.Network.LobbyController;
import it.polimi.se2018.Network.client.ClientInterface;
import it.polimi.se2018.View.UI.ViewInterface;
import it.polimi.se2018.View.ViewEvents.VCEvent;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;

public class VirtualView implements ViewInterface {

    private LobbyController lobbyController;
    public ArrayList<MyObserver> getObserverCollection() {
        return observerCollection;
    }
    private ArrayList<MyObserver> observerCollection;
    private HashMap<String, ClientInterface> clients = new HashMap<>();
    private VCEvent event;


    public VirtualView(){

    }

    @Override
    public void updateWindowCard() {

    }

    @Override
    public void showUI() {

    }

    @Override
    public void update(MyObservable o, Object arg) {
        System.out.println(arg.toString());
    }

    @Override
    public void registerObserver(MyObserver observer) {

        observerCollection.add(lobbyController);
    }

    @Override
    public void unregisterObserver(MyObserver observer) {

        observerCollection.remove(lobbyController);
    }

    @Override
    public void notifyObservers() {
        for (MyObserver o: observerCollection) {
            update(this, toString());
        }
    }

    public HashMap<String, ClientInterface> getClients() {

        return clients;
    }

    /*public void sendLoginEvent(String username) throws RemoteException {

        update(this, username);
        lobbyController.handleLogin(username);
    }*/

    public void getEvent(VCEvent event) {
        this.event = event;
    }
}
