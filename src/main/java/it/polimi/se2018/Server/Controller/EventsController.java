package it.polimi.se2018.Server.Controller;

import it.polimi.se2018.Client.View.SkipTurnEvent;
import it.polimi.se2018.Client.View.VCEvent;
import it.polimi.se2018.Server.Model.Die;
import it.polimi.se2018.Server.Model.Event.MVEvent;
import it.polimi.se2018.Server.Model.Event.PlaceDieEvent;
import it.polimi.se2018.Server.Model.Game;

public class EventsController extends Controller {

    private Game game;
    private boolean control1;
    private boolean control2;

    private EventsController() {
        super();
    }

    private boolean checkPlayer(String username){

        return (game.getPlayers().indexOf(game.findPlayer(username)) == game.getTurn());

    }

    private boolean checkValidPlacementMove(PlaceDieEvent e){
        control1 = this.checkPlayer(e.getUsername());
        Die die = null;
        for(Die d: game.getRolledDice()) {
            if (d.getValue() == e.getValue() && d.getColor() == e.getColor()) {
                die = d;
                control2 = true;
                break;
            }
        }
        if(!game.findPlayer(e.getUsername()).getWindowCard().checkLegalPlacement(die, e.getCoordX(), e.getCoordY(), true, true))
            return false;
        else
            return (control1 && control2);
    }

    private boolean checkValidSkip(SkipTurnEvent e){
        return this.checkPlayer(e.getUsername());
    }

}
