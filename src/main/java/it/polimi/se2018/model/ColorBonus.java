package it.polimi.se2018.model;
/**Class of the Color Variety Public Objective of the game.
 * @author Ibrahim El Shemy
 * @author Marco Gasperini
 */
public class ColorBonus extends PublicObjective {
    public ColorBonus(String title, String description, int score) {
        super(title, description, score);
    }

    /**
     * This method calculates and sets the Color Variety Bonus score of a player, analyzing his Window card counting the amount of dice of each color.
     * @param p is the player whose score has to be calculated.
     */
    @Override
    public void calculateBonus(Player p) {
        WindowCard temp = p.getWindowCard();
        int redRec = 0;
        int greenRec = 0;
        int purpleRec = 0;
        int yellowRec = 0;
        int blueRec = 0;
        Color currentC;

        for (int i = 0; i < temp.getRows(); i++){
            for (int j = 0; j< temp.getCols(); j++){
                if(temp.getGridCell(i,j).isPlaced()) {
                    currentC = temp.getGridCell(i, j).getPlacedDie().getColor();
                    switch (currentC){
                        case RED: redRec++; break;
                        case GREEN: greenRec++; break;
                        case PURPLE: purpleRec++; break;
                        case YELLOW: yellowRec++; break;
                        case BLUE: blueRec++; break;
                        default:
                    }
                }
            }
        }

        p.setPlayerScore(Math.min(redRec,Math.min(greenRec,Math.min(purpleRec,Math.min(yellowRec,blueRec)))) * this.getScore());

    }
}
