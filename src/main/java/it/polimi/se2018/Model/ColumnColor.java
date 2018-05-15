package it.polimi.se2018.Model;

public class ColumnColor extends PublicObjective {
    public ColumnColor(int number, String title, String description, int score) {
        super(number, title, description, score);
    }

    @Override
    public void calculateBonus(Player p) {

        WindowCard temp = p.getWindowCard();
        int validCols = 0;
        boolean ok;
        Color currentC;

        for (int i = 0; i < temp.COLS; i++) {

            int[] frequency = {0,0,0,0,0};
            ok = true;

            for (int j = 0; j < temp.ROWS; j++) {
                if (temp.getGridCell(j, i).isPlaced()) {
                    currentC = temp.getGridCell(j, i).getPlacedDie().getColor();
                    switch (currentC){
                        case BLUE: frequency[0]++; break;
                        case RED: frequency[1]++; break;
                        case YELLOW: frequency[2]++; break;
                        case PURPLE: frequency[3]++; break;
                        case GREEN: frequency[4]++; break;
                        default:
                    }
                } else
                    ok = false;
            }

            for (int index = 0; index < frequency.length; index++) {
                if (frequency[index] < 2 && ok) {
                    ok = true;
                } else
                    ok = false;
            }

            if (ok) validCols++;
        }

        p.setPlayerScore(p.getPlayerScore() + validCols * this.getScore());
    }
}