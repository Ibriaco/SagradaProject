package it.polimi.se2018.View.ViewEvents;

import it.polimi.se2018.Controller.ControllerInterface;
import it.polimi.se2018.Model.InvalidConnectionException;
import it.polimi.se2018.Model.InvalidViewException;
import it.polimi.se2018.Model.WindowCardAssociationException;

import java.io.Serializable;
import java.rmi.RemoteException;

/**
 * Class of the events that occur between ViewInterface and Controller.
 * @author Ibrahim El Shemy
 * @author Marco Gasperini
 */
public interface VCEvent extends Serializable {

    static final long serialVersionUID = 42L;
    void accept(ControllerInterface controller) throws RemoteException, InvalidConnectionException, WindowCardAssociationException, InvalidViewException;
    String getUsername();
}
