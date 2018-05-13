package it.polimi.se2018.Model;

public class ColumnShade extends PublicObjective {
    public ColumnShade(int number, String title, String description, String cardType, int score) {
        super(number, title, description, "PublicObjective", score);
    }

    @Override
    public void calculateBonus(Player p) {
        int validRows = 0;


        for (int i = 0; i < p.getWindowCard().COLS; i++){

            int[] frequency={0,0,0,0,0,0};
            boolean ok = true;

            for (int j=0; j <p.getWindowCard().ROWS; j++){
                if(p.getWindowCard().getGridCell(j,i).isPlaced() == true) {
                    if (p.getWindowCard().getGridCell(j, i).getPlacedDie().getValue() == 1)
                        frequency[0] = frequency[0] + 1;
                    if (p.getWindowCard().getGridCell(j, i).getPlacedDie().getValue() == 2)
                        frequency[1] = frequency[1] + 1;
                    if (p.getWindowCard().getGridCell(j, i).getPlacedDie().getValue() == 3)
                        frequency[2] = frequency[2] + 1;
                    if (p.getWindowCard().getGridCell(j, i).getPlacedDie().getValue() == 4)
                        frequency[3] = frequency[3] + 1;
                    if (p.getWindowCard().getGridCell(j, i).getPlacedDie().getValue() == 5)
                        frequency[4] = frequency[4] + 1;
                    if (p.getWindowCard().getGridCell(j, i).getPlacedDie().getValue() == 6)
                        frequency[5] = frequency[5] + 1;
                }
                else
                    ok = false;
            }

            for(int index = 0; index<frequency.length; index++){
                if (frequency[index] < 2 && ok){
                    ok = true;
                }
                else
                    ok = false;
            }

            if (ok) validRows++;
        }

        p.setPlayerScore(validRows*this.getScore());
    }

}