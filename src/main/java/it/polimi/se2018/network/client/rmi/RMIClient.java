package it.polimi.se2018.network.client.rmi;

import it.polimi.se2018.model.event.MVEvent;
import it.polimi.se2018.model.InvalidConnectionException;
import it.polimi.se2018.model.InvalidViewException;
import it.polimi.se2018.MyObservable;
import it.polimi.se2018.MyObserver;
import it.polimi.se2018.network.client.NetworkHandler;
import it.polimi.se2018.network.server.rmi.RMIServerInterface;
import it.polimi.se2018.view.viewevents.VCEvent;
import org.json.simple.parser.ParseException;

import java.io.IOException;
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

        /*new Thread(new Runnable(){
            public void run(){
                boolean ok = true;
                while(ok){
                    try {
                        server.ping();
                        Thread.sleep(10000);
                    }
                    catch(RemoteException e){
                        ok = false;
                        System.exit(-1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();*/
    }

    public void ping() throws RemoteException{
    }

    /**
     * Sends an event from the client invoking a method from the server
     * @param event event sent to the server
     * @throws InvalidConnectionException thrown exception
     * @throws RemoteException thrown exception
     * @throws InvalidViewException thrown exception
     */
    public void sendMVEvent (MVEvent event) throws InvalidConnectionException, IOException, InvalidViewException, ParseException {
        networkHandler.receciveMVEvent(event);
    }

    //metodo per inviare VCEvent da client a server
    @Override
    public void sendEvent(VCEvent event) throws IOException, InvalidConnectionException, InvalidViewException, ParseException {
        if (event.toString().equals("Login event")) {
            String username = event.getUsername();
            System.out.println("Trying to authenticate " + username + " ...");
            server.sendUser(remoteRef);
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
