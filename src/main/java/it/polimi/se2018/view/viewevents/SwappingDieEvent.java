package it.polimi.se2018.view.viewevents;

import it.polimi.se2018.controller.ControllerInterface;
import it.polimi.se2018.model.InvalidConnectionException;
import it.polimi.se2018.model.InvalidDieException;
import it.polimi.se2018.model.InvalidViewException;
import it.polimi.se2018.org.json.simple.parser.ParseException;

import java.io.IOException;

public class SwappingDieEvent implements VCEvent {

    private String username;
    private int roundPos;
    private int cellPos;

    public SwappingDieEvent(String user, int roundPos, int cellPos) {
        this.username = user;
        this.roundPos = roundPos;
        this.cellPos = cellPos;
    }

    @Override
    public void accept(ControllerInterface controller) throws IOException, InvalidConnectionException, InvalidViewException, ParseException, InvalidDieException {
        controller.handleVCEvent(this);
    }

    @Override
    public String getUsername() {
        return username;
    }


    public int getRoundPos() {
        return roundPos;
    }

    public int getCellPos() {
        return cellPos;
    }

}
