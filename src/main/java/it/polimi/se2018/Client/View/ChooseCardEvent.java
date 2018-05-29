package it.polimi.se2018.Client.View;

import it.polimi.se2018.Server.Model.Player;

/**
 * Event that returns the number and the side of a chosen Card by the player
 * @author Ibrahim El Shemy
 * @author Marco Gasperini
 */
public class ChooseCardEvent extends VCEvent {

    private int windowNumber;
    private int side;

    /**
     *
     * @param username username of the current player.
     * @param windowNumber refers to the number of the Card
     * @param side refers to the side of the Card
     */
    public ChooseCardEvent(String username, int windowNumber, int side) {

        super(username);
        this.windowNumber = windowNumber;
        this.side = side;
    }

    public int getWindowNumber() {

        return windowNumber;
    }

    public int getSide() {

        return side;
    }
}
