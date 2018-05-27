package it.polimi.se2018.Client.View;

import it.polimi.se2018.Server.Model.Player;

/**
 * Event that rolls dice
 * @author Ibrahim El Shemy
 * @author Marco Gasperini
 */
public class RollDiceEvent extends VCEvent {

    private boolean pressed;

    /**
     *
     * @param player refers to the current player.
     * @param pressed boolean to identify either a player can roll dice or not
     */
    public RollDiceEvent(Player player, boolean pressed) {

        super(player);
        this.pressed = pressed;
    }

    public boolean isPressed(){

        return pressed;
    }
}
