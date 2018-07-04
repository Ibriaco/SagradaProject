package it.polimi.se2018.view.viewevents;

import it.polimi.se2018.controller.ControllerInterface;
import it.polimi.se2018.model.InvalidConnectionException;
import it.polimi.se2018.model.InvalidDieException;
import it.polimi.se2018.model.InvalidViewException;
import it.polimi.se2018.org.json.simple.parser.ParseException;

import java.io.IOException;

public class MovingDieEvent implements VCEvent {

    private String username;
    private int oldX;
    private int oldY;
    private int newX;
    private int newY;

    public MovingDieEvent(String username, int oldX, int oldY, int newX, int newY) {
        this.username = username;
        this.oldX = oldX;
        this.oldY = oldY;
        this.newX = newX;
        this.newY = newY;
    }

    @Override
    public void accept(ControllerInterface controller) throws IOException, InvalidConnectionException, InvalidViewException, ParseException, InvalidDieException {
        controller.handleVCEvent(this);
    }

    @Override
    public String getUsername() {
        return username;
    }



    public int getOldX() {
        return oldX;
    }

    public int getOldY() {
        return oldY;
    }

    public int getNewX() {
        return newX;
    }

    public int getNewY() {
        return newY;
    }


}
