package it.polimi.se2018.Model;

public class RowColor extends PublicObjective {
    public RowColor(int number, String title, String description, String cardType, int score) {
        super(number, title, description, "PublicObjective", score);
    }

    @Override
    public void calculateBonus(Player p) {
        int validRows = 0;

        for (int i = 0; i < p.getWindowCard().ROWS; i++){

            int[] frequency={0,0,0,0,0};
            boolean ok = true;

            for (int j=0; j <p.getWindowCard().COLS; j++){
                if(p.getWindowCard().getGridCell(i,j).isPlaced() == true) {
                    if (p.getWindowCard().getGridCell(i, j).getPlacedDie().getColor() == Color.BLUE)
                        frequency[0] = frequency[0] + 1;
                    if (p.getWindowCard().getGridCell(i, j).getPlacedDie().getColor() == Color.RED)
                        frequency[1] = frequency[1] + 1;
                    if (p.getWindowCard().getGridCell(i, j).getPlacedDie().getColor() == Color.YELLOW)
                        frequency[2] = frequency[2] + 1;
                    if (p.getWindowCard().getGridCell(i, j).getPlacedDie().getColor() == Color.PURPLE)
                        frequency[3] = frequency[3] + 1;
                    if (p.getWindowCard().getGridCell(i, j).getPlacedDie().getColor() == Color.GREEN)
                        frequency[4] = frequency[4] + 1;
                 }
                else
                    ok = false;
            }

            for(int index = 0; index<frequency.length; index++){
                if (frequency[index] == 1 && ok){
                    ok = true;
                }
                else
                    ok = false;
            }

           if(ok) validRows++;
        }

        p.setPlayerScore(validRows*this.getScore());
    }



}
