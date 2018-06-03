package it.polimi.se2018.Model.Event;

import it.polimi.se2018.Model.Color;
import it.polimi.se2018.Model.Die;
import it.polimi.se2018.Model.Game;
import it.polimi.se2018.Model.Player;


/**
 * Event that notifies that a Die has been placed.
 * @author Ibrahim El Shemy
 * @author Marco Gasperini
 */
public class PlaceDieEvent extends MVEvent{


    private Color color;
    private int value;
    private String username;
    private int coordX;
    private int coordY;

    /**
     *
     * @param coordX row position where the die is placed
     * @param coordY column position where the die is placed
     * @param game refers to the actual game
     * @param player refers to the current player who places the die
     * @param die refers to the object that is getting placed
     */
    public PlaceDieEvent(int coordX, int coordY, Game game, Player player, Die die) {
        super(game, player, die);
        this.coordX = coordX;
        this.coordY = coordY;
        this.username = player.getUsername();
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
