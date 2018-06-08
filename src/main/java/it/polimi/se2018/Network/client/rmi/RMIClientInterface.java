package it.polimi.se2018.Network.client.rmi;

import it.polimi.se2018.Message;
import it.polimi.se2018.Network.client.ClientInterface;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RMIClientInterface extends Remote, ClientInterface {

    void notify(String message) throws RemoteException;

    void loginRequest(String username) throws RemoteException;

}
