package it.polimi.se2018.Model.Event;

import it.polimi.se2018.Model.Die;

import java.util.ArrayList;

public class RollDiceEvent extends MVEvent{

    private ArrayList<Die> dice;

    public ArrayList<Die> getDice(){
        return dice;
    }
}
