package it.polimi.se2018.Controller;

import it.polimi.se2018.Controller.ToolCard;
import it.polimi.se2018.Model.Color;
import it.polimi.se2018.Model.Die;
import it.polimi.se2018.Model.Player;
import it.polimi.se2018.Model.RoundCell;

public class StraightEdge extends ToolCard {
    public StraightEdge(int number, String title, String description, String cardType, boolean used, int cost, Color color, int shade) {
        super(number, title, description, cardType, used, cost, color, shade);
    }

    @Override
    public void applyEffect(Player p, Die d, RoundCell r) {
        super.applyEffect(p, d, r);
    }
}
