package it.polimi.se2018.Server.Network.RMI;

import it.polimi.se2018.Server.Network.ClientInterface;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RMIClientInterface extends Remote, ClientInterface {
    public void sendMessage(String s) throws RemoteException;

    public String getMessage(String text) throws RemoteException;

}
