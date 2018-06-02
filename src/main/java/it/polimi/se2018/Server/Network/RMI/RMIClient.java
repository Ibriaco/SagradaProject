package it.polimi.se2018.Server.Network.RMI;

import it.polimi.se2018.Message;
import it.polimi.se2018.Server.Network.ServerInterface;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class RMIClient implements RMIClientInterface {

    public void notify(Message message) throws RemoteException {
        System.out.println("Ho ricevuto il messaggio: " + message.getMessage());
    }


    public RMIClient() throws RemoteException{

    }

    public boolean authenticate(String username, String password) {
        if (username != null && password != null){
            return true;
        }
        else
            return false;

    }
}
