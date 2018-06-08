package it.polimi.se2018.Network.client;

import it.polimi.se2018.Message;
import it.polimi.se2018.MyObservable;
import it.polimi.se2018.MyObserver;

import java.rmi.RemoteException;

public interface ClientInterface extends MyObservable, MyObserver {

    void notify(String message) throws RemoteException;

    void loginRequest(String username) throws RemoteException;

}
