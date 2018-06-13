package it.polimi.se2018.View.ViewEvents;

import it.polimi.se2018.Controller.ControllerInterface;
import it.polimi.se2018.Model.InvalidConnectionException;
import it.polimi.se2018.Model.InvalidViewException;
import it.polimi.se2018.Model.WindowCardAssociationException;
import it.polimi.se2018.Network.Client.ClientInterface;

import java.rmi.RemoteException;

/**
 * Events that return connection type and the username of the player who logs in.
 * @author Ibrahim El Shemy
 * @author Marco Gasperini
 */
public class LoginEvent implements VCEvent {

    private String username;


    private ClientInterface client;

    public LoginEvent (String username) {
        this.username = username;

    }

    public ClientInterface getClient(){
        return client;
    }

    public void setClient(ClientInterface client) {
        this.client = client;
    }

    @Override
    public void accept(ControllerInterface controller) throws RemoteException, InvalidConnectionException, WindowCardAssociationException, InvalidViewException {
        controller.handleVCEvent(this);
    }

    public String getUsername() {

        return username;
    }

    @Override
    public String toString() {
        return "Login Event";
    }
}
