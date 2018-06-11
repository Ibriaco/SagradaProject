package it.polimi.se2018.Network.client;

import it.polimi.se2018.Model.InvalidConnectionException;
import it.polimi.se2018.Model.InvalidViewException;
import it.polimi.se2018.Model.WindowCardAssociationException;
import it.polimi.se2018.MyObservable;
import it.polimi.se2018.MyObserver;
import it.polimi.se2018.View.ViewEvents.VCEvent;

import java.rmi.RemoteException;

public interface ClientInterface extends MyObservable, MyObserver {

    void notify(String message) throws RemoteException;

    void loginRequest(String username) throws RemoteException, InvalidConnectionException, InvalidViewException, WindowCardAssociationException;

    void sendEvent(VCEvent event) throws RemoteException;
}
