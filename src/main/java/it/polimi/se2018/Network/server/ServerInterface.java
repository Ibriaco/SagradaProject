package it.polimi.se2018.Network.server;

import it.polimi.se2018.Model.InvalidConnectionException;
import it.polimi.se2018.Model.InvalidViewException;
import it.polimi.se2018.Network.LobbyController;
import it.polimi.se2018.Network.client.ClientInterface;
import it.polimi.se2018.View.ViewEvents.VCEvent;

import java.rmi.RemoteException;

/**
 * Class that represents the generic Server Interface that will be implement by either RMI/Socket.
 */
public interface ServerInterface{

    void vceTransport(VCEvent event) throws RemoteException, InvalidConnectionException, InvalidViewException;
    void sendUser(String username, ClientInterface client) throws RemoteException, InvalidConnectionException, InvalidViewException;
    LobbyController getLobbyController() throws RemoteException;
}
