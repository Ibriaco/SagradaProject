package it.polimi.se2018.Controller;

import it.polimi.se2018.Controller.ToolCard;
import it.polimi.se2018.Model.Color;
import it.polimi.se2018.Model.Die;
import it.polimi.se2018.Model.Player;
import it.polimi.se2018.Model.RoundCell;

public class EglomiseBrush extends ToolCard {
    public EglomiseBrush(int number, String title, String description, boolean used, int cost, Color color, int shade) {
        super(number, title, description, used, cost, color, shade);
    }

    @Override
    public void applyEffect(Player p, Die d, RoundCell r) {
        super.applyEffect(p, d, r);
    }
}
