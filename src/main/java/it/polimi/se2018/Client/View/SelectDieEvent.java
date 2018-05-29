package it.polimi.se2018.Client.View;

import it.polimi.se2018.Server.Model.*;

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
     * @param coordX refers to the x position on the Window Card.
     * @param coordY refers to the y position on the Window Card.
     * @param value
     * @param color
     */

    // caso in cui seleziono un dado presente sulla Window Card
    public SelectDieEvent(String username, int coordX, int coordY, int value, Color color){

        super(username);
        this.coordX = coordX;
        this.coordY = coordY;
        this.value = value;
        this.color = color;
    }


    /**
     *
     * @param username
     * @param position
     * @param color
     * @param value
     */
    // caso in cui seleziono un dado sul RoundTrack
    public SelectDieEvent(String username, int position, Color color, int value){

        super(username);
        this.color = color;
        this.value = value;
        this.position = position;
    }

    /**
     *
     * @param username
     * @param color
     * @param value
     * @param position
     */
    // caso in cui seleziono un dado dalla DraftPool
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
