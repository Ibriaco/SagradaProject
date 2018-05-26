package it.polimi.se2018.Server.Model.Event;

import it.polimi.se2018.Server.Model.Player;

/**
 *
 * @author Ibrahim El Shemy
 * @author Marco Gasperini
 */
public class LoggedUserEvent extends MVEvent {

    private String username;

    /**
     *
     * @param player refers to the current player in game
     */
    public LoggedUserEvent(Player player) {
        super(player);
        this.username = player.getUsername();
    }

    public String getUsername() {

        return username;
    }

}
