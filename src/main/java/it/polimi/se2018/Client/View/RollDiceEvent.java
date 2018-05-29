package it.polimi.se2018.Client.View;

import it.polimi.se2018.Server.Model.Player;

/**
 * Event that rolls dice
 * @author Ibrahim El Shemy
 * @author Marco Gasperini
 */
public class RollDiceEvent extends VCEvent {

    /**
     *
     * @param username username of the current player.
     */
    public RollDiceEvent(String username) {
        super(username);
    }
}
