package it.polimi.se2018.view.viewevents;

import it.polimi.se2018.controller.ControllerInterface;
import it.polimi.se2018.model.InvalidConnectionException;
import it.polimi.se2018.model.InvalidViewException;
import it.polimi.se2018.org.json.simple.parser.ParseException;

import java.io.IOException;

/**
 * event that rolls dice
 * @author Ibrahim El Shemy
 * @author Marco Gasperini
 */
public class RollDiceEvent implements VCEvent {

    private String username;

    public RollDiceEvent(String username) {
        this.username = username;
    }

    public String getUsername(){
        return username;
    }
    @Override
    public void accept(ControllerInterface controller) throws InvalidConnectionException, ParseException, InvalidViewException, IOException {
        controller.handleVCEvent(this);
    }
}
