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
     *
     * @param player refers to the current player.
     * @param pos refers to the position in the RoundCells List
     */
    public CheckRoundTrackEvent(Player player, int pos) {

        super(player);
        this.cellNumber = pos;
    }

    public int getCellNumber() {

        return cellNumber;
    }
}
