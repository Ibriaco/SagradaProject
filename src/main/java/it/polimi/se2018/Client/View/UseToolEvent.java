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
     * @param player refers to the current player
     * @param game refers to the actual game
     * @param pos refers to the position where the Tool Card is located
     */
    public UseToolEvent(Player player, Game game, int pos) {
        super(player);
        toolCardNumber = game.getToolCards().get(pos).getNumber();
    }

    public int getToolCardNumber(){

        return toolCardNumber;
    }
}
