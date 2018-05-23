package it.polimi.se2018.Server.Controller;


import it.polimi.se2018.Client.View.PlaceDieEvent;
import it.polimi.se2018.Client.View.SelectDieEvent;
import it.polimi.se2018.Server.Model.Color;
import it.polimi.se2018.Server.Model.Die;
import it.polimi.se2018.Server.Model.Player;
//muovi esattamente 2 dadi rispettando tutte le restrizioni

public class Lathekin extends ToolCard {
    public Lathekin(int number, String title, String description, boolean used, int cost, Color color, int shade) {
        super(number, title, description, used, cost, color, shade);
    }
    protected void applyEffect(Player p, PlaceDieEvent placeDieE1, PlaceDieEvent placeDieE2, SelectDieEvent selectDieE1, SelectDieEvent selectDieE2){
        //selectDieE1 è l'evento del dado selezionato che va spostato
        //placeDieE1 è l'evento del dado spostato nella nuova cella
        Die d = p.getWindowCard().getGridCell(selectDieE1.getCoordX(),selectDieE1.getCoordY()).getPlacedDie();
        p.getWindowCard().removeDie( selectDieE1.getCoordX(), selectDieE1.getCoordY());
        p.getWindowCard().placeDie(d, placeDieE1.getCoordX(), placeDieE1.getCoordY(),true,true);

        d = p.getWindowCard().getGridCell(selectDieE2.getCoordX(),selectDieE2.getCoordY()).getPlacedDie();
        p.getWindowCard().removeDie( selectDieE2.getCoordX(), selectDieE2.getCoordY());
        p.getWindowCard().placeDie(d, placeDieE2.getCoordX(), placeDieE2.getCoordY(),true,true);

    }
}
