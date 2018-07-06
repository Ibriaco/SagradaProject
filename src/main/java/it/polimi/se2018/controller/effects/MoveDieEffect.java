package it.polimi.se2018.controller.effects;

import it.polimi.se2018.controller.ToolCardController;
import it.polimi.se2018.model.*;
import it.polimi.se2018.model.event.MoveDieEvent;
import it.polimi.se2018.org.json.simple.parser.ParseException;

import java.io.IOException;

public class MoveDieEffect implements Effect {

    private boolean color;
    private boolean value;
    private boolean around;
    private int amount;

    public MoveDieEffect(boolean color, boolean value, boolean around, int amount){
        this.color = color;
        this.value = value;
        this.amount = amount;
        this.around = around;
    }

    @Override
    public void accept(ToolCardController toolCardController) throws InvalidConnectionException, InvalidViewException, ParseException, IOException {
        toolCardController.checkApplyEffect(this);
    }


    public void applyEffect(WindowCard w, Die die, int newX, int newY){
        w.placeDie(die, newY, newX, color, value, around);
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
