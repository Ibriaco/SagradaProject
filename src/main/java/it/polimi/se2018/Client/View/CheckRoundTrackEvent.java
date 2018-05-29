package it.polimi.se2018.Client.View;

import it.polimi.se2018.Server.Model.Game;
import it.polimi.se2018.Server.Model.Player;

/**
 * Event that returns the number of the cell of the RoundTrack
 * @author Ibrahim El Shemy
 * @author Marco Gasperini
 */
public class CheckRoundTrackEvent extends VCEvent {

    int cellNumber;

    /**
     * @param username username of the current player.
     * @param pos refers to the position in the RoundCells List
     */
    public CheckRoundTrackEvent(String username, int pos) {

        super(username);
        this.cellNumber = pos;
    }

    public int getCellNumber() {

        return cellNumber;
    }
}
