package it.polimi.se2018.Network.Server.RMI;

import it.polimi.se2018.Model.InvalidConnectionException;
import it.polimi.se2018.Model.InvalidViewException;
import it.polimi.se2018.Model.WindowCardAssociationException;
import it.polimi.se2018.Network.Client.ClientInterface;
import it.polimi.se2018.Network.Server.VirtualView;
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
        //super(0);
        this.virtualView = virtualView;
    }

    /**
     * Sends a VC (View-Controller) event to the Virtual View
     * @param event event to be sent to the Virtual View
     * @throws InvalidConnectionException thrown exception
     * @throws RemoteException thrown exception
     * @throws InvalidViewException thrown exception
     */
    public void vceTransport(VCEvent event) throws InvalidConnectionException, RemoteException, InvalidViewException, WindowCardAssociationException {
        virtualView.receiveEvent(event);
    }

    /**
     * Puts into a HashMap a Client associated to a specific username

     * @param client Client associated to the player
     */
    public void sendUser(ClientInterface client){
        virtualView.setClientTemp(client);
    }



    /*public void startGame() throws InvalidConnectionException, InvalidViewException, RemoteException {
        if(lobbyController.getLobby().getVirtualView().getClients().size() == 4)
            lobbyController.setupGame();
    }*/
}



