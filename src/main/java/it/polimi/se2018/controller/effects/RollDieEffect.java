package it.polimi.se2018.controller.effects;

import it.polimi.se2018.controller.ToolCardController;
import it.polimi.se2018.model.Die;
import it.polimi.se2018.model.InvalidDieException;

import java.util.Random;

public class RollDieEffect implements Effect {

    @Override
    public void accept(ToolCardController toolCardController) {

        toolCardController.checkApplyEffect(this);
    }

    public void applyEffect(Die die) throws InvalidDieException {
        Random random = new Random();
        die.setValue(random.nextInt(6) + 1);
    }
}
