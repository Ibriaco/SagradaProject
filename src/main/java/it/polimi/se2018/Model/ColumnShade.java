package it.polimi.se2018.Model;
/**Class of the Column Shade Variety Public Objective of the game.
 * @author Ibrahim El Shemy
 * @author Marco Gasperini
 */
public class ColumnShade extends ColumnRowShades {
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
        boolean ok;

        for(int i = 0; i < temp.getCols(); i++){

            int[] frequency={0,0,0,0,0,0};
            ok = true;

            for(int j=0; j < temp.getRows(); j++){
                if(temp.getGridCell(j,i).isPlaced())
                    frequency = super.calculateFrequency(temp, frequency, j, i, true);
                else
                    ok = false;
            }

            for(int index = 0; index < frequency.length; index++){
                ok = frequency[index] < 2 && ok;
            }

            if(ok)
                validRows++;
        }

        p.setPlayerScore(validRows * this.getScore());
    }

}