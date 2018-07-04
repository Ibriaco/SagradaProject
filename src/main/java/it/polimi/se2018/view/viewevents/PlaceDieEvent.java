package it.polimi.se2018.view.viewevents;


import it.polimi.se2018.controller.ControllerInterface;
import it.polimi.se2018.model.InvalidConnectionException;
import it.polimi.se2018.model.InvalidViewException;
import it.polimi.se2018.org.json.simple.parser.ParseException;

import java.io.IOException;

/**
 * Events that lets the player place a die
 * @author Ibrahim El Shemy
 * @author Marco Gasperini
 */
public class PlaceDieEvent implements VCEvent {


    private int pos;
    private int coordX;
    private int coordY;
    private String username;

    /**
     *
     * @param username username of the current player
     * @param pos is the position of die in the DraftPool
     * @param x x coordinate where the player wants to place the die
     * @param y y coordinate where the player wants to place the die
     */
    public PlaceDieEvent(String username, int pos, int x, int y) {

        this.username = username;
        this.pos = pos;
        this.coordX = x;
        this.coordY = y;
    }

    public String getUsername(){
        return username;
    }

    @Override
    public void accept(ControllerInterface controller) throws InvalidConnectionException, InvalidViewException, ParseException, IOException {
        controller.handleVCEvent(this);
    }

    public int getPos(){

        return pos;
    }

    public int getCoordX() {

        return coordX;
    }

    public int getCoordY() {

        return coordY;
    }
}
