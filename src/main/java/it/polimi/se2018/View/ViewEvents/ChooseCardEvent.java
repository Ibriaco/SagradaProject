package it.polimi.se2018.View.ViewEvents;

import it.polimi.se2018.Controller.ControllerInterface;
/**
 * Event that returns the number and the side of a chosen Card by the player
 * @author Ibrahim El Shemy
 * @author Marco Gasperini
 */
public class ChooseCardEvent implements VCEvent {

    private String windowName;
    private String username;

    /**
     *
     * @param username username of the current player.
     * @param windowName refers to the name of the Card
     */
    public ChooseCardEvent(String username,String windowName) {

        this.username = username;
        this.windowName = windowName;
    }

    public String getWindowName() {

        return windowName;
    }

    @Override
    public void accept(ControllerInterface controller){
        controller.handleVCEvent(this);
    }

    public String getUsername(){
        return username;
    }
}
