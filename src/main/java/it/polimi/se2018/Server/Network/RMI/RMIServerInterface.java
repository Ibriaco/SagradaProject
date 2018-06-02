package it.polimi.se2018.Server.Network.RMI;

import it.polimi.se2018.Client.View.Network.RMI.RMIClientInterface;
import it.polimi.se2018.Message;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RMIServerInterface extends Remote{

    void addClient(RMIClientInterface client) throws RemoteException;

    //bisogna capire come gestire disconnessione
    void removeClient(RMIClientInterface client) throws RemoteException;

    void send(Message message) throws RemoteException;

}
