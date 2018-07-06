package it.polimi.se2018.view.viewevents;

import it.polimi.se2018.controller.ControllerInterface;
import it.polimi.se2018.model.InvalidConnectionException;
import it.polimi.se2018.model.InvalidDieException;
import it.polimi.se2018.model.InvalidViewException;
import it.polimi.se2018.org.json.simple.parser.ParseException;

import java.io.IOException;

public class PlaceDieWithRestriction implements VCEvent {
    private String username;
    private boolean color;
    private boolean shade;
    private boolean around;
    private int col;
    private int row;

    public PlaceDieWithRestriction(String username, int x, int y,boolean color, boolean shade, boolean around){
        this.username = username;
        this.color = color;
        this.shade = shade;
        this.around = around;
        col = x;
        row = y;
    }

    @Override
    public void accept(ControllerInterface controller) throws IOException, InvalidConnectionException, InvalidViewException, ParseException, InvalidDieException {
        controller.handleVCEvent(this);
    }

    @Override
    public String getUsername() {
        return username;
    }


    public boolean isColor() {
        return color;
    }

    public boolean isShade() {
        return shade;
    }

    public boolean isAround() {
        return around;
    }

    public int getCol() {
        return col;
    }

    public int getRow() {
        return row;
    }
}
