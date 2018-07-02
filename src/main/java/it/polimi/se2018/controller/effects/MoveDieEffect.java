package it.polimi.se2018.controller.effects;

import it.polimi.se2018.controller.ToolCardController;
import it.polimi.se2018.model.Die;
import it.polimi.se2018.model.WindowCard;

public class MoveDieEffect implements Effect {

    private boolean color;
    private boolean value;
    private int amount;

    public MoveDieEffect(boolean color, boolean value, int amount){
        this.color = color;
        this.value = value;
        this.amount = amount;
    }

    @Override
    public void accept(ToolCardController toolCardController) {
        toolCardController.checkApplyEffect(this);
    }

    public void applyEffect(WindowCard w, Die die, int newX, int newY){
        w.placeDie(die, newY, newX, color, value);
    }


    public boolean isColor() {
        return color;
    }

    public boolean isValue() {
        return value;
    }

    public int getAmount() {
        return amount;
    }

}
