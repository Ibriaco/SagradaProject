package it.polimi.se2018.Model.Event;

import it.polimi.se2018.View.UI.ViewInterface;

/**
 * Event that notifies that a Card has been chosen by the player ( there are many types of cards ).
 * @author Ibrahim El Shemy
 * @author Marco Gasperini
 */
public class ChosenCardEvent implements MVEvent {

    private String windowName;
    private String publicCardName;
    private String toolCardName;
    private String privateCardName;
    private String username;

    /*public ChosenCardEvent(Player player, WindowCard windowCard) {
        this.username = username;

        this.windowName = windowCard.getWindowName();
    }*/


    /*public ChosenCardEvent(Player player, int side, int pos) {
        super(player);

        if(side == 0)
            this.windowName = player.getWindowCardAssociations()[pos].getFront().getWindowName();
        else if(side == 1)
            this.windowName = player.getWindowCardAssociations()[pos].getBack().getWindowName();
    }*/


    /*public ChosenCardEvent(Player askingPlayer, Player ownerPlayer) {
        super(askingPlayer);

        this.privateCardName = ownerPlayer.getPrivateObjective().getTitle();
    }*/


    public String getWindowName() {

        return windowName;
    }

    @Override
    public void accept(ViewInterface vi) {

    }

    public String getUsername() {

        return username;
    }



}
