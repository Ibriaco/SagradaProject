package it.polimi.se2018.Model;

/**
 * @author Griggo
 * Class of the Public Cards Light Shade,..
 */
public class LightShade extends PublicObjective {
    public LightShade(int number, String title, String description, int score) {
        super(number, title, description, score);
    }


    /**
     * @author griggo
     * @param p is the player whose bonus is calculated
     */
    @Override
    public void calculateBonus(Player p) {

        WindowCard temp = p.getWindowCard();
        int oneRec = 0;
        int twoRec = 0;
        int val;

        for (int i = 0; i < temp.ROWS; i++){
            for (int j = 0; j < temp.COLS; j++){
                if(temp.getGridCell(i,j).isPlaced()) {
                    val = temp.getGridCell(i, j).getPlacedDie().getValue();
                    switch (val) {
                        case 1: oneRec++; break;
                        case 2: twoRec++; break;
                        default:
                    }
                }
            }
        }

        p.setPlayerScore(Math.min(oneRec,twoRec) * this.getScore());

    }
}
