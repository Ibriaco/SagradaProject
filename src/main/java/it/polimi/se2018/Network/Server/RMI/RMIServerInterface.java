package it.polimi.se2018.Network.Server.RMI;

import it.polimi.se2018.Model.InvalidConnectionException;
import it.polimi.se2018.Model.InvalidViewException;
import it.polimi.se2018.Model.WindowCardAssociationException;
import it.polimi.se2018.Network.Client.ClientInterface;
import it.polimi.se2018.Network.Server.ServerInterface;
import it.polimi.se2018.View.ViewEvents.VCEvent;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Interface that refers to the connection (Server side) using RMI
 */
public interface RMIServerInterface extends Remote, ServerInterface {

    //bisogna capire come gestire disconnessione
    //void removeClient(RMIClientInterface Client) throws RemoteException;

    //void send(Message message) throws RemoteException;

    void sendUser(ClientInterface client) throws RemoteException, InvalidConnectionException, InvalidViewException;
    void vceTransport(VCEvent event) throws InvalidConnectionException, RemoteException, InvalidViewException, WindowCardAssociationException;
    //void startGame() throws RemoteException, InvalidConnectionException, InvalidViewException, WindowCardAssociationException;
    //void sendPrivateObjective(VCEvent event) throws RemoteException;

}
