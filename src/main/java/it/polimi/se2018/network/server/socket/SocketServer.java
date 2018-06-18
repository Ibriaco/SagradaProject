package it.polimi.se2018.network.server.socket;

import it.polimi.se2018.model.InvalidConnectionException;
import it.polimi.se2018.model.InvalidViewException;
import it.polimi.se2018.network.client.ClientInterface;
import it.polimi.se2018.network.server.ServerInterface;
import it.polimi.se2018.network.server.VirtualView;
import it.polimi.se2018.view.viewevents.VCEvent;
import it.polimi.se2018.controller.LobbyController;

import java.io.IOException;
import java.net.ServerSocket;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

/**
 * Class that implements the ServerInterface
 * @author Gregorio Galletti
 */
public class SocketServer implements ServerInterface {
    private static ServerSocket serverSocket;
    private static ConnectionManager connectionsManager;
    private static List<SocketConnection> socketConnections;
    private LobbyController lobbyController;
    private VirtualView virtualView;

    public SocketServer(int port, VirtualView v){
        try {
            virtualView = v;
            serverSocket = new ServerSocket(port);
            connectionsManager = new ConnectionManager(serverSocket, this, virtualView);
            socketConnections = new ArrayList<>();
            connectionsManager.start();
        } catch(IOException ex) {
        }
    }

    public List<SocketConnection> getSocketConnections() {
        return socketConnections;
    }

    public void addSocketConnection(SocketConnection sc){
        socketConnections.add(sc);
    }

/*
    @Override
    public void removeClient(RMIClientInterface client){

    }

    @Override
    public void send(Message message){
        ObjectOutputStream b;
        Iterator<SocketConnection> clientIterator = socketConnections.iterator();
        while(clientIterator.hasNext()){
            try {
                b = new ObjectOutputStream(clientIterator.next().getConnectionSocket().getOutputStream());
                b.writeObject(message);

            } catch (IOException e) {
                e.printStackTrace();
            }

            }
        }


*/
    /*@Override
    public void loginUser(VCEvent event){
        String user = event.getUsername();

        if(lobbyController.checkUser(user)) {
            lobbyController.addInLobby(user);

            System.out.println("Utente loggato!");
        }
        else{
            System.out.println("Utente non loggato!");
        }

    }*/

    @Override
    public void vceTransport(VCEvent event) throws RemoteException, InvalidConnectionException, InvalidViewException {

    }

    @Override
    public void sendUser(ClientInterface client) throws RemoteException, InvalidViewException {

    }

}
