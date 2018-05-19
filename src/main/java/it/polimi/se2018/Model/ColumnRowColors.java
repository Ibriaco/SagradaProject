package it.polimi.se2018.Model;

/**Class used to reduce computational complexity for Bonus Classes' methods.
 * @author Gregorio Galletti
 */
public class ColumnRowColors extends PublicObjective {
    public ColumnRowColors(int number, String title, String description, int score) {
        super(number, title, description, score);
    }

    /**
     * This method calculates the recurrencies of different colors and puts them into an array.
     * @param w WindowCard
     * @param freq vector in which are saved the recurrencies of some elements.
     * @param j corresponds either the columns of the WindowCard.
     * @param i corresponds either to the rows of the WindowCard.
     * @param type in this case, type = false. Used to manipulate bonus of Color type.
     * @return an array of int, in which are saved the recurrencies of colors found in the WindowCard that may give a bonus to the Player.
     */
    public int[] calculateFrequency(WindowCard w, int[] freq, int j, int i, boolean type){

        Color currentC;
        if(type)
            currentC = w.getGridCell(j, i).getPlacedDie().getColor();
        else
            currentC = w.getGridCell(i, j).getPlacedDie().getColor();

        switch (currentC){
            case BLUE: freq[0]++; break;
            case RED: freq[1]++; break;
            case YELLOW: freq[2]++; break;
            case PURPLE: freq[3]++; break;
            case GREEN: freq[4]++; break;
            default:
        }

        return freq;
    }
}
