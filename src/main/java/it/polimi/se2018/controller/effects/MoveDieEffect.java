package it.polimi.se2018.controller.effects;

import it.polimi.se2018.controller.ToolCardController;

public class MoveDieEffect implements Effect {

    @Override
    public void accept(ToolCardController toolCardController) {
        toolCardController.checkApplyEffect(this);
    }
}
