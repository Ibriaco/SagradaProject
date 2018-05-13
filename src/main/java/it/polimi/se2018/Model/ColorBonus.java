package it.polimi.se2018.Model;


public class ColorBonus extends PublicObjective {
    public ColorBonus(int number, String title, String description, String cardType, int score) {
        super(number, title, description, "PublicObjective", score);
    }

    @Override
    public void calculateBonus(Player p) {

        WindowCard temp = p.getWindowCard();
        int redRec = 0;
        int greenRec = 0;
        int purpleRec = 0;
        int yellowRec = 0;
        int blueRec = 0;

        for (int i = 0; i < temp.COLS; i++){
            for (int j = 0; j< temp.ROWS; j++){
                if(temp.getGridCell(i,j).isPlaced()) {
                    if (temp.getGridCell(i, j).getPlacedDie().getColor() == Color.RED)
                        redRec++;
                    if (temp.getGridCell(i, j).getPlacedDie().getColor() == Color.GREEN)
                        greenRec++;
                    if (temp.getGridCell(i, j).getPlacedDie().getColor() == Color.PURPLE)
                        purpleRec++;
                    if (temp.getGridCell(i, j).getPlacedDie().getColor() == Color.YELLOW)
                        yellowRec++;
                    if (temp.getGridCell(i, j).getPlacedDie().getColor() == Color.BLUE)
                        blueRec++;
                }
            }
        }

        p.setPlayerScore(p.getPlayerScore() + Math.min(redRec,Math.min(greenRec,Math.min(purpleRec,Math.min(yellowRec,blueRec))))*this.getScore());

    }
}
