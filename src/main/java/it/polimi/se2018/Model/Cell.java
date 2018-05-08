package it.polimi.se2018.Model;

public class Cell {
    private Color color;
    private int shade;
    private boolean placed;
    private Die placedDie;


    public Cell() {
    }

    public Color getColor() {
        return color;
    }

    public int getShade() {
        return shade;
    }

    public boolean isPlaced() {
        return placed;
    }

    public Die getPlacedDie() {
        return placedDie;
    }
}
