package it.polimi.se2018.Model;
/**Class for the Row Color Variety Public Objective of the game.
 * @author Ibrahim El Shemy
 * @author Marco Gasperini
 */
public class RowColor extends PublicObjective {
    public RowColor(int number, String title, String description, int score) {
        super(number, title, description, score);
    }

    @Override
    public void calculateBonus(Player p) {

        WindowCard temp = p.getWindowCard();
        int validRows = 0;
        boolean ok;
        Color currentC;

        for (int i = 0; i < temp.ROWS; i++){

            int[] frequency={0,0,0,0,0};
            ok = true;

            for (int j = 0; j < temp.COLS; j++){
                if(temp.getGridCell(i,j).isPlaced()) {
                    currentC = temp.getGridCell(i, j).getPlacedDie().getColor();
                    switch (currentC){
                        case BLUE: frequency[0]++; break;
                        case RED: frequency[1]++; break;
                        case YELLOW: frequency[2]++; break;
                        case PURPLE: frequency[3]++; break;
                        case GREEN: frequency[4]++; break;
                        default:
                    }
                }
                else
                    ok = false;
            }

            for(int index = 0; index < frequency.length; index++){
                if (frequency[index] == 1 && ok)
                    ok = true;
                else
                    ok = false;
            }

           if(ok) validRows++;
        }

        p.setPlayerScore(validRows * this.getScore());
    }



}
