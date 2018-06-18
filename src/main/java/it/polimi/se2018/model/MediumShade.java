package it.polimi.se2018.model;
/**Class for the Medium Shades Public Objective of the game.
 * @author Ibrahim El Shemy
 * @author Marco Gasperini
 */
public class MediumShade extends PublicObjective {
    public MediumShade(String title, String description, int score) {
        super(title, description, score);
    }

    /**
     * This method calculates and sets the Medium Shades Bonus score of a player, analyzing his Window card counting the amount of dice with value 3 or 4.
     * @param p is the player whose score has to be calculated.
     */
    @Override
    public void calculateBonus(Player p) {

        WindowCard temp = p.getWindowCard();
        int threeRec = 0;
        int fourRec = 0;
        int val;

        for (int i = 0; i < temp.getRows(); i++){
            for (int j = 0; j < temp.getCols(); j++){
                if(temp.getGridCell(i,j).isPlaced()) {
                    val = temp.getGridCell(i, j).getPlacedDie().getValue();
                    switch (val) {
                        case 3: threeRec++; break;
                        case 4: fourRec++; break;
                        default:
                    }
                }
            }
        }

        p.setPlayerScore(Math.min(threeRec,fourRec) * this.getScore());

    }
}
