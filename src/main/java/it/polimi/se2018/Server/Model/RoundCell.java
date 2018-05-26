package it.polimi.se2018.Server.Model;

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
        diceList = new ArrayList<>();
    }

    public int getValue() {

        return value;
    }

    /**
     * Method that adds a die to the round track.
     * @param d Refers to the die to be added.
     */
    public void addDie(Die d){

        diceList.add(d);
    }

    /**
     * Method that swaps a drafted die with a die on the round track.
     * @author Ibrahim El Shemy
     * @param d1 Refers to the die that will replace the die on the round track.
     * @param d2 Refers to the die that will be replaced by d1.
     */
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

        }


    }

    /**
     * @return a List with all dice in the round track.
     */
    public List<Die> getDiceList() {

        return diceList;
    }


}

