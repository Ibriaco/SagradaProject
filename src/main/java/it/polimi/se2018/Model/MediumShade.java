package it.polimi.se2018.Model;

public class MediumShade extends PublicObjective {
    public MediumShade(int number, String title, String description, String cardType, int score) {
        super(number, title, description, "PublicObjective", score);
    }


    @Override
    public void calculateBonus(Player p) {

        int threeRec = 0;
        int fourRec = 0;


        for (int i = 0; i < p.getWindowCard().ROWS; i++){
            for (int j = 0; j< p.getWindowCard().COLS; j++){
                if (p.getWindowCard().getGridCell(i,j).getPlacedDie().getValue() == 3)
                    threeRec++;
                if (p.getWindowCard().getGridCell(i,j).getPlacedDie().getValue() == 4)
                    fourRec++;
            }
        }

        p.setPlayerScore(Math.min(threeRec,fourRec)*this.getScore());

       // super.calculateBonus(p);
    }
}
