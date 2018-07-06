package it.polimi.se2018.controller.effects;

import it.polimi.se2018.controller.ToolCardController;
import it.polimi.se2018.model.*;
import it.polimi.se2018.org.json.simple.parser.ParseException;

import java.io.IOException;

public class PlaceRestriction implements Effect {
    private boolean color;
    private boolean value;
    private boolean around;

    public PlaceRestriction(boolean color, boolean value, boolean around){
        this.color = color;
        this.value = value;
        this.around = around;
    }
    @Override
    public void accept(ToolCardController toolCardController) throws InvalidConnectionException, InvalidViewException, ParseException, IOException, InvalidDieException {
        toolCardController.checkApplyEffect(this);
    }

}
