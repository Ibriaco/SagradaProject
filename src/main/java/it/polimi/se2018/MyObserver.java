package it.polimi.se2018;

import it.polimi.se2018.Model.InvalidConnectionException;
import it.polimi.se2018.Model.InvalidViewException;

import java.rmi.RemoteException;

public interface MyObserver {

    void update(MyObservable o, Object arg) throws RemoteException, InvalidConnectionException, InvalidViewException;
}
