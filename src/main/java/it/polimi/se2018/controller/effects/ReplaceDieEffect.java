package it.polimi.se2018.controller.effects;

import it.polimi.se2018.controller.ToolCardController;
import it.polimi.se2018.model.*;
import it.polimi.se2018.org.json.simple.parser.ParseException;

import java.io.IOException;

public class ReplaceDieEffect implements Effect {

    @Override
    public void accept(ToolCardController toolCardController) throws InvalidConnectionException, InvalidViewException, ParseException, IOException {
        toolCardController.checkApplyEffect(this);
    }

    public void applyEffect(Die d, Game game, ToolCardController controller, int position) {
        if (d.getColor() == Color.RED){
            game.setRedAmount(game.getRedAmount() + 1);
            controller.setD(new Die(game.getColorList()));
            game.reduceAmount(d.getColor());
            game.getRolledDice().set(position,controller.getD());
        }
        if (d.getColor() == Color.YELLOW){
            game.setYellowAmount(game.getYellowAmount() + 1);
            controller.setD(new Die(game.getColorList()));
            game.reduceAmount(d.getColor());
            game.getRolledDice().set(position,controller.getD());
        }
        if (d.getColor() == Color.GREEN){
            game.setGreenAmount(game.getGreenAmount() + 1);
            controller.setD(new Die(game.getColorList()));
            game.reduceAmount(d.getColor());
            game.getRolledDice().set(position,controller.getD());
        }
        if (d.getColor() == Color.BLUE){
            game.setBlueAmount(game.getBlueAmount() + 1);
            controller.setD(new Die(game.getColorList()));
            game.reduceAmount(d.getColor());
            game.getRolledDice().set(position,controller.getD());
        }
        if (d.getColor() == Color.PURPLE){
            game.setPurpleAmount(game.getPurpleAmount() + 1);
            controller.setD(new Die(game.getColorList()));
            game.reduceAmount(d.getColor());
            game.getRolledDice().set(position,controller.getD());
        }
    }
}
