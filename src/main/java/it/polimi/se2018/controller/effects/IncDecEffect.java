package it.polimi.se2018.controller.effects;

import it.polimi.se2018.controller.ToolCardController;
import it.polimi.se2018.model.Die;
import it.polimi.se2018.model.InvalidConnectionException;
import it.polimi.se2018.model.InvalidDieException;
import it.polimi.se2018.model.InvalidViewException;
import it.polimi.se2018.view.viewevents.VCEvent;

import java.io.IOException;

public class IncDecEffect implements Effect {

    @Override
    public void accept(ToolCardController toolCardController) throws InvalidConnectionException, InvalidViewException, org.json.simple.parser.ParseException, IOException {
        System.out.println("eseguo accept");
        toolCardController.checkApplyEffect(this);
    }




    public boolean applyEffect(int choose, Die die) throws InvalidDieException {
        if (choose == 1 && (die.getValue() != 6)){
            die.setValue(die.getValue()+1);
            return true;
        }
        else if (choose == 1 && die.getValue() == 6){
            return false;
        }
        else if (choose == 2 && die.getValue() != 1){
            die.setValue(die.getValue()-1);
            return true;
        }
        else if (choose == 2 && die.getValue() == 1){
            return false;
        }
        return false;
    }


}
