package it.polimi.se2018.Controller;

import it.polimi.se2018.Model.Event.MVEvent;
import it.polimi.se2018.Model.Event.PlaceDieEvent;

public class EventsController {

    //ho un dubbio sul tipo di parametro passato a questo metodo
    public boolean checkLegalEvent(MVEvent e){

        return false;
    }

    public boolean checkValidPlacementMove(PlaceDieEvent e){

        return false;
    }

    public boolean checkValidSkip(SkipTurnEvent e){

        return false;
    }

    public void seePlayerWindow(SeePlayerWindowEvent e){

    }
}
