package it.polimi.se2018;

import it.polimi.se2018.Model.InvalidConnectionException;
import it.polimi.se2018.Model.InvalidViewException;
import it.polimi.se2018.Model.WindowCardAssociationException;

import java.rmi.RemoteException;

public interface MyObservable {

    void registerObserver(MyObserver observer) throws RemoteException;
    void unregisterObserver(MyObserver observer) throws RemoteException;
    void notifyObservers() throws RemoteException, InvalidConnectionException, InvalidViewException, WindowCardAssociationException;
}
