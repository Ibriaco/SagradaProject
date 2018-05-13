package it.polimi.se2018.Model;

import com.sun.scenario.effect.impl.sw.java.JSWBlend_GREENPeer;

public class ColorBonus extends PublicObjective {
    public ColorBonus(int number, String title, String description, String cardType, int score) {
        super(number, title, description, "PublicObjective", score);
    }

    @Override
    public void calculateBonus(Player p) {

        int redRec = 0;
        int greenRec = 0;
        int purpleRec = 0;
        int yellowRec = 0;
        int blueRec = 0;

        for (int i = 0; i < p.getWindowCard().COLS; i++){
            for (int j = 0; j< p.getWindowCard().ROWS; j++){
                if (p.getWindowCard().getGridCell(i,j).getPlacedDie().getColor() == Color.RED)
                    redRec++;
                if (p.getWindowCard().getGridCell(i,j).getPlacedDie().getColor() == Color.GREEN)
                    greenRec++;
                if (p.getWindowCard().getGridCell(i,j).getPlacedDie().getColor() == Color.PURPLE)
                    purpleRec++;
                if (p.getWindowCard().getGridCell(i,j).getPlacedDie().getColor() == Color.YELLOW)
                    yellowRec++;
                if (p.getWindowCard().getGridCell(i,j).getPlacedDie().getColor() == Color.BLUE)
                    blueRec++;
            }
        }

        p.setPlayerScore(Math.min(redRec,Math.min(greenRec,Math.min(purpleRec,Math.min(yellowRec,blueRec))))*4);

    }
}
