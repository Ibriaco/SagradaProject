package it.polimi.se2018.Model;

public abstract class PublicObjective extends Card {
    private int score;

    public PublicObjective(int score) {
        super();
        this.score = score;
    }

    public int getScore() {
        return score;
    }

    public void calculateBonus(Player p){

    }
}
