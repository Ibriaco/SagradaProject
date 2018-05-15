package it.polimi.se2018.Model;

public class DeepShade extends PublicObjective {
    public DeepShade(int number, String title, String description, int score) {
        super(number, title, description, score);
    }



    @Override
    public void calculateBonus(Player p) {

        WindowCard temp = p.getWindowCard();
        int fiveRec = 0;
        int sixRec = 0;

        for (int i = 0; i < temp.ROWS; i++){
            for (int j = 0; j < temp.COLS; j++){
                if(temp.getGridCell(i,j).isPlaced()) {
                    if (temp.getGridCell(i, j).getPlacedDie().getValue() == 5)
                        fiveRec++;
                    if (temp.getGridCell(i, j).getPlacedDie().getValue() == 6)
                        sixRec++;
                }
            }
        }

        p.setPlayerScore(p.getPlayerScore() + Math.min(fiveRec,sixRec)*this.getScore());

    }
}
