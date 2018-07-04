package it.polimi.se2018.view.viewevents;

import it.polimi.se2018.controller.ControllerInterface;
import it.polimi.se2018.model.InvalidConnectionException;
import it.polimi.se2018.model.InvalidViewException;
import it.polimi.se2018.network.client.ClientInterface;
import it.polimi.se2018.org.json.simple.parser.ParseException;

import java.io.IOException;
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
    public void accept(ControllerInterface controller) throws IOException, InvalidConnectionException, InvalidViewException, ParseException {
        controller.handleVCEvent(this);
    }

    public String getUsername() {

        return username;
    }

    @Override
    public String toString() {
        return "Login event";
    }
}
