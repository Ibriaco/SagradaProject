package it.polimi.se2018.Network.server.rmi;

import it.polimi.se2018.Model.InvalidConnectionException;
import it.polimi.se2018.Model.InvalidViewException;
import it.polimi.se2018.Network.LobbyController;
import it.polimi.se2018.Network.client.ClientInterface;
import it.polimi.se2018.Network.server.ServerInterface;
import it.polimi.se2018.View.ViewEvents.VCEvent;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Interface that refers to the connection (Server side) using RMI
 */
public interface RMIServerInterface extends Remote, ServerInterface {

    //bisogna capire come gestire disconnessione
    //void removeClient(RMIClientInterface client) throws RemoteException;

    //void send(Message message) throws RemoteException;

    void sendUser(String username, ClientInterface client) throws RemoteException, InvalidConnectionException, InvalidViewException;
    void vceTransport(VCEvent event) throws InvalidConnectionException, RemoteException, InvalidViewException;
    //void startGame() throws RemoteException, InvalidConnectionException, InvalidViewException, WindowCardAssociationException;
    //void sendPrivateObjective(VCEvent event) throws RemoteException;

    LobbyController getLobbyController() throws RemoteException;
}
