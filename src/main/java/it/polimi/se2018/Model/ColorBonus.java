package it.polimi.se2018.Model;
/**Class of the Color Variety Public Objective of the game.
 * @author Ibrahim El Shemy
 * @author Marco Gasperini
 */
public class ColorBonus extends PublicObjective {
    public ColorBonus(int number, String title, String description, int score) {
        super(number, title, description, score);
    }

    @Override
    public void calculateBonus(Player p) {
        WindowCard temp = p.getWindowCard();
        int redRec = 0;
        int greenRec = 0;
        int purpleRec = 0;
        int yellowRec = 0;
        int blueRec = 0;
        Color currentC;

        for (int i = 0; i < temp.ROWS; i++){
            for (int j = 0; j< temp.COLS; j++){
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
