package it.polimi.se2018.View.ViewEvents;

import it.polimi.se2018.Controller.ControllerInterface;

/**
 * Event that lets the player use a Tool Card
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
    public void accept(ControllerInterface controller){
        controller.handleVCEvent(this);
    }
}
