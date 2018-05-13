package it.polimi.se2018.Model;

public class DeepShade extends PublicObjective {
    public DeepShade(int number, String title, String description, String cardType, int score) {
        super(number, title, description, "PublicObjective", score);
    }



    @Override
    public void calculateBonus(Player p) {

        int fiveRec = 0;
        int sixRec = 0;

        for (int i = 0; i < p.getWindowCard().ROWS; i++){
            for (int j = 0; j< p.getWindowCard().COLS; j++){
                if (p.getWindowCard().getGridCell(i,j).getPlacedDie().getValue() == 1)
                    fiveRec++;
                if (p.getWindowCard().getGridCell(i,j).getPlacedDie().getValue() == 2)
                    sixRec++;
            }
        }

        p.setPlayerScore(Math.min(fiveRec,sixRec));

    }
}
