package it.polimi.se2018.Model;
/**Class for the Deep Shades Public Objective of the game.
 * @author Ibrahim El Shemy
 * @author Marco Gasperini
 */
public class DeepShade extends PublicObjective {
    public DeepShade(int number, String title, String description, int score) {
        super(number, title, description, score);
    }

    /**
     * This method calculates and sets the Deep Shades Bonus score of a player, analyzing his Window card counting the amount of dice with value 5 or 6.
     * @param p is the player whose score has to be calculated.
     */
    @Override
    public void calculateBonus(Player p) {

        WindowCard temp = p.getWindowCard();
        int fiveRec = 0;
        int sixRec = 0;
        int currentV;
        for (int i = 0; i < temp.ROWS; i++){
            for (int j = 0; j < temp.COLS; j++){
                if(temp.getGridCell(i,j).isPlaced()) {
                    currentV = temp.getGridCell(i, j).getPlacedDie().getValue();
                    switch (currentV) {
                        case 5: fiveRec++; break;
                        case 6: sixRec++; break;
                        default:
                    }
                }
            }
        }

        p.setPlayerScore(Math.min(fiveRec,sixRec) * this.getScore());

    }
}
