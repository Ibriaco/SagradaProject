package it.polimi.se2018.Model;

public class PrivateObjective extends Card {
    private int score;
    private Color color;

    public PrivateObjective() {
    }

    public int getScore() {
        return score;
    }

    public Color getColor() {
        return color;
    }

    @Override
    public String toString() {
        return "PrivateObjective{" +
                "score=" + score +
                ", color=" + color +
                '}';
    }
}
