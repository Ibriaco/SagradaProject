package it.polimi.se2018.Client.View.Network.RMI;

import it.polimi.se2018.Message;

import java.rmi.RemoteException;

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
