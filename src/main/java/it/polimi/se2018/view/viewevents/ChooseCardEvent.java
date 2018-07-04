package it.polimi.se2018.view.viewevents;

import it.polimi.se2018.controller.ControllerInterface;
import it.polimi.se2018.model.InvalidConnectionException;
import it.polimi.se2018.model.InvalidViewException;
import it.polimi.se2018.model.WindowCard;
import it.polimi.se2018.org.json.simple.parser.ParseException;

import java.io.IOException;

/**
 * event that returns the number and the side of a chosen Card by the player
 * @author Ibrahim El Shemy
 * @author Marco Gasperini
 */
public class ChooseCardEvent implements VCEvent {
    private WindowCard windowCard;
    private String username;

    /**
     *
     * @param username username of the current player.
     * @param windowCard refers to the name of the Card
     */
    public ChooseCardEvent(String username,WindowCard windowCard) {

        this.username = username;
        this.windowCard = windowCard;
    }

    public WindowCard getWindowCard() {
        return windowCard;
    }

    @Override
    public void accept(ControllerInterface controller) throws IOException, InvalidConnectionException, InvalidViewException, ParseException {
        controller.handleVCEvent(this);
    }

    public String getUsername(){
        return username;
    }
}
