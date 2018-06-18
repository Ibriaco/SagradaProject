package it.polimi.se2018.model;
/**Class for the Row Shade Variety Public Objective of the game.
 * @author Ibrahim El Shemy
 * @author Marco Gasperini 
 */
public class RowShade extends ColumnRowAbstract {
    public RowShade(String title, String description, int score) {
        super(title, description, score);
    }

    /**
     * This method calculates and sets the Row Shade Bonus score of a player, analyzing his Window card searching for rows with no repeated dice shades.
     * @param p is the player whose score has to be calculated.
     */
    // questo va assolutamente testato
    @Override
    public void calculateBonus(Player p) {

        WindowCard temp = p.getWindowCard();
        int validRows = 0;
        boolean ok;

        for (int i = 0; i < temp.getRows(); i++){

            int[] frequency={0,0,0,0,0,0};
            ok = true;

               for (int j=0; j < temp.getCols(); j++){
                   if(temp.getGridCell(i,j).isPlaced()) {
                       frequency = super.calculateFrequency(temp, frequency, j, i, true);
                   }
                   else
                       ok = false;
               }

            for (int aFrequency : frequency)
                ok = aFrequency < 2 && ok;


              if (ok) validRows++;
        }

            p.setPlayerScore(validRows * this.getScore());
        }

}
