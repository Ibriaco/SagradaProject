package it.polimi.se2018.controller.effects;

import it.polimi.se2018.controller.ToolCardController;
import it.polimi.se2018.model.Die;
import it.polimi.se2018.model.InvalidConnectionException;
import it.polimi.se2018.model.InvalidDieException;
import it.polimi.se2018.model.InvalidViewException;
import org.json.simple.parser.ParseException;

import java.io.IOException;

public class ReverseDieEffect implements Effect {


    @Override
    public void accept(ToolCardController toolCardController) throws InvalidConnectionException, InvalidViewException, ParseException, IOException, InvalidDieException {
        toolCardController.checkApplyEffect(this);

    }

    public void applyEffect(Die die) throws InvalidDieException {
        System.out.println("Valore dado: " + die.getValue());
        die.reverse(die);
    }
}
