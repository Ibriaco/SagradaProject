package it.polimi.se2018.model.event;

import it.polimi.se2018.model.InvalidConnectionException;
import it.polimi.se2018.model.InvalidDieException;
import it.polimi.se2018.model.InvalidViewException;
import it.polimi.se2018.org.json.simple.parser.ParseException;
import it.polimi.se2018.view.ui.ViewInterface;

import java.io.IOException;

public class IsTurnEvent implements MVEvent {
    private String username;
    private String playerInTurn;
    private String user;

    private boolean connected;

    public IsTurnEvent (String playerInTurn, boolean connected){
        this.playerInTurn = playerInTurn;
        this.connected = connected;
        user = playerInTurn;
        username = playerInTurn;
    }


    @Override
    public void accept(ViewInterface vi) throws IOException, InvalidConnectionException, InvalidViewException, ParseException, InvalidDieException {
        vi.handleMVEvent(this);
    }

    @Override
    public String getUsername() {
        return username;
    }

    public boolean isConnected() {
        return connected;
    }

    public String getUser() {
        return user;
    }

    public void printPlayerInTurn(){
        System.out.println("\nE' IL TURNO DI: " + playerInTurn);
    }
}
