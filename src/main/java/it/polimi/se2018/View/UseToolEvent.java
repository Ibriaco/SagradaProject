package it.polimi.se2018.View;

import java.util.Observable;

/**
 * Event that lets the player use a Tool Card
 * @author Ibrahim El Shemy
 * @author Marco Gasperini
 */
public class UseToolEvent extends VCEvent{

    private int toolCardNumber;

    /**
     *
     * @param username username of the current player
     * @param pos position where the Tool Card is located
     */
    public UseToolEvent(String username, int pos) {
        super(username);
        this.toolCardNumber = pos;
    }

    public int getToolCardNumber(){

        return toolCardNumber;
    }
}
