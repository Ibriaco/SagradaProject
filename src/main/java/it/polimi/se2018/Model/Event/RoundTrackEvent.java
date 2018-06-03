package it.polimi.se2018.Model.Event;


import it.polimi.se2018.Model.Die;
import it.polimi.se2018.Model.Game;
import it.polimi.se2018.Model.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * Event that notifies that a player wants to see the Round Track
 * @author Ibrahim El Shemy
 * @author Marco Gasperini
 */
public class RoundTrackEvent extends MVEvent{

    private List<Die> trackDice;
    private String username;

    /**
     *
     * @param game refers to the actual game
     * @param player refers to the current player
     * @param roundNumber refers to the round number of the game
     */
    public RoundTrackEvent(Game game, Player player, int roundNumber) {
        super(game, player);
        this.trackDice = game.getRoundCells().get(roundNumber).getDiceList();
        this.username = player.getUsername();
    }

    public List<Die> getTrackDice() {

        return trackDice;
    }

    public String getUsername(){

        return username;
    }
}

