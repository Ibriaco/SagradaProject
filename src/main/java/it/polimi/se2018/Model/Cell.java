package it.polimi.se2018.Model;

public class Cell {
    private Color color;
    private int shade;
    private boolean placed;
    private Die placedDie;


    public Cell(Color color, int shade) {
        this.color = color;
        this.shade = shade;
    }

    public Color getColor() {
        return color;
    }

    public int getShade() {
        return shade;
    }

    // da testare
    public boolean isPlaced() {
        return placed;
    }

    // da testare
    public Die getPlacedDie() {
        return placedDie;
    }
}
