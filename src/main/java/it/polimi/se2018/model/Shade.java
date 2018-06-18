package it.polimi.se2018.model;
/**Class for the Shade Variety Public Objective of the game.
 * @author Ibrahim El Shemy
 * @author Marco Gasperini
 */
public class Shade extends PublicObjective {
    public Shade(String title, String description, int score) {
        super(title, description, score);
    }

    /**
     * This method calculates and sets the Shade Variety Bonus score of a player, analyzing his Window card counting the amount of dice of each shade.
     * @param p is the player whose score has to be calculated.
     */
    @Override
    public void calculateBonus(Player p) {

        WindowCard temp = p.getWindowCard();
        int oneRec = 0;
        int twoRec = 0;
        int threeRec = 0;
        int fourRec = 0;
        int fiveRec = 0;
        int sixRec = 0;
        int val;

        for (int i = 0; i < temp.getRows(); i++){
            for (int j = 0; j < temp.getCols(); j++){
                if(temp.getGridCell(i,j).isPlaced()) {
                    val = temp.getGridCell(i, j).getPlacedDie().getValue();
                    switch (val) {
                        case 1: oneRec++; break;
                        case 2: twoRec++; break;
                        case 3: threeRec++; break;
                        case 4: fourRec++; break;
                        case 5: fiveRec++; break;
                        case 6: sixRec++; break;
                        default:
                    }
                }
            }
        }

        p.setPlayerScore(Math.min(oneRec,Math.min(twoRec,Math.min(threeRec,Math.min(fourRec,Math.min(fiveRec,sixRec))))) * this.getScore());

    }
}
