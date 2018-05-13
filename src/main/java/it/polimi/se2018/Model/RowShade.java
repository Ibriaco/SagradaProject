package it.polimi.se2018.Model;

import java.util.ArrayList;

public class RowShade extends PublicObjective {
    public RowShade(int number, String title, String description, String cardType, int score) {
        super(number, title, description, "PublicObjective", score);
    }

    // questo va assolutamente testato
    @Override
    public void calculateBonus(Player p) {

        int validRows = 0;
        boolean ok = true;

        for (int i = 0; i < p.getWindowCard().COLS; i++){

           int[] frequency={0,0,0,0,0,0};

               for (int j=0; j <p.getWindowCard().COLS; j++){
                   if(p.getWindowCard().getGridCell(i,j).isPlaced() == true) {
                       if (p.getWindowCard().getGridCell(i, j).getPlacedDie().getValue() == 1)
                           frequency[0] = frequency[0] + 1;
                       if (p.getWindowCard().getGridCell(i, j).getPlacedDie().getValue() == 2)
                           frequency[1] = frequency[1] + 1;
                       if (p.getWindowCard().getGridCell(i, j).getPlacedDie().getValue() == 3)
                           frequency[2] = frequency[2] + 1;
                       if (p.getWindowCard().getGridCell(i, j).getPlacedDie().getValue() == 4)
                           frequency[3] = frequency[3] + 1;
                       if (p.getWindowCard().getGridCell(i, j).getPlacedDie().getValue() == 5)
                           frequency[4] = frequency[4] + 1;
                       if (p.getWindowCard().getGridCell(i, j).getPlacedDie().getValue() == 6)
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

               validRows++;
        }

            p.setPlayerScore(validRows*5);
        }

}
