package it.polimi.se2018.Network.RMI;

import it.polimi.se2018.Network.ClientInterface;
import it.polimi.se2018.View.VCEvent;
import it.polimi.se2018.Message;
import it.polimi.se2018.Network.ServerInterface;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RMIServerInterface extends Remote, ServerInterface{

    void addClient(ClientInterface client) throws RemoteException;

    //bisogna capire come gestire disconnessione
    //void removeClient(RMIClientInterface client) throws RemoteException;

    //void send(Message message) throws RemoteException;

    void loginUser(VCEvent event) throws RemoteException;

    //void sendPrivateObjective(VCEvent event) throws RemoteException;
}
