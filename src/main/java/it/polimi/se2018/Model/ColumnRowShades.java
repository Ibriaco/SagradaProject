package it.polimi.se2018.Model;

/**Class used to reduce computational complexity for Bonus Classes' methods.
 * @author Gregorio Galletti
 */
public class ColumnRowShades extends PublicObjective{
    public ColumnRowShades(String title, String description, int score) {
        super(title, description, score);
    }

    /**
     *This method calculates the recurrencies of different shades and puts them into an array.
     * @param w WindowCard
     * @param freq vector in which are saved the recurrencies of some elements.
     * @param j corresponds either the rows of the WindowCard.
     * @param i corresponds either to the columns of the WindowCard.
     * @param type in this case, type = true. Used to manipulate bonus of Shade type.
     * @return an array of int, in which are saved the recurrencies of shades found in the WindowCard that may give a bonus to the Player.
     */
    public int[] calculateFrequency(WindowCard w, int[] freq, int j, int i, boolean type){

        int currentV;
        if(type)
            currentV = w.getGridCell(j, i).getPlacedDie().getValue();
        else
            currentV = w.getGridCell(i, j).getPlacedDie().getValue();

        switch (currentV){
            case 1: freq[0]++; break;
            case 2: freq[1]++; break;
            case 3: freq[2]++; break;
            case 4: freq[3]++; break;
            case 5: freq[4]++; break;
            case 6: freq[5]++; break;
            default:
        }

        return freq;
    }
}

