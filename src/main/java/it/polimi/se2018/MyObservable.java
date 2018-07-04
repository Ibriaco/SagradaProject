package it.polimi.se2018;

import it.polimi.se2018.model.InvalidConnectionException;
import it.polimi.se2018.model.InvalidDieException;
import it.polimi.se2018.model.InvalidViewException;
import it.polimi.se2018.org.json.simple.parser.ParseException;

import java.io.IOException;
import java.rmi.RemoteException;

public interface MyObservable {

    void registerObserver(MyObserver observer) throws RemoteException;
    void unregisterObserver(MyObserver observer) throws RemoteException;
    void notifyObservers() throws IOException, InvalidConnectionException, InvalidViewException, ParseException, InvalidDieException;
}
