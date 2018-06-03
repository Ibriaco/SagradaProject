package it.polimi.se2018.Model;
/**Class for the Light Shades Public Objective of the game.
 * @author Ibrahim El Shemy
 * @author Marco Gasperini
 */
public class LightShade extends PublicObjective {
    public LightShade(int number, String title, String description, int score) {
        super(number, title, description, score);
    }

    /**
     * This method calculates and sets the Light Shades Bonus score of a player, analyzing his Window card counting the amount of dice with value 1 or 2.
     * @param p is the player whose score has to be calculated.
     */
    @Override
    public void calculateBonus(Player p) {

        WindowCard temp = p.getWindowCard();
        int oneRec = 0;
        int twoRec = 0;
        int val;

        for (int i = 0; i < temp.getRows(); i++){
            for (int j = 0; j < temp.getCols(); j++){
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
