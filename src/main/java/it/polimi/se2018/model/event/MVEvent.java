package it.polimi.se2018.model.event;

import it.polimi.se2018.model.InvalidConnectionException;
import it.polimi.se2018.model.InvalidViewException;
import it.polimi.se2018.view.ui.ViewInterface;

import java.io.Serializable;
import java.rmi.RemoteException;

public interface MVEvent extends Serializable{

    void accept(ViewInterface vi) throws RemoteException, InvalidConnectionException, InvalidViewException;

    String getUsername();
}
