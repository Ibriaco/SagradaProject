package it.polimi.se2018.view.viewevents;

import it.polimi.se2018.controller.ControllerInterface;
import it.polimi.se2018.model.InvalidConnectionException;
import it.polimi.se2018.model.InvalidViewException;
import it.polimi.se2018.org.json.simple.parser.ParseException;

import java.io.IOException;

/**
 * event that lets the player use a Tool Card
 * @author Ibrahim El Shemy
 * @author Marco Gasperini
 */
public class UseToolEvent implements VCEvent{

    private int toolCardNumber;
    private String username;

    /**
     *
     * @param username username of the current player
     * @param pos position where the Tool Card is located
     */
    public UseToolEvent(String username, int pos) {
        this.username = username;
        this.toolCardNumber = pos;
    }

    public int getToolCardNumber(){

        return toolCardNumber;
    }

    public String getUsername(){
        return username;
    }

    @Override
    public void accept(ControllerInterface controller) throws InvalidConnectionException, ParseException, InvalidViewException, IOException {
        controller.handleVCEvent(this);
    }
}
