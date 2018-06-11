package it.polimi.se2018.Network.server.rmi;

import it.polimi.se2018.Model.InvalidConnectionException;
import it.polimi.se2018.Model.InvalidViewException;
import it.polimi.se2018.Network.LobbyController;
import it.polimi.se2018.Network.client.ClientInterface;
import it.polimi.se2018.Network.server.VirtualView;
import it.polimi.se2018.View.ViewEvents.VCEvent;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * Class that implements RMI Server Interface
 * @author Ibrahim El Shemy
 * @author Marco Gasperini
 */
public class RMIServer extends UnicastRemoteObject implements RMIServerInterface {

    private VirtualView virtualView;
    private static final long serialVersionUID = -7098548671967083832L;

    public RMIServer(VirtualView virtualView)throws RemoteException {
        super(0);
        this.virtualView = virtualView;
    }

    /**
     * Sends a VC (View-Controller) event to the Virtual View
     * @param event event to be sent to the Virtual View
     * @throws InvalidConnectionException thrown exception
     * @throws RemoteException thrown exception
     * @throws InvalidViewException thrown exception
     */
    public void vceTransport(VCEvent event) throws InvalidConnectionException, RemoteException, InvalidViewException {
        virtualView.receiveEvent(event);
    }

    /**
     * Puts into a HashMap a client associated to a specific username
     * @param username username of the player
     * @param client client associated to the player
     */
    public void sendUser(String username, ClientInterface client){
        virtualView.addClientToMap(username,client);
    }



    /*public void startGame() throws InvalidConnectionException, InvalidViewException, RemoteException {
        if(lobbyController.getLobby().getVirtualView().getClients().size() == 4)
            lobbyController.setupGame();
    }*/
}



