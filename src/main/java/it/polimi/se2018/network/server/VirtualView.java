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
import java.util.*;

/**
 * Class that works as a Proxy on the server side
 * It is observed by the EventsController
 * It observes the EventsController
 * @author Ibrahim El Shemy
 */
public class VirtualView implements ViewInterface {

    private ArrayList<MyObserver> observerCollection = new ArrayList<>();
    private HashMap<String, ClientInterface> clients;
    private List<String> removedClients;
    private VCEvent event;
    private ClientInterface clientTemp;

    public VirtualView(){

        clients = new HashMap<>();
        removedClients = new ArrayList<>();
    }

    /**
     * Updates a window card
     */
    @Override
    public void updateWindowCard() {

    }

    public synchronized void addClientToMap(String u, ClientInterface c){
        clients.put(u,c);
    }
    public void removeClientFromMap(String u) {clients.remove(u); }

    public Map<String, ClientInterface> getClients() {

        return clients;
    }


    public List<String> getRemovedClients() {

        return removedClients;
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
    public void handleMVEvent(DisconnectedEvent event) {

    }

    @Override
    public void handleMVEvent(PrivateCardEvent privateCardEvent) {

    }

    @Override
    public void handleMVEvent(WindowCardEvent event) {

    }

    @Override
    public void handleMVEvent(UpdateGameEvent updateGameEvent) {

    }

    @Override
    public void handleMVEvent(PublicCardEvent publicCardEvent) {

    }

    @Override
    public void handleMVEvent(ToolCardEvent toolCardEvent) {

    }

    @Override
    public void createNH(String choice) throws RemoteException {
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

    @Override
    public void handleMVEvent(IsTurnEvent isTurnEvent) throws InvalidConnectionException, InvalidViewException, ParseException, IOException {

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
    public void update(MyObservable o, MVEvent arg) throws IOException, InvalidConnectionException, InvalidViewException, ParseException {
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

    public void disconnectAlert(MVEvent event) throws InvalidConnectionException, ParseException, InvalidViewException, IOException {
        for (String user : clients.keySet()) {
            clients.get(user).sendMVEvent(event);
            System.out.println("disconnectALert:  dimensione hasmap" + clients.size() + "   dimensione removedlist:  " + removedClients.size());
        }
    }
    public synchronized void serverBeat(String user){
        new Thread(new Runnable(){
            public void run() {
                boolean ok = true;
                while (ok) {
                    try {
                        clients.get(user).ping();
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    } catch (RemoteException e) {
                        removedClients.add(user);
                        clients.remove(user);

                        ok = false;
                        DisconnectedEvent disconnectedEvent = new DisconnectedEvent(user);
                        try {
                            disconnectAlert(disconnectedEvent);
                            System.out.println("ho creato evento disconnessione");
                        } catch (InvalidConnectionException | ParseException | IOException | InvalidViewException e1) {
                            e1.printStackTrace();

                        }
                    }
                }
            }
        }).start();
    }
}
