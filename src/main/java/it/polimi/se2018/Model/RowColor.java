package it.polimi.se2018.Model;

public class RowColor extends PublicObjective {
    public RowColor(int number, String title, String description, String cardType, int score) {
        super(number, title, description, "PublicObjective", score);
    }

    @Override
    public void calculateBonus(Player p) {

        WindowCard temp = p.getWindowCard();
        int validRows = 0;
        boolean ok;

        for (int i = 0; i < p.getWindowCard().ROWS; i++){

            int[] frequency={0,0,0,0,0};
            ok = true;

            for (int j=0; j <p.getWindowCard().COLS; j++){
                if(temp.getGridCell(i,j).isPlaced()) {
                    if (temp.getGridCell(i, j).getPlacedDie().getColor() == Color.BLUE)
                        frequency[0]++;
                    if (temp.getGridCell(i, j).getPlacedDie().getColor() == Color.RED)
                        frequency[1]++;
                    if (temp.getGridCell(i, j).getPlacedDie().getColor() == Color.YELLOW)
                        frequency[2]++;
                    if (temp.getGridCell(i, j).getPlacedDie().getColor() == Color.PURPLE)
                        frequency[3]++;
                    if (temp.getGridCell(i, j).getPlacedDie().getColor() == Color.GREEN)
                        frequency[4]++;
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

        p.setPlayerScore(p.getPlayerScore() + validRows*this.getScore());
    }



}
