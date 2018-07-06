package it.polimi.se2018.view.viewevents;

import it.polimi.se2018.controller.ControllerInterface;
import it.polimi.se2018.model.Color;
import it.polimi.se2018.model.InvalidConnectionException;
import it.polimi.se2018.model.InvalidDieException;
import it.polimi.se2018.model.InvalidViewException;
import it.polimi.se2018.org.json.simple.parser.ParseException;

import java.io.IOException;

public class ColorAndNumberEvent implements VCEvent {
    private String username;
    private int position;
    private int positionRoundTrack;
    private int number;
    private int oldCol;
    private int oldRow;
    private int newCol;
    private int newRow;

    public ColorAndNumberEvent(String username,int positionRoundTrack, int position, int number, int oldCol, int oldRow, int newCol, int newRow){
        this.username = username;
        this.position = position;
        this.positionRoundTrack = positionRoundTrack;
        this.number = number;
        this.oldCol = oldCol;
        this.oldRow = oldRow;
        this.newCol = newCol;
        this.newRow = newRow;
    }

    @Override
    public void accept(ControllerInterface controller) throws IOException, InvalidConnectionException, InvalidViewException, ParseException, InvalidDieException {
        controller.handleVCEvent(this);
    }

    @Override
    public String getUsername() {
        return username;
    }
    public int getPosition() {
        return position;
    }
    public int getNumber() {
        return number;
    }
    public int getOldCol() {
        return oldCol;
    }

    public int getOldRow() {
        return oldRow;
    }

    public int getNewCol() {
        return newCol;
    }

    public int getPositionRoundTrack() {
        return positionRoundTrack;
    }


    public int getNewRow() {
        return newRow;
    }
}
