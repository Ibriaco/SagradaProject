package it.polimi.se2018.Network.RMI;

import it.polimi.se2018.View.VCEvent;
import it.polimi.se2018.Message;
import it.polimi.se2018.Network.LobbyController;

import java.rmi.ConnectException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Iterator;

public class RMIServer extends UnicastRemoteObject implements RMIServerInterface {

    private ArrayList<RMIClientInterface> clients = new ArrayList<>();
    private LobbyController lobbyController;

    public RMIServer(LobbyController lc) throws RemoteException {
        super(0);
        lobbyController = lc;
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

    public void loginUser(VCEvent event) throws RemoteException {
        String user = event.getUsername();
        //System.out.println("okei login");
        if(lobbyController.checkUser(user)) {
            lobbyController.addInLobby(user);

            Iterator<RMIClientInterface> clientIterator = clients.iterator();
            while(clientIterator.hasNext()){
                try{
                    clientIterator.next().notify(new Message("Utente " + user + " loggato"));
                }catch(ConnectException e) {
                }
            }

            System.out.println("Utente loggato!");
        }
        else{
            System.out.println("Utente non loggato!");
        }

    }

}