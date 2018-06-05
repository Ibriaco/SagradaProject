package it.polimi.se2018.Network;

import it.polimi.se2018.View.VCEvent;
import it.polimi.se2018.Message;
import it.polimi.se2018.Network.RMI.RMIClientInterface;

import java.rmi.RemoteException;

public interface ServerInterface{

    void addClient(ClientInterface client) throws RemoteException;

    //void removeClient(RMIClientInterface client) throws RemoteException;

    //void send(Message message) throws RemoteException;

    void loginUser(VCEvent event) throws RemoteException;

    //void sendPrivateObjective(VCEvent event) throws RemoteException;
}
