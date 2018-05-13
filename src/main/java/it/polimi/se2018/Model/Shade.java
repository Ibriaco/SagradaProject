package it.polimi.se2018.Model;

public class Shade extends PublicObjective {
    public Shade(int number, String title, String description, String cardType, int score) {
        super(number, title, description, "PublicObjective", score);
    }



    @Override
    public void calculateBonus(Player p) {

        int oneRec = 0;
        int twoRec = 0;
        int threeRec = 0;
        int fourRec = 0;
        int fiveRec = 0;
        int sixRec = 0;

        for (int i = 0; i < p.getWindowCard().ROWS; i++){
            for (int j = 0; j< p.getWindowCard().COLS; j++){
                if (p.getWindowCard().getGridCell(i,j).getPlacedDie().getValue() == 1)
                    oneRec++;
                if (p.getWindowCard().getGridCell(i,j).getPlacedDie().getValue() == 2)
                    twoRec++;
                if (p.getWindowCard().getGridCell(i,j).getPlacedDie().getValue() == 3)
                    threeRec++;
                if (p.getWindowCard().getGridCell(i,j).getPlacedDie().getValue() == 4)
                    fourRec++;
                if (p.getWindowCard().getGridCell(i,j).getPlacedDie().getValue() == 5)
                    fiveRec++;
                if (p.getWindowCard().getGridCell(i,j).getPlacedDie().getValue() == 6)
                    sixRec++;
            }
        }

        p.setPlayerScore(Math.min(oneRec,Math.min(twoRec,Math.min(threeRec,Math.min(fourRec,Math.min(fiveRec,sixRec)))))*5);

    }
}
