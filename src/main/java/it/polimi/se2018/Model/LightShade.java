package it.polimi.se2018.Model;
/**Class for the Light Shades Public Objective of the game.
 * @author Ibrahim El Shemy
 * @author Marco Gasperini
 */
public class LightShade extends PublicObjective {
    public LightShade(int number, String title, String description, int score) {
        super(number, title, description, score);
    }


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
