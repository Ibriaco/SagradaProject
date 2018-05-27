package it.polimi.se2018.Client.View;

import it.polimi.se2018.Server.Model.Game;
import it.polimi.se2018.Server.Model.Player;

/**
 * Event that notifies that a turn is skipped.
 * @author Ibrahim El Shemy
 * @author Marco Gasperini
 */
public class SkipTurnEvent extends VCEvent {
    private int turn;
    private int round;

    /**
     *
     * @param turn refers to the current turn of the game.
     * @param player refers to the current player.
     * @param game referst to the actual game.
     */
    public SkipTurnEvent(int turn, Player player, Game game) {
        super(player);
        this.turn = game.getTurn();
        this.round = game.getRound();
    }

    public int getTurn(){

        return turn;
    }

    public int getRound(){

        return round;
    }
}
