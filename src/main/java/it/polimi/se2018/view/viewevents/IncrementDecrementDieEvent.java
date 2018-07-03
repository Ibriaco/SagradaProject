package it.polimi.se2018.view.viewevents;

import it.polimi.se2018.controller.ControllerInterface;
import it.polimi.se2018.model.InvalidConnectionException;
import it.polimi.se2018.model.InvalidDieException;
import it.polimi.se2018.model.InvalidViewException;
import org.json.simple.parser.ParseException;

import java.io.IOException;

public class IncrementDecrementDieEvent implements VCEvent {
    private String username;
    private int choice;

    public IncrementDecrementDieEvent(String username, int choice){
        this.choice = choice;
        this.username = username;
    }
    @Override
    public void accept(ControllerInterface controller) throws IOException, InvalidConnectionException, InvalidViewException, ParseException, InvalidDieException {
        controller.handleVCEvent(this);
    }

    public int getChoice() {
        return choice;
    }

    @Override
    public String getUsername() {
        return username;
    }
}
