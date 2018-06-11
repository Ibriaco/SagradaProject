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
 * Class that represents the implementation of the RMI Server
 * @author Ibrahim El Shemy
 */
public class RMIServer extends UnicastRemoteObject implements RMIServerInterface {

    private LobbyController lobbyController;
    private VirtualView virtualView;
    private static final long serialVersionUID = -7098548671967083832L;

    public RMIServer(LobbyController lc, VirtualView virtualView)throws RemoteException {
        super(0);
        this.virtualView = virtualView;
        this.lobbyController = lc;
    }

    /**
     * Send a VC (View-Controller) event to the Virtual View
     * @param event to be sent to the Virtual View
     * @throws InvalidConnectionException exception thrown
     * @throws RemoteException exception thrown
     * @throws InvalidViewException exception thrown
     */
    public void vceTransport(VCEvent event) throws InvalidConnectionException, RemoteException, InvalidViewException {
        virtualView.getEvent(event);
    }

    /**
     * Puts into a HashMap a client associated to a specific username
     * @param username of the player
     * @param client associated to the player
     */
    public void sendUser(String username, ClientInterface client){
        lobbyController.getLobby().getVirtualView().getClients().put(username,client);
    }


    public LobbyController getLobbyController() {

        return lobbyController;
    }

    /*public void startGame() throws InvalidConnectionException, InvalidViewException, RemoteException {
        if(lobbyController.getLobby().getVirtualView().getClients().size() == 4)
            lobbyController.setupGame();
    }*/
}



