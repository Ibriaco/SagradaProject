package it.polimi.se2018.Client.View.Network.RMI;

import it.polimi.se2018.Message;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RMIClientInterface extends Remote {

    public void notify(Message message) throws RemoteException;

    public boolean authenticate(String username, String password) throws RemoteException;

}
