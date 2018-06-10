package it.polimi.se2018.View.ViewEvents;

import it.polimi.se2018.Model.*;

/**
 * Event that notifies that a die is being selected.
 * @author Ibrahim El Shemy
 * @author Marco Gasperini
 */
public class SelectDieEvent extends VCEvent{

    private Color color;
    private int value;
    private int coordX;
    private int coordY;
    private int position;

    /**
     *
     * @param coordX x coordinate on the Window Card.
     * @param coordY y coordinate on the Window Card.
     * @param value value of the die
     * @param color color of the die
     */
    public SelectDieEvent(String username, int coordX, int coordY, int value, Color color){

        super(username);
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

        super(username);
        this.color = color;
        this.value = value;
        this.position = position;
    }

    /**
     *
     * @param username username of the current player.
     * @param position position of the die in the Draft Pool.
     * @param color color of the die.
     * @param value value of the die.
     */
    public SelectDieEvent(String username, Color color, int value, int position){

        super(username);
        this.color = color;
        this.value = value;
        this.position = position;
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
}
