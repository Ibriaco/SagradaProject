package it.polimi.se2018.Model;

public class LightShade extends PublicObjective {
    public LightShade(int number, String title, String description, String cardType, int score) {
        super(number, title, description, "PublicObjective", score);
    }



    @Override
    public void calculateBonus(Player p) {

        int oneRec = 0;
        int twoRec = 0;


        for (int i = 0; i < p.getWindowCard().ROWS; i++){
            for (int j = 0; j< p.getWindowCard().COLS; j++){
                if (p.getWindowCard().getGridCell(i,j).getPlacedDie().getValue() == 1)
                    oneRec++;
                if (p.getWindowCard().getGridCell(i,j).getPlacedDie().getValue() == 2)
                    twoRec++;
            }
        }

        p.setPlayerScore(Math.min(oneRec,twoRec)*this.getScore());

       // super.calculateBonus(p);
    }
}
