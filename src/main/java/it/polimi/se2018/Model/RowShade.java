package it.polimi.se2018.Model;

import java.util.ArrayList;

public class RowShade extends PublicObjective {
    public RowShade(int number, String title, String description, int score) {
        super(number, title, description, score);
    }

    // questo va assolutamente testato
    @Override
    public void calculateBonus(Player p) {

        WindowCard temp = p.getWindowCard();
        int validRows = 0;
        boolean ok;
        for (int i = 0; i < temp.ROWS; i++){

           int[] frequency={0,0,0,0,0,0};
            ok = true;

               for (int j=0; j < temp.COLS; j++){
                   if(temp.getGridCell(i,j).isPlaced()) {
                       if (temp.getGridCell(i, j).getPlacedDie().getValue() == 1)
                           frequency[0]++;
                       if (temp.getGridCell(i, j).getPlacedDie().getValue() == 2)
                           frequency[1]++;
                       if (temp.getGridCell(i, j).getPlacedDie().getValue() == 3)
                           frequency[2]++;
                       if (temp.getGridCell(i, j).getPlacedDie().getValue() == 4)
                           frequency[3]++;
                       if (temp.getGridCell(i, j).getPlacedDie().getValue() == 5)
                           frequency[4]++;
                       if (temp.getGridCell(i, j).getPlacedDie().getValue() == 6)
                           frequency[5]++;
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

            p.setPlayerScore(p.getPlayerScore() + validRows*this.getScore());
        }

}
