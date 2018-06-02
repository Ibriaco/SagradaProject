package it.polimi.se2018.Server.Network.RMI;

import it.polimi.se2018.Message;
import it.polimi.se2018.Server.Network.ClientInterface;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RMIClientInterface extends Remote {

    public void notify(Message message) throws RemoteException;

    public boolean authenticate(String username, String password) throws RemoteException;

}
