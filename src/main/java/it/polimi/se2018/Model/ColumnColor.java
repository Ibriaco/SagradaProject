package it.polimi.se2018.Model;

public class ColumnColor extends PublicObjective {
    public ColumnColor(int number, String title, String description, String cardType, int score) {
        super(number, title, description, "PublicObjective", score);
    }

    @Override
    public void calculateBonus(Player p) {

        WindowCard temp = p.getWindowCard();
        int validCols = 0;
        boolean ok;

        for (int i = 0; i < temp.COLS; i++) {

            int[] frequency = {0,0,0,0,0};
            ok = true;

            for (int j = 0; j < temp.ROWS; j++) {
                if (temp.getGridCell(j, i).isPlaced()) {
                    if (temp.getGridCell(j, i).getPlacedDie().getColor() == Color.BLUE)
                        frequency[0]++;
                    if (temp.getGridCell(j, i).getPlacedDie().getColor() == Color.RED)
                        frequency[1]++;
                    if (temp.getGridCell(j, i).getPlacedDie().getColor() == Color.YELLOW)
                        frequency[2]++;
                    if (temp.getGridCell(j, i).getPlacedDie().getColor() == Color.PURPLE)
                        frequency[3]++;
                    if (temp.getGridCell(j, i).getPlacedDie().getColor() == Color.GREEN)
                        frequency[4]++;
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