package it.polimi.se2018;

import it.polimi.se2018.Model.Event.MVEvent;
import it.polimi.se2018.Model.InvalidConnectionException;
import it.polimi.se2018.Model.InvalidViewException;
import it.polimi.se2018.Model.WindowCardAssociationException;
import it.polimi.se2018.View.ViewEvents.VCEvent;

import java.rmi.RemoteException;

public interface MyObserver {

    void update(MyObservable o, VCEvent arg) throws RemoteException, InvalidConnectionException, InvalidViewException, WindowCardAssociationException;
    void update(MyObservable o, MVEvent arg) throws RemoteException, InvalidConnectionException, InvalidViewException, WindowCardAssociationException;

}
