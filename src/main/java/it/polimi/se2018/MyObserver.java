package it.polimi.se2018;

import java.rmi.RemoteException;

public interface MyObserver {

    void update(MyObservable o, Object arg) throws RemoteException;
}
