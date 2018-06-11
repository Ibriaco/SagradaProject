package it.polimi.se2018.Network.server.rmi;

import it.polimi.se2018.Model.InvalidConnectionException;
import it.polimi.se2018.Model.InvalidViewException;
import it.polimi.se2018.Network.LobbyController;
import it.polimi.se2018.Network.client.ClientInterface;
import it.polimi.se2018.Network.server.VirtualView;
import it.polimi.se2018.View.ViewEvents.VCEvent;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class RMIServer extends UnicastRemoteObject implements RMIServerInterface {

    private LobbyController lobbyController;
    private VirtualView virtualView;
    private static final long serialVersionUID = -7098548671967083832L;

    public RMIServer(LobbyController lc, VirtualView virtualView)throws RemoteException {
        super(0);
        this.virtualView = virtualView;
        this.lobbyController = lc;
    }


    public void vceTransport(VCEvent event) throws InvalidConnectionException, RemoteException, InvalidViewException {
        virtualView.getEvent(event);
    }


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



