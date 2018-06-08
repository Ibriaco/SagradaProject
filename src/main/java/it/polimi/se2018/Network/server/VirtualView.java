package it.polimi.se2018.Network.server;


import it.polimi.se2018.MyObservable;
import it.polimi.se2018.MyObserver;
import it.polimi.se2018.Network.NetworkHandler;
import it.polimi.se2018.Network.client.ClientInterface;
import it.polimi.se2018.View.ViewInterface;
import sun.nio.ch.Net;

import java.util.ArrayList;
import java.util.HashMap;

public class VirtualView implements ViewInterface {

    private ArrayList<MyObserver> observerCollection;
    private NetworkHandler nh;
    private HashMap<String, ClientInterface> clients = new HashMap<>();


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
        observerCollection.add(observer);
    }

    @Override
    public void unregisterObserver(MyObserver observer) {
        observerCollection.remove(observer);
    }

    @Override
    public void notifyObservers() {
        for (MyObserver o: observerCollection) {
            update(this, "");
        }
    }

    public HashMap<String, ClientInterface> getClients() {
        return clients;
    }

}
