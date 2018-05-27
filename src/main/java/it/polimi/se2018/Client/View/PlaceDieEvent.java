package it.polimi.se2018.Client.View;


import it.polimi.se2018.Server.Model.Color;
import it.polimi.se2018.Server.Model.Die;
import it.polimi.se2018.Server.Model.Player;

/**
 * Events that lets the player place a die
 * @author Ibrahim El Shemy
 * @author Marco Gasperini
 */
public class PlaceDieEvent extends VCEvent {

    private Color color;
    private int value;
    private int coordX;
    private int coordY;

    /**
     *
     * @param player refers to the current player
     * @param color refers to the color of the die
     * @param value refers to the value of the die
     * @param x refers to the x coordinate where the player wants to place the die
     * @param y refers to the y coordinate where the player wants to place the die
     */
    public PlaceDieEvent(Player player, Color color, int value, int x, int y) {

        super(player);
        this.color = color;
        this.value = value;
        this.coordX = x;
        this.coordY = y;
    }


    public Color getColor(){

        return color;
    }

    public int getValue(){

        return value;
    }

    public int getCoordX() {

        return coordX;
    }

    public int getCoordY() {

        return coordY;
    }
}
