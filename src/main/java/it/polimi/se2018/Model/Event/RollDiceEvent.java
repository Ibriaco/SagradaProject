package it.polimi.se2018.Model.Event;

import it.polimi.se2018.Model.Die;
import it.polimi.se2018.View.UI.ViewInterface;

import java.util.List;

/**
 * Event that notifies that some dice have been rolled
 * @author Ibrahim El Shemy
 * @author Marco Gasperini
 */
public class RollDiceEvent implements MVEvent{

    private List<Die> dice;
    private String username;


    public List<Die> getDice(){

        return dice;
    }

    @Override
    public void accept(ViewInterface vi) {

    }

    @Override
    public String getUsername() {
        return username;
    }
}
