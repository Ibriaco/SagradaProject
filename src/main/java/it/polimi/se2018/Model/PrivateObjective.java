package it.polimi.se2018.Model;

public class PrivateObjective extends Card {
    private int score;
    private Color color;

    public PrivateObjective(int number, String title, String description, String cardType, int score, Color color) {
        super(number, title, description, cardType);
        this.score = score;
        this.color = color;
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
