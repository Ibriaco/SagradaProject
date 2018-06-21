package it.polimi.se2018.network.server;


import it.polimi.se2018.model.event.*;
import it.polimi.se2018.model.InvalidConnectionException;
import it.polimi.se2018.model.InvalidViewException;
import it.polimi.se2018.MyObservable;
import it.polimi.se2018.MyObserver;
import it.polimi.se2018.network.client.ClientInterface;
import it.polimi.se2018.view.ui.ViewInterface;
import it.polimi.se2018.view.viewevents.VCEvent;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Class that works as a Proxy on the server side
 * It is observed by the EventsController
 * It observes the EventsController
 * @author Ibrahim El Shemy
 */
public class VirtualView implements ViewInterface {

    private ArrayList<MyObserver> observerCollection = new ArrayList<>();
    private HashMap<String, ClientInterface> clients;
    private VCEvent event;
    private ClientInterface clientTemp;

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
    public void removeClientFromMap(String u) {clients.remove(u); }

    public Map<String, ClientInterface> getClients() {

        return clients;
    }

    /**
     * Receives an event from the server
     * @param event event received
     * @throws InvalidConnectionException thrown exception
     * @throws RemoteException thrown exception
     * @throws InvalidViewException thrown exception
     */
    public void receiveEvent(VCEvent event) throws InvalidConnectionException, IOException, InvalidViewException, ParseException {
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

    @Override
    public void handleMVEvent(PrivateCardEvent privateCardEvent) {

    }

    @Override
    public void handleMVEvent(WindowCardEvent event) {

    }

    @Override
    public void handleMVEvent(NewGameEvent newGameEvent) {

    }

    @Override
    public void handleMVEvent(PublicCardEvent publicCardEvent) {

    }

    @Override
    public void handleMVEvent(ToolCardEvent toolCardEvent) {

    }

    @Override
    public void createNH(int choice) throws RemoteException {
        /*Intentionally left void, not used in this class*/
    }

    @Override
    public void setUsername(String u) {
        /*Intentionally left void, not used in this class*/
    }

    @Override
    public void createLoginEvent() throws InvalidConnectionException, IOException, InvalidViewException, ParseException {
        /*Intentionally left void, not used in this class*/
    }

    /**
     * Shows the user interface
     */
    @Override
    public void showUI() {
        /*Intentionally left void, not used in this class*/
    }

    public void setClientTemp(ClientInterface clientTemp) {
        this.clientTemp = clientTemp;
    }

    public ClientInterface getClientTemp() {
        return clientTemp;
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
    public void notifyObservers() throws InvalidConnectionException, IOException, InvalidViewException, ParseException {
        for (MyObserver o: observerCollection) {
            o.update(this, event);
        }
    }

    @Override
    public void update(MyObservable o, VCEvent arg) throws RemoteException, InvalidConnectionException, InvalidViewException {

    }

    //se lo username dell'evento è ALL
    //inoltro l'evento a tutti i client
    //alrimenti solo al client corrispondente allo username
    @Override
    public void update(MyObservable o, MVEvent arg) throws RemoteException, InvalidConnectionException, InvalidViewException {
        if (arg.getUsername().equals("ALL")) {
            for (String user : clients.keySet()) {
                clients.get(user).sendMVEvent(arg);
            }
        }
        else{
            stampa();
            System.out.println(arg.getUsername());
            clients.get(arg.getUsername()).sendMVEvent(arg);
        }
    }

    public void stampa(){
        for (String s: getClients().keySet()) {
            System.out.println(getClients().keySet());
        }
    }
}
