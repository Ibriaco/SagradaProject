package it.polimi.se2018.Network.server.rmi;

import it.polimi.se2018.Network.client.ClientInterface;
import it.polimi.se2018.Network.server.ServerInterface;
import it.polimi.se2018.View.VCEvent;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RMIServerInterface extends Remote, ServerInterface {

    void addClient(ClientInterface client) throws RemoteException;

    //bisogna capire come gestire disconnessione
    //void removeClient(RMIClientInterface client) throws RemoteException;

    //void send(Message message) throws RemoteException;

    void loginUser(VCEvent event) throws RemoteException;

    //void sendPrivateObjective(VCEvent event) throws RemoteException;
}
