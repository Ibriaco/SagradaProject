package it.polimi.se2018.Server.Model.Event;


import it.polimi.se2018.Server.Model.Die;

import java.util.ArrayList;

public class CheckRoundTrackEvent extends MVEvent{

    private ArrayList<Die> trackDice;
    private String username;

    public ArrayList<Die> getTrackDice() {
        return trackDice;
    }

    public String getUsername(){
        return username;
    }
}
