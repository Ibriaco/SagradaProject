package it.polimi.se2018.model;
/**Class of the Column Color Variety Public Objective of the game.
 * @author Ibrahim El Shemy
 * @author Marco Gasperini
 */
public class ColumnColor extends ColumnRowAbstract {
    public ColumnColor(String title, String description, int score) {
        super(title, description, score);
    }

    /**
     * This method calculates and sets the Column Color Bonus score of a player, analyzing his Window card searching for columns with no repeated dice colors.
     * @param p is the player whose score has to be calculated.
     */
    @Override
    public void calculateBonus(Player p) {

        WindowCard temp = p.getWindowCard();
        int validCols = 0;
        boolean ok;

        for (int i = 0; i < temp.getCols(); i++) {

            int[] frequency = {0,0,0,0,0};
            ok = true;

            for(int j = 0; j < temp.getRows(); j++) {
                if (temp.getGridCell(j, i).isPlaced())
                        frequency = super.calculateFrequency(temp, frequency, i, j, false);
                else
                    ok = false;
            }

            for (int aFrequency : frequency)
                ok = aFrequency < 2 && ok;

            if(ok)
                validCols++;
        }

        p.setPlayerScore(validCols * this.getScore());
    }
}