package it.polimi.se2018.Model.Event;

import it.polimi.se2018.Model.InvalidConnectionException;
import it.polimi.se2018.Model.InvalidViewException;
import it.polimi.se2018.Model.WindowCardAssociationException;
import it.polimi.se2018.View.UI.ViewInterface;

import java.io.Serializable;
import java.rmi.RemoteException;

public interface MVEvent extends Serializable{

    void accept(ViewInterface vi) throws RemoteException, InvalidConnectionException, WindowCardAssociationException, InvalidViewException;

    String getUsername();
}
