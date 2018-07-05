package it.polimi.se2018.controller.effects;

import it.polimi.se2018.controller.ToolCardController;
import it.polimi.se2018.model.*;
import it.polimi.se2018.org.json.simple.parser.ParseException;
import java.io.IOException;

public class SwapDieEffect implements Effect {
    @Override
    public void accept(ToolCardController toolCardController) throws InvalidConnectionException, InvalidViewException, ParseException, IOException, InvalidDieException {
        toolCardController.checkApplyEffect(this);
    }

    public void applyEffect(Die d, Game game, int diePos, int roundPos, int cellPos){
        game.getRolledDice().set(diePos,game.getRoundCells().get(roundPos).getDiceList().get(cellPos));
        game.getRoundCells().get(roundPos).getDiceList().set(cellPos, d);
    }
}
