package it.polimi.se2018.Model;

public class Shade extends PublicObjective {
    public Shade(int number, String title, String description, int score) {
        super(number, title, description, score);
    }



    @Override
    public void calculateBonus(Player p) {

        WindowCard temp = p.getWindowCard();
        int oneRec = 0;
        int twoRec = 0;
        int threeRec = 0;
        int fourRec = 0;
        int fiveRec = 0;
        int sixRec = 0;
        int val;

        for (int i = 0; i < temp.ROWS; i++){
            for (int j = 0; j < temp.COLS; j++){
                if(temp.getGridCell(i,j).isPlaced()) {
                    val = temp.getGridCell(i, j).getPlacedDie().getValue();
                    switch (val) {
                        case 1: oneRec++; break;
                        case 2: twoRec++; break;
                        case 3: threeRec++; break;
                        case 4: fourRec++; break;
                        case 5: fiveRec++; break;
                        case 6: sixRec++; break;
                        default:
                    }
                }
            }
        }

        p.setPlayerScore(Math.min(oneRec,Math.min(twoRec,Math.min(threeRec,Math.min(fourRec,Math.min(fiveRec,sixRec))))) * this.getScore());

    }
}
