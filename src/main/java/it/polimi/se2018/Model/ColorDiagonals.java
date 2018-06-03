package it.polimi.se2018.Model;
/**Class of the Color Diagonals Public Objective of the game.
 * @author Gregorio Galletti
 */
public class ColorDiagonals extends PublicObjective {
    public ColorDiagonals(int number, String title, String description, int score) {
        super(number, title, description, score);
    }

    WindowCard temp;

    /**
     * This method calculates and sets the Color Diagonals Bonus score of a player, analyzing his Window card searching for diagonally adjacent dice of the same color.
     * @param p is the player whose score has to be calculated.
     */
    @Override
    public void calculateBonus(Player p) {

        temp = p.getWindowCard();
        Color currentColor;
        int counter = 1;
        int previousR;
        int previousC;
        int nextR;
        int nextC;
        boolean bonus = false;

        for(int i = 0; i < temp.getRows(); i++){
            for(int j = 1; j < temp.getCols() - 1; j++){
                if(temp.getGridCell(i,j).isPlaced()) {
                    currentColor = temp.getGridCell(i,j).getPlacedDie().getColor();
                    previousR = i - 1;
                    previousC = j - 1;
                    nextR = i + 1;
                    nextC = j + 1;

                    counter += checkNE(currentColor,previousR, nextC) + checkNW(currentColor,previousR,previousC) + checkSE(currentColor,nextR,nextC) + checkSW(currentColor,nextR,previousC);

                    temp.getGridCell(i,j).setVisited(true);
                    bonus = true;
                }
            }
        }

        if(!bonus)
            counter = 0;

        p.setPlayerScore(counter);
    }

    private int checkSE(Color currentColor, int nextR, int nextC) {

        if(nextR >= temp.getRows())
            return 0;

        Cell seCell = temp.getGridCell(nextR,nextC);
        if(!seCell.isPlaced() || seCell.isVisited())
            return 0;

        Color seColor = seCell.getPlacedDie().getColor();
        if(seColor == currentColor) {
            seCell.setVisited(true);
            return 1;
        }
        return 0;
    }

    private int checkSW(Color currentColor, int nextR, int previousC) {

        if(nextR >= temp.getRows())
            return 0;

        Cell swCell = temp.getGridCell(nextR,previousC);
        if(!swCell.isPlaced() || swCell.isVisited())
            return 0;

        Color swColor = swCell.getPlacedDie().getColor();
        if(swColor == currentColor) {
            swCell.setVisited(true);
            return 1;
        }
        return 0;
    }

    private int checkNE(Color currentColor, int previousR, int nextC) {

        if(previousR < 0)
            return 0;

        Cell neCell = temp.getGridCell(previousR,nextC);
        if(!neCell.isPlaced() || neCell.isVisited())
            return 0;

        Color neColor = neCell.getPlacedDie().getColor();
        if(neColor == currentColor) {
            neCell.setVisited(true);
            return 1;
        }
        return 0;
    }

    private int checkNW(Color currentColor, int previousR, int previousC) {

        if(previousR < 0)
            return 0;

        Cell nwCell = temp.getGridCell(previousR,previousC);
        if(!nwCell.isPlaced() || nwCell.isVisited())
            return 0;

        Color nwColor = nwCell.getPlacedDie().getColor();
        if(nwColor == currentColor) {
            nwCell.setVisited(true);
            return 1;
        }
        return 0;
    }


}
