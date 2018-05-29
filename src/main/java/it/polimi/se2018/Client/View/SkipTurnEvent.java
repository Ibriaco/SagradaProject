package it.polimi.se2018.Client.View;

import it.polimi.se2018.Server.Model.Game;
import it.polimi.se2018.Server.Model.Player;

/**
 * Event that notifies that a turn is skipped.
 * @author Ibrahim El Shemy
 * @author Marco Gasperini
 */
public class SkipTurnEvent extends VCEvent {

    /**
     * @param username username of the player
     */
    public SkipTurnEvent(String username) {
        super(username);
    }
}
