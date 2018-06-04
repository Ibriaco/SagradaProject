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
    private static final long serialVersionUID = -7098548671967083832L;

    public RMIServer(LobbyController lc) throws RemoteException {
        super(0);
        lobbyController = lc;
    }

    public void addClient(RMIClientInterface client) throws RemoteException {
        if(clients.size() < 4) {
            clients.add(client);
            System.out.println("Client " + (clients.indexOf(client) + 1) + " connesso!");
        }
        else {
            client.notify(new Message("Spiacente! Lobby al completo!"));
        }
    }


    public void removeClient(RMIClientInterface client) throws RemoteException{
        clients.remove(client);
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
        if(lobbyController.checkUser(user)&&lobbyController.checkOnlinePlayers(user)&&lobbyController.checkTime(user)) {
            lobbyController.addInLobby(user);

            Iterator<RMIClientInterface> clientIterator = clients.iterator();
            while(clientIterator.hasNext()){
                try{
                    clientIterator.next().notify(new Message("Utente " + user + " loggato\n" +
                            "Online players: " + String.valueOf(clients.size())));
                }catch(ConnectException e) {
                }
            }

            System.out.println("Utente loggato!");
        }
        else if(!lobbyController.checkOnlinePlayers(user)){
            System.out.println("Lobby al completo!");
        }
        else if(!lobbyController.checkUser(user)){
            clients.get(clients.size()-1).notify(new Message("Utente " + user + " già presente\n" +
                    "Online players: " + String.valueOf(clients.size()-1)));
            this.removeClient(clients.get(clients.size()-1));
            System.out.println("Utente non loggato!");
        }
        else if(!lobbyController.checkTime(user)){
            clients.get(clients.size()-1).notify(new Message("Spiacente " + user + ": tempo scaduto!\n" +
                    "Unisciti a un'altra partita per giocare!"));
            this.removeClient(clients.get(clients.size()-1));
            System.out.println("Utente non loggato!");
        }
    }


}



