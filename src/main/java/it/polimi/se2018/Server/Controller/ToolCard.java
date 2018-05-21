package it.polimi.se2018.Server.Controller;


import it.polimi.se2018.Server.Model.*;

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

    protected boolean isUsed() {

        return used;
    }

    protected int getCost() {

        return cost;
    }

    protected Color getColor() {

        return color;
    }

    protected int getShade() {

        return shade;
    }

    protected void applyEffect(Player p, Die d, RoundCell r){

    }

}
