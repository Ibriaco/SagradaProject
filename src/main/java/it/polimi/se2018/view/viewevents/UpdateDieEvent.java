package it.polimi.se2018.view.viewevents;

import it.polimi.se2018.controller.ControllerInterface;
import it.polimi.se2018.model.InvalidConnectionException;
import it.polimi.se2018.model.InvalidDieException;
import it.polimi.se2018.model.InvalidViewException;
import it.polimi.se2018.org.json.simple.parser.ParseException;

import java.io.IOException;

public class UpdateDieEvent implements VCEvent {
    private String username;
    private int val;
    private int pos;


    public UpdateDieEvent(String user, int val, int pos) {
        this.username = user;
        this.val = val;
        this.pos = pos;
    }

    @Override
    public void accept(ControllerInterface controller) throws IOException, InvalidConnectionException, InvalidViewException, ParseException, InvalidDieException {
        controller.handleVCEvent(this);

    }

    @Override
    public String getUsername() {
        return username;
    }

    public int getVal() {
        return val;
    }

    public int getPos() {
        return pos;
    }

}
