package it.polimi.se2018.view.viewevents;

import it.polimi.se2018.controller.ControllerInterface;

/**
 * event that rolls dice
 * @author Ibrahim El Shemy
 * @author Marco Gasperini
 */
public class RollDiceEvent implements VCEvent {

    private String username;

    public RollDiceEvent(String username) {
        this.username = username;
    }

    public String getUsername(){
        return username;
    }
    @Override
    public void accept(ControllerInterface controller) {
        controller.handleVCEvent(this);
    }
}
