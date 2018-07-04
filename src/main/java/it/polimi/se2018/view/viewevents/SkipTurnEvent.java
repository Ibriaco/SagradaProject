package it.polimi.se2018.view.viewevents;

import it.polimi.se2018.controller.ControllerInterface;
import it.polimi.se2018.model.InvalidConnectionException;
import it.polimi.se2018.model.InvalidViewException;
import it.polimi.se2018.org.json.simple.parser.ParseException;

import java.io.IOException;

/**
 * event that notifies that a turn is skipped.
 * @author Ibrahim El Shemy
 * @author Marco Gasperini
 */
public class SkipTurnEvent implements VCEvent {


    private String username;

    public SkipTurnEvent(String username) {

        this.username = username;
    }

    public String getUsername() {

        return username;
    }

    @Override
    public void accept(ControllerInterface controller) throws InvalidConnectionException, InvalidViewException, ParseException, IOException {
        controller.handleVCEvent(this);
    }
}
