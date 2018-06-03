package it.polimi.se2018.Model.Event;

import it.polimi.se2018.Model.*;

import java.util.ArrayList;

/**
 * Event that notifies that a Card has been chosen by the player ( there are many types of cards ).
 * @author Ibrahim El Shemy
 * @author Marco Gasperini
 */
public class ChosenCardEvent extends MVEvent {

    private String windowName;
    private String username;
    private String publicCardName;
    private String toolCardName;
    private String privateCardName;

    /**
     * @param player refers to the current player.
     * @param windowCard refers to any Window Card from the game.
     */
    public ChosenCardEvent(Player player, WindowCard windowCard) {
        super(player);

        this.windowName = windowCard.getWindowName();
    }

    /**
     *
     * @param player refers to the current player.
     * @param side refers to the side of the WindowCard the player is selecting.
     * @param pos refers to the position of the WindowCardAssociation.
     */
    public ChosenCardEvent(Player player, int side, int pos) {
        super(player);

        if(side == 0)
            this.windowName = player.getWindowCardAssociations()[pos].getFront().getWindowName();
        else if(side == 1)
            this.windowName = player.getWindowCardAssociations()[pos].getBack().getWindowName();
    }

    /**
     *
     * @param askingPlayer refers to the current player.
     * @param ownerPlayer refers to the player who owns the card that is being chosen.
     */
    public ChosenCardEvent(Player askingPlayer, Player ownerPlayer) {
        super(askingPlayer);

        this.privateCardName = ownerPlayer.getPrivateObjective().getTitle();
    }


    /*public ChosenCardEvent(Player player){

    }*/

    public String getWindowName() {

        return windowName;
    }

    public String getUsername() {

        return username;
    }

}
