package it.polimi.se2018.model.event;

import it.polimi.se2018.model.InvalidConnectionException;
import it.polimi.se2018.model.InvalidViewException;
import it.polimi.se2018.view.ui.ViewInterface;
import org.json.simple.parser.ParseException;

import java.io.IOException;

public class DisconnectedEvent implements MVEvent {

    private String username; //username dell'utente che si è disconnesso

    public DisconnectedEvent(String username){
        this.username = username;
    }
    @Override
    public void accept(ViewInterface vi) throws IOException, InvalidConnectionException, InvalidViewException, ParseException {
        vi.handleMVEvent(this);
    }

    @Override
    public String getUsername() {
        return username;
    }

    public void printDisconnection() {
        System.out.println("user: " + username + " disconnected!");
    }
}
