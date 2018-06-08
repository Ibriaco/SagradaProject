package it.polimi.se2018.Network.client.rmi;

import it.polimi.se2018.Model.Event.MVEvent;
import it.polimi.se2018.MyObservable;
import it.polimi.se2018.MyObserver;
import it.polimi.se2018.Network.NetworkHandler;
import it.polimi.se2018.Network.server.rmi.RMIServerInterface;
import it.polimi.se2018.View.LoginEvent;
import it.polimi.se2018.View.VCEvent;
import it.polimi.se2018.Message;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class RMIClient implements RMIClientInterface {

    private RMIClientInterface remoteRef;
    private RMIServerInterface server;
    private ArrayList<MyObserver> observerCollection = new ArrayList<>();

    public void notify(String message) throws RemoteException {
        System.out.println(message);
    }

    public RMIClient() throws RemoteException {
        try {
            server = (RMIServerInterface) Naming.lookup("//localhost/MyServer");
            remoteRef = (RMIClientInterface) UnicastRemoteObject.exportObject(this, 0);

        } catch (MalformedURLException e) {
            System.err.println("URL non trovato!");
        } catch (RemoteException e) {
            System.err.println("Errore di connessione: " + e.getMessage() + "!");
        } catch (NotBoundException e) {
            System.err.println("Il riferimento passato non è associato a nulla!");
        }
    }

    public void loginRequest(String username) throws RemoteException {
        VCEvent loginE = new LoginEvent("RMI", username);
        System.out.println("Procedo ad autenticare " + username + " ...");
        server.sendUser(username, remoteRef);
        server.loginUser(loginE);



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
    public void notifyObservers() throws RemoteException {
        for (MyObserver o: observerCollection) {
            update(this, "");
        }
    }

    @Override
    public void update(MyObservable o, Object arg) throws RemoteException {
        System.out.println(arg.toString());
    }

}
