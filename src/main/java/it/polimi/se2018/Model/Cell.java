package it.polimi.se2018.Model;

public class Cell {
    private Color color;
    private int shade;
    private boolean placed;
    private Die placedDie;
    private boolean visited;


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

    public void setColor(Color color) {

        this.color = color;
    }

    public void setShade(int shade) {

        this.shade = shade;
    }

    // da testare
    public boolean isPlaced() {

        if(placedDie != null)
            placed = true;
        else
            placed = false;
        return placed;
    }

    // da testare
    public Die getPlacedDie() {

        return placedDie;
    }

    public boolean isVisited() {

        return visited;
    }

    public void setVisited(boolean visited) {

        this.visited = visited;
    }
}
