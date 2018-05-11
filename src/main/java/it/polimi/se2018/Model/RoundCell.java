package it.polimi.se2018.Model;

import java.util.ArrayList;

public class RoundCell {
    private int value;
    private ArrayList<Die> diceList;

    public RoundCell(int value, ArrayList<Die> diceList) {
        this.value = value;
        this.diceList = diceList;
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

        d1.setValue(val);
        d1.setColor(col);

    }
}

