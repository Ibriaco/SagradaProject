package it.polimi.se2018.Network.client.rmi;

import it.polimi.se2018.Message;
import it.polimi.se2018.Model.InvalidConnectionException;
import it.polimi.se2018.Model.InvalidViewException;
import it.polimi.se2018.Model.WindowCardAssociationException;
import it.polimi.se2018.Network.client.ClientInterface;
import it.polimi.se2018.View.ViewEvents.VCEvent;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RMIClientInterface extends Remote, ClientInterface {

    void notify(String message) throws RemoteException;

    void loginRequest(String username) throws RemoteException, InvalidConnectionException, InvalidViewException, WindowCardAssociationException;

    void sendEvent(VCEvent event) throws RemoteException;
}
