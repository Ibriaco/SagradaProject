package it.polimi.se2018.Model;

public class Shade extends PublicObjective {
    public Shade(int number, String title, String description, int score) {
        super(number, title, description, score);
    }



    @Override
    public void calculateBonus(Player p) {

        WindowCard temp = p.getWindowCard();
        int oneRec = 0;
        int twoRec = 0;
        int threeRec = 0;
        int fourRec = 0;
        int fiveRec = 0;
        int sixRec = 0;

        for (int i = 0; i < temp.ROWS; i++){
            for (int j = 0; j < temp.COLS; j++){
                if(temp.getGridCell(i,j).isPlaced()) {
                    if (temp.getGridCell(i, j).getPlacedDie().getValue() == 1)
                        oneRec++;
                    if (temp.getGridCell(i, j).getPlacedDie().getValue() == 2)
                        twoRec++;
                    if (temp.getGridCell(i, j).getPlacedDie().getValue() == 3)
                        threeRec++;
                    if (temp.getGridCell(i, j).getPlacedDie().getValue() == 4)
                        fourRec++;
                    if (temp.getGridCell(i, j).getPlacedDie().getValue() == 5)
                        fiveRec++;
                    if (temp.getGridCell(i, j).getPlacedDie().getValue() == 6)
                        sixRec++;
                }
            }
        }

        p.setPlayerScore(p.getPlayerScore() + Math.min(oneRec,Math.min(twoRec,Math.min(threeRec,Math.min(fourRec,Math.min(fiveRec,sixRec)))))*this.getScore());

    }
}
