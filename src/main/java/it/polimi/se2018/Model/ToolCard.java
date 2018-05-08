package it.polimi.se2018.Model;

public abstract class ToolCard extends Card {
    private boolean used;
    private int cost;
    private Color color;
    private int shade;

    public ToolCard() {
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
