package it.polimi.se2018.Network.server.rmi;

import it.polimi.se2018.Network.LobbyController;
import it.polimi.se2018.Network.client.ClientInterface;
import it.polimi.se2018.Network.client.rmi.RMIClientInterface;
import it.polimi.se2018.Network.server.ServerInterface;
import it.polimi.se2018.View.VCEvent;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RMIServerInterface extends Remote, ServerInterface {

    //bisogna capire come gestire disconnessione
    //void removeClient(RMIClientInterface client) throws RemoteException;

    //void send(Message message) throws RemoteException;

    int loginUser(VCEvent event) throws RemoteException;
    public void sendUser(String username, ClientInterface client) throws RemoteException;

    //void sendPrivateObjective(VCEvent event) throws RemoteException;

    LobbyController getLobbyController() throws RemoteException;
}
