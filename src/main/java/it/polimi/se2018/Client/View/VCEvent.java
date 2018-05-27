package it.polimi.se2018.Client.View;

import it.polimi.se2018.Server.Model.Game;
import it.polimi.se2018.Server.Model.Player;

/**
 * Class of the events that occur between View and Controller.
 * @author Ibrahim El Shemy
 * @author Marco Gasperini
 */
public abstract class VCEvent {

    private String username;

    /**
     * @param player refers to the current player
     */
    public VCEvent(Player player) {

        this.username = player.getUsername();
    }

    public String getUsername() {

        return username;
    }
}
