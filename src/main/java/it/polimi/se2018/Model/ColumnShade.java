package it.polimi.se2018.Model;

public class ColumnShade extends PublicObjective {
    public ColumnShade(int number, String title, String description, String cardType, int score) {
        super(number, title, description, "PublicObjective", score);
    }

    @Override
    public void calculateBonus(Player p) {
        super.calculateBonus(p);
    }
}
