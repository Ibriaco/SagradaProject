package it.polimi.se2018.Model;

public class LightShade extends PublicObjective {
    public LightShade(int number, String title, String description, String cardType, int score) {
        super(number, title, description, "PublicObjective", score);
    }



    @Override
    public void calculateBonus(Player p) {

        WindowCard temp = p.getWindowCard();
        int oneRec = 0;
        int twoRec = 0;
        int val = 0;

        for (int i = 0; i < temp.ROWS; i++){
            for (int j = 0; j < temp.COLS; j++){
                if(temp.getGridCell(i,j).isPlaced()) {
                    val = temp.getGridCell(i, j).getPlacedDie().getValue();
                    if (val == 1)
                        oneRec++;
                    if (val == 2)
                        twoRec++;
                }
            }
        }

        p.setPlayerScore(p.getPlayerScore() + Math.min(oneRec,twoRec)*this.getScore());

    }
}
