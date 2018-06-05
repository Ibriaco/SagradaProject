package it.polimi.se2018.Network.RMI;

import it.polimi.se2018.Message;

import java.rmi.ConnectException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Iterator;

public class RMISender {

    private ArrayList<RMIClientInterface> clients = new ArrayList<>();


    public RMISender() {
    }


    public void send(Message message) throws RemoteException {
        Iterator<RMIClientInterface> clientIterator = clients.iterator();
        while(clientIterator.hasNext()){
            try{
                clientIterator.next().notify(message);
            }catch(ConnectException e) {
                clientIterator.remove();
                System.out.println("Client rimosso!");
            }
        }
    }

    public void addClient(RMIClientInterface client) {
        clients.add(client);
    }
}
