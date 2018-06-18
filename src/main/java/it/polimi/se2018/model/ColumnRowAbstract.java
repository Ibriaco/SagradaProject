package it.polimi.se2018.model;

/**Class used to reduce computational complexity for Bonus Classes' methods.
 * @author Gregorio Galletti
 */
public class ColumnRowAbstract extends PublicObjective{
    public ColumnRowAbstract(String title, String description, int score) {
        super(title, description, score);
    }

    /**
     *This method calculates the recurrences of different shades and puts them into an array.
     * @param w WindowCard
     * @param freq vector in which are saved the recurrences of some elements.
     * @param j corresponds either the rows of the WindowCard.
     * @param i corresponds either to the columns of the WindowCard.
     * @param type in this case, type = true. Used to manipulate bonus of Shade type.
     * @return an array of int, in which are saved the recurrences of shades found in the WindowCard that may give a bonus to the Player.
     */
    public int[] calculateFrequency(WindowCard w, int[] freq, int j, int i, boolean type){

        int currentV;
        Color currentC;

        if(type) {
            currentV = w.getGridCell(j, i).getPlacedDie().getValue();
            switch (currentV) {
                case 1:
                    freq[0]++;
                    break;
                case 2:
                    freq[1]++;
                    break;
                case 3:
                    freq[2]++;
                    break;
                case 4:
                    freq[3]++;
                    break;
                case 5:
                    freq[4]++;
                    break;
                case 6:
                    freq[5]++;
                    break;
                default:
            }
        }
        else{
            currentC = w.getGridCell(i, j).getPlacedDie().getColor();
            switch (currentC) {
                case BLUE:
                    freq[0]++;
                    break;
                case RED:
                    freq[1]++;
                    break;
                case YELLOW:
                    freq[2]++;
                    break;
                case PURPLE:
                    freq[3]++;
                    break;
                case GREEN:
                    freq[4]++;
                    break;
                case WHITE:
                    break;
                default:
            }
        }

        return freq;
    }
}

