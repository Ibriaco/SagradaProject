package it.polimi.se2018.controller.effects;

import it.polimi.se2018.controller.ToolCardController;
import it.polimi.se2018.model.Die;
import it.polimi.se2018.model.InvalidConnectionException;
import it.polimi.se2018.model.InvalidDieException;
import it.polimi.se2018.model.InvalidViewException;
import it.polimi.se2018.org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.List;
import java.util.Random;

public class RollDiceEffect implements Effect {

    @Override
    public void accept(ToolCardController toolCardController) throws InvalidConnectionException, InvalidViewException, ParseException, IOException, InvalidDieException {
        toolCardController.checkApplyEffect(this);
    }

    public void applyEffect(List<Die> diceList) throws InvalidDieException {
        for (Die die: diceList) {
            Random random = new Random();
            die.setValue(random.nextInt(6) + 1);
        }
    }
}
