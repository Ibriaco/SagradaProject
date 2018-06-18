package it.polimi.se2018.network.server.rmi;

import it.polimi.se2018.model.InvalidConnectionException;
import it.polimi.se2018.model.InvalidViewException;
import it.polimi.se2018.network.client.ClientInterface;
import it.polimi.se2018.network.server.VirtualView;
import it.polimi.se2018.view.viewevents.VCEvent;
import org.json.simple.parser.ParseException;
import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * Class that implements rmi server Interface
 * @author Ibrahim El Shemy
 * @author Marco Gasperini
 */
public class RMIServer extends UnicastRemoteObject implements RMIServerInterface {

    private VirtualView virtualView;
    private static final long serialVersionUID = -7098548671967083832L;

    public RMIServer(VirtualView virtualView)throws RemoteException {
        this.virtualView = virtualView;
    }

    /**
     * Sends a VC (view-controller) event to the Virtual view
     * @param event event to be sent to the Virtual view
     * @throws RemoteException thrown exception
     */
    public void vceTransport(VCEvent event) throws IOException, ParseException, InvalidConnectionException, InvalidViewException {
        virtualView.receiveEvent(event);
    }

    /**
     * Puts into a HashMap a client associated to a specific username

     * @param client client associated to the player
     */
    public void sendUser(ClientInterface client){

        virtualView.setClientTemp(client);
    }
}



