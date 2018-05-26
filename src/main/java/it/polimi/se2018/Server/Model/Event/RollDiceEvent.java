package it.polimi.se2018.Server.Model.Event;

import it.polimi.se2018.Server.Model.Die;
import it.polimi.se2018.Server.Model.Game;


import java.util.ArrayList;
import java.util.List;

/**
 * Event that notifies that some dice have been rolled
 * @author Ibrahim El Shemy
 * @author Marco Gasperini
 */
public class RollDiceEvent extends MVEvent{

    private List<Die> dice;
    Game game;

    /**
     *
     * @param game refers to the actual game
     */
    public RollDiceEvent(Game game) {
        super(game);
        this.game = game;
        this.dice = game.getRolledDice();
    }

    public List<Die> getDice(){

        return dice;
    }

}
