package it.polimi.se2018.view.viewevents;

import it.polimi.se2018.controller.ControllerInterface;
import it.polimi.se2018.model.Color;
import it.polimi.se2018.model.InvalidConnectionException;
import it.polimi.se2018.model.InvalidDieException;
import it.polimi.se2018.model.InvalidViewException;
import org.json.simple.parser.ParseException;

import java.io.IOException;

/**
 * event that notifies that a die is being selected.
 * @author Ibrahim El Shemy
 * @author Marco Gasperini
 */
public class SelectDieEvent implements VCEvent{

    private Color color;
    private int value;
    private int coordX;
    private int coordY;
    private int position;
    private String username;

    /**
     *
     * @param coordX x coordinate on the Window Card.
     * @param coordY y coordinate on the Window Card.
     * @param value value of the die
     * @param color color of the die
     */
    public SelectDieEvent(String username, int coordX, int coordY, int value, Color color){

        this.username = username;
        this.coordX = coordX;
        this.coordY = coordY;
        this.value = value;
        this.color = color;
    }


    /**
     *
     * @param username username of the current player.
     * @param position position of the die on the Round Track.
     * @param color color of the die.
     * @param value value of the die.
     */
    public SelectDieEvent(String username, int position, Color color, int value){

        this.username = username;
        this.color = color;
        this.value = value;
        this.position = position;
    }

    /**
     *
     * @param username username of the current player.
     * @param position position of the die in the Draft Pool.
     */
    public SelectDieEvent(String username,int position){

        this.username = username;
        this.position = position;
    }

    public String getUsername(){
        return username;
    }

    public Color getColor() {

        return color;
    }

    public int getValue() {

        return value;
    }

    public int getCoordX() {

        return coordX;
    }

    public int getCoordY() {

        return coordY;
    }

    public int getPosition(){

        return position;
    }

    @Override
    public void accept(ControllerInterface controller) throws InvalidConnectionException, ParseException, InvalidViewException, IOException, InvalidDieException {
        controller.handleVCEvent(this);
    }
}
