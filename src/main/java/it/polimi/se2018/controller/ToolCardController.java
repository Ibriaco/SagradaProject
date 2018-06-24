package it.polimi.se2018.controller;

import it.polimi.se2018.model.Die;
import it.polimi.se2018.model.Game;
import it.polimi.se2018.model.ToolCard;
import it.polimi.se2018.view.viewevents.SelectDieEvent;
import it.polimi.se2018.view.viewevents.UseToolEvent;

import java.util.ArrayList;

public class ToolCardController {

    private ArrayList<ToolCard> toolCards;
    private Game game;

    public ToolCardController() {
        //still needs to be implemented
    }

    /*public boolean checkValidToolCardUse(UseToolCardEvent e){
        return false;
    }*/

   // public Die selectDie(){
     //   return Die;
    //}

    public boolean checkExistingDie(Die d){
        return false;
    }

    public boolean checkApplicableEffect(Die d){
        return false;
    }

    public void handleVCEvent(UseToolEvent event) {
        String user = event.getUsername();
        int pos = event.getToolCardNumber();

    }
}
