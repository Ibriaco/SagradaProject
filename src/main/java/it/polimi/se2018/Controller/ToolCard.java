package it.polimi.se2018.Controller;

import it.polimi.se2018.Model.*;

public abstract class ToolCard extends Card {
    private boolean used;
    private int cost;
    private Color color;
    private int shade;

    public ToolCard(int number, String title, String description, boolean used, int cost, Color color, int shade) {
        super(number, title, description);
        this.used = used;
        this.cost = cost;
        this.color = color;
        this.shade = shade;
    }

    public boolean isUsed() {
        return used;
    }

    public int getCost() {
        return cost;
    }

    public Color getColor() {
        return color;
    }

    public int getShade() {
        return shade;
    }

    public void applyEffect(Player p, Die d, RoundCell r){

    }

}
