package it.polimi.se2018.Network.RMI;

import it.polimi.se2018.Message;
import it.polimi.se2018.Network.ClientInterface;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RMIClientInterface extends Remote, ClientInterface {

    void notify(Message message) throws RemoteException;

    void loginRequest() throws RemoteException;
}
