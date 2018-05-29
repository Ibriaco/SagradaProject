package it.polimi.se2018.Client.View;

import it.polimi.se2018.Server.Model.Game;
import it.polimi.se2018.Server.Model.Player;
import it.polimi.se2018.Server.Controller.ToolCard;

/**
 * Event that lets the player use a Tool Card
 * @author Ibrahim El Shemy
 * @author Marco Gasperini
 */
public class UseToolEvent extends VCEvent {

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
