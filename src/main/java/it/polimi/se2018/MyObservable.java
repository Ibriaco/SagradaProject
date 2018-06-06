package it.polimi.se2018;

import java.rmi.RemoteException;

public interface MyObservable {

    void registerObserver(MyObserver observer) throws RemoteException;
    void unregisterObserver(MyObserver observer) throws RemoteException;
    void notifyObservers() throws RemoteException;
}
