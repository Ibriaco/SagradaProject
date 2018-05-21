package it.polimi.se2018.Server.Model.Event;

import it.polimi.se2018.Server.Model.Color;


/**
 * Event that notifies that a Die has been placed.
 */
public class PlaceDieEvent extends MVEvent{


    private Color color;
    private int value;
    private String username;
    private int coordX;
    private int coordY;

    public PlaceDieEvent(int coordX, int coordY) {
        super();
        this.coordX = coordX;
        this.coordY = coordY;
    }

    public Color getColor() {
        return color;
    }

    public int getValue() {
        return value;
    }

    public String getUsername() {
        return username;
    }

    public int getCoordX() {

        return coordX;
    }

    public int getCoordY() {

        return coordY;
    }
}
