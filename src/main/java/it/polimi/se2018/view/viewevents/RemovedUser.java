package it.polimi.se2018.view.viewevents;

import it.polimi.se2018.controller.ControllerInterface;
import it.polimi.se2018.model.InvalidConnectionException;
import it.polimi.se2018.model.InvalidDieException;
import it.polimi.se2018.model.InvalidViewException;
import it.polimi.se2018.org.json.simple.parser.ParseException;

import java.io.IOException;

public class RemovedUser implements VCEvent {

    private String username;

    public RemovedUser(String username) {
        this.username = username;
    }

    @Override
    public void accept(ControllerInterface controller) throws IOException, InvalidConnectionException, InvalidViewException, ParseException, InvalidDieException {

    }

    @Override
    public String getUsername() {
        return null;
    }
}
