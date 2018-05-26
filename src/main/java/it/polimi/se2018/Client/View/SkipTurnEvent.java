package it.polimi.se2018.Client.View;

import it.polimi.se2018.Server.Model.Game;
import it.polimi.se2018.Server.Model.Player;

/**
 * Event that notifies that a turn is skipped.
 * @author Ibrahim El Shemy
 * @author Marco Gasperini
 */
public class SkipTurnEvent extends VCEvent {
    int turn;

    /**
     *
     * @param turn refers to the current turn of the game.
     * @param player refers to the current player.
     * @param game referst to the actual game.
     */
    public SkipTurnEvent(int turn, Player player, Game game) {
        super(player);
        this.turn = game.getTurn();
    }

    public int getTurn(){
        return turn;
    }
}
