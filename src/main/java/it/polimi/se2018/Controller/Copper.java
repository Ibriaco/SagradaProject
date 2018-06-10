package it.polimi.se2018.Controller;


import it.polimi.se2018.View.ViewEvents.PlaceDieEvent;
import it.polimi.se2018.View.ViewEvents.SelectDieEvent;
import it.polimi.se2018.Model.Color;
import it.polimi.se2018.Model.Die;
import it.polimi.se2018.Model.Player;


//Muovi  un  qualsiasi  dado  nella  tua  vetrata  ignorando  le  restrizioni  di  valore. Devi  rispettare
// tutte  le  altre  restrizioni  di  piazzamento

public class Copper extends ToolCard {
    public Copper(int number, String title, String description, boolean used, int cost, Color color, int shade) {
        super(number, title, description, used, cost, color, shade);
    }
    protected void applyEffect(Player p, PlaceDieEvent placeDieE, SelectDieEvent selectDieE){
        Die d = p.getWindowCard().getGridCell(selectDieE.getCoordX(),selectDieE.getCoordY()).getPlacedDie();
        p.getWindowCard().removeDie( selectDieE.getCoordX(),selectDieE.getCoordY());
        p.getWindowCard().placeDie(d, placeDieE.getCoordX(), placeDieE.getCoordY(),true,false);

    }

}
