package it.polimi.se2018.Server.Controller;

import it.polimi.se2018.Server.Model.Event.MVEvent;
import it.polimi.se2018.Server.Model.Event.PlaceDieEvent;

public class EventsController {

    //ho un dubbio sul tipo di parametro passato a questo metodo
    public boolean checkLegalEvent(MVEvent e){

        return false;
    }

    public boolean checkValidPlacementMove(PlaceDieEvent e){

        return false;
    }

    /*public boolean checkValidSkip(SkipTurnEvent e){

        return false;
    }

    public void seePlayerWindow(SeePlayerWindowEvent e){

    }
    */
}
