package it.polimi.se2018.Model;

public class MediumShade extends PublicObjective {
    public MediumShade(int number, String title, String description, int score) {
        super(number, title, description, score);
    }


    @Override
    public void calculateBonus(Player p) {

        WindowCard temp = p.getWindowCard();
        int threeRec = 0;
        int fourRec = 0;

        for (int i = 0; i < temp.ROWS; i++){
            for (int j = 0; j < temp.COLS; j++){
                if(temp.getGridCell(i,j).isPlaced()) {
                    if (temp.getGridCell(i, j).getPlacedDie().getValue() == 3)
                        threeRec++;
                    if (temp.getGridCell(i, j).getPlacedDie().getValue() == 4)
                        fourRec++;
                }
            }
        }

        p.setPlayerScore(p.getPlayerScore() + Math.min(threeRec,fourRec)*this.getScore());

    }
}
