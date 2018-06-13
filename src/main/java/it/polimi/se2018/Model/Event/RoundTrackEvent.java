package it.polimi.se2018.Model.Event;


import it.polimi.se2018.Model.Die;
import it.polimi.se2018.View.UI.ViewInterface;

import java.util.List;

/**
 * Event that notifies that a player wants to see the Round Track
 * @author Ibrahim El Shemy
 * @author Marco Gasperini
 */
public class RoundTrackEvent implements MVEvent{

    private List<Die> trackDice;
    private String username;


    public List<Die> getTrackDice() {

        return trackDice;
    }

    @Override
    public void accept(ViewInterface vi) {

    }

    public String getUsername(){

        return username;
    }
}

