package it.polimi.se2018.model.event;


import it.polimi.se2018.model.Die;
import it.polimi.se2018.view.ui.ViewInterface;

import java.util.List;

/**
 * event that notifies that a player wants to see the Round Track
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

