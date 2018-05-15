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
        int currentV;

        for (int i = 0; i < temp.ROWS; i++){

           int[] frequency={0,0,0,0,0,0};
            ok = true;

               for (int j=0; j < temp.COLS; j++){
                   if(temp.getGridCell(i,j).isPlaced()) {
                       currentV = temp.getGridCell(i, j).getPlacedDie().getValue();
                       switch (currentV){
                           case 1: frequency[0]++; break;
                           case 2: frequency[1]++; break;
                           case 3: frequency[2]++; break;
                           case 4: frequency[3]++; break;
                           case 5: frequency[4]++; break;
                           case 6: frequency[5]++; break;
                           default:
                       }
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
