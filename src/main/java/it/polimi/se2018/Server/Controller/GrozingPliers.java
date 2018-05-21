package it.polimi.se2018.Server.Controller;

import it.polimi.se2018.Server.Model.Die;
import it.polimi.se2018.Server.Model.Color;
import it.polimi.se2018.Server.Model.InvalidDieException;
//Dopo  aver  scelto  un  dado,  aumenta  o  dominuisci  il  valore  del  dado  scelto  di  1

public class GrozingPliers extends ToolCard {
    public GrozingPliers(int number, String title, String description, boolean used, int cost, Color color, int shade) {
        super(number, title, description, used, cost, color, shade);
    }

    protected void applyEffect(Die d, String s){
        if(d.getValue()>1 && d.getValue()<6){
            if (s.equals("up")) {
                try {
                    d.setValue(d.getValue() + 1);
                } catch (InvalidDieException e) {
                    e.printStackTrace();
                }
            }
            if (s.equals("down")) {
                try {
                    d.setValue(d.getValue() - 1);
                } catch (InvalidDieException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
