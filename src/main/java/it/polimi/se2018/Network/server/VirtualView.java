package it.polimi.se2018.Network.server;

import it.polimi.se2018.Model.Event.LoggedUserEvent;
import it.polimi.se2018.MyObservable;
import it.polimi.se2018.MyObserver;
import it.polimi.se2018.Network.client.Client;
import it.polimi.se2018.Network.client.ClientInterface;
import it.polimi.se2018.Network.client.rmi.RMIClient;
import it.polimi.se2018.Network.client.socket.SocketClient;
import it.polimi.se2018.View.ViewInterface;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;

public class VirtualView implements ViewInterface {

    private ArrayList<MyObserver> observerCollection;
    private RMIClient rmiClient;
    private SocketClient socketClient;

    private HashMap<String, ClientInterface> clients = new HashMap<>();
    /*public void getPlayers(LoggedUserEvent event){
        String user = event.getUsername();
        clients.put(user,);
    }*/

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

    public HashMap<String, ClientInterface> getClients() {
        return clients;
    }

    // mi serve array list observerCOllection per tenere traccia degli observers

}
