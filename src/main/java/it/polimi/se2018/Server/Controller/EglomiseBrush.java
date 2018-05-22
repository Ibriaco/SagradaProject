package it.polimi.se2018.Server.Controller;

import it.polimi.se2018.Server.Model.Color;
import it.polimi.se2018.Server.Model.Die;
import it.polimi.se2018.Server.Model.InvalidDieException;
import it.polimi.se2018.Server.Model.Player;
import it.polimi.se2018.Client.View.PlaceDieEvent;
//Muovi  un  qualsiasi  dado  nella  tua  vetrata  ignorando  le  restrizioni  di  colore. Devi  rispettare
// tutte  le  altre  restrizioni  di  piazzamento

public class EglomiseBrush extends ToolCard {
    public EglomiseBrush(int number, String title, String description, boolean used, int cost, Color color, int shade) {
        super(number, title, description, used, cost, color, shade);
    }
    protected void applyEffect(Player p, PlaceDieEvent placeDieE, int x, int y){
        //X e Y sono le coordinate del dado da spostare, non quelle dove verrà posizionato
        Die d = p.getWindowCard().getGridCell(placeDieE.getCoordX(),placeDieE.getCoordY()).getPlacedDie();
        p.getWindowCard().removeDie( x, y);
        p.getWindowCard().placeDie(d, placeDieE.getCoordX(), placeDieE.getCoordY(),false,true);

    }

}
