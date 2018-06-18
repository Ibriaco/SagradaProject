package it.polimi.se2018.model;

/**
 *PrivateObjective Class of the Game. Contains a constructor and getter methods.
 */
public class PrivateObjective extends Card {
    private int score;
    private Color color;

    public PrivateObjective(String title, String description, int score, Color color) {
        super(title, description);
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

        return "PRIVATE OBJECTIVE: " + "[Shades of " + color + "S]";
    }
}