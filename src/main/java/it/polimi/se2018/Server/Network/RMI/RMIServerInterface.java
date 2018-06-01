package it.polimi.se2018.Server.Network.RMI;

import it.polimi.se2018.Server.Network.ServerInterface;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RMIServerInterface extends Remote, ServerInterface{

    public void sendMessage(String s) throws RemoteException;

    public String getMessage(String text) throws RemoteException;

}
