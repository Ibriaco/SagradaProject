package it.polimi.se2018.Model;

import java.util.ArrayList;
import java.util.List;
/**Class for the Round Cell of the game.
 * @author Ibrahim El Shemy
 */
public class RoundCell {
    private int value;
    private List<Die> diceList;

    public RoundCell(int value) {
        this.value = value;
        diceList = new ArrayList<Die>();
    }

    public int getValue() {

        return value;
    }

    // da testare
    public void addDie(Die d){

        diceList.add(d);
    }

    // da testare
    public void replaceDie(Die d1, Die d2){
        int val;
        Color col;

        val = d2.getValue();
        col = d2.getColor();

        diceList.set(diceList.indexOf(d2), d1);

        try {
            d1.setValue(val);
            d1.setColor(col);
        } catch (InvalidDieException e) {
            e.printStackTrace();
        }


    }
}

