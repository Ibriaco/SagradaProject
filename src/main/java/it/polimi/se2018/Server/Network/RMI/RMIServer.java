package it.polimi.se2018.Server.Network.RMI;

import it.polimi.se2018.Client.View.Network.RMI.RMIClientInterface;
import it.polimi.se2018.Message;

import java.rmi.ConnectException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Iterator;

public class RMIServer extends UnicastRemoteObject implements RMIServerInterface {

    private ArrayList<RMIClientInterface> clients = new ArrayList<RMIClientInterface>();

    public RMIServer() throws RemoteException {
        super(0);
    }

    private static final long serialVersionUID = -7098548671967083832L;

    public void addClient(RMIClientInterface client) throws RemoteException {
        if(clients.size() < 4) {
            clients.add(client);
            System.out.println("Client " + (clients.indexOf(client) + 1) + " connesso!");
        }
        else
            System.out.println("Lobby al completo! Unisciti a una nuova lobby per giocare!");
    }

    //bisogna capire come implementarlo
    public void removeClient(RMIClientInterface client) throws RemoteException{

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

}