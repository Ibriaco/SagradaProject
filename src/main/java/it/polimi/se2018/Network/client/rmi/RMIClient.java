package it.polimi.se2018.Network.client.rmi;

import it.polimi.se2018.MyObservable;
import it.polimi.se2018.MyObserver;
import it.polimi.se2018.Network.server.rmi.RMIServerInterface;
import it.polimi.se2018.View.LoginEvent;
import it.polimi.se2018.View.VCEvent;
import it.polimi.se2018.Message;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class RMIClient implements RMIClientInterface {

    private RMIClientInterface remoteRef;
    private RMIServerInterface server;

    public void notify(Message message) throws RemoteException {
        System.out.println(message.getMessage());
    }

    public RMIClient() throws RemoteException{
        try{
        server = (RMIServerInterface) Naming.lookup("//localhost/MyServer");
        remoteRef = (RMIClientInterface) UnicastRemoteObject.exportObject(this, 0);

    } catch (MalformedURLException e) {
        System.err.println("URL non trovato!");
    } catch (RemoteException e) {
        System.err.println("Errore di connessione: " + e.getMessage() + "!");
    } catch (NotBoundException e) {
        System.err.println("Il riferimento passato non è associato a nulla!");
    }
    //loginRequest();
    }

    public void getPrivateObjective() throws RemoteException{

    }


    public void loginRequest(String username) throws RemoteException {
        VCEvent loginE = new LoginEvent("RMI", username);
        System.out.println("Procedo ad autenticare " + username + " ...");
        int status = server.loginUser(loginE);
        switch (status) {
            case 0:
                server.sendUser(username,remoteRef);
                break;
            case -1:
                System.out.println("Lobby is full");
                break;
            case -2:
                System.out.println("Username already used");
                break;
            case -3:
                System.out.println("Timer expired");
                break;
            default:
                break;

        }

    }



    @Override
    public void registerObserver(MyObserver observer) throws RemoteException{

    }

    @Override
    public void unregisterObserver(MyObserver observer) throws RemoteException{

    }

    @Override
    public void notifyObservers() throws RemoteException{

    }

    @Override
    public void update(MyObservable o, Object arg) throws RemoteException{

    }
}
