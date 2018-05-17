package it.polimi.se2018.Model;
/**Class of the Column Shade Variety Public Objective of the game.
 * @author Ibrahim El Shemy
 * @author Marco Gasperini
 */
public class ColumnShade extends PublicObjective {
    public ColumnShade(int number, String title, String description, int score) {
        super(number, title, description, score);
    }

    /**
     * This method calculates and sets the Column Shade Bonus score of a player, analyzing his Window card searching for columns with no repeated dice shades.
     * @param p is the player whose score has to be calculated.
     */
    @Override
    public void calculateBonus(Player p) {

        WindowCard temp = p.getWindowCard();
        int validRows = 0;
        int currentV;

        for (int i = 0; i < temp.COLS; i++){

            int[] frequency={0,0,0,0,0,0};
            boolean ok = true;

            for (int j=0; j < temp.ROWS; j++){
                if(temp.getGridCell(j,i).isPlaced()) {
                    currentV = temp.getGridCell(j, i).getPlacedDie().getValue();
                    switch (currentV){
                        case 1: frequency[0]++; break;
                        case 2: frequency[1]++; break;
                        case 3: frequency[2]++; break;
                        case 4: frequency[3]++; break;
                        case 5: frequency[4]++; break;
                        case 6: frequency[5]++; break;
                        default:
                    }
                }
                else
                    ok = false;
            }

            for(int index = 0; index < frequency.length; index++){
                if (frequency[index] < 2 && ok){
                    ok = true;
                }
                else
                    ok = false;
            }

            if (ok) validRows++;
        }

        p.setPlayerScore(validRows * this.getScore());
    }

}