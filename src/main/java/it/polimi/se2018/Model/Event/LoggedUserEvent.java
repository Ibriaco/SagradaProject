package it.polimi.se2018.Model.Event;

import it.polimi.se2018.View.UI.ViewInterface;

/**
 *
 * @author Ibrahim El Shemy
 * @author Marco Gasperini
 */
public class LoggedUserEvent implements MVEvent {

    private boolean approved;
    private String state = "";
    private String username;

    /**
     *
     * @param username refers to the current player in game
     */
    public LoggedUserEvent(String username, boolean approved) {
        this.approved = approved;
        this.username = username;
    }

    public void accept(ViewInterface vi){
        vi.handleMVEvent(this);
    }

    public String getUsername() {

        return username;
    }


    public void setState(String state) {

        this.state = state;
    }

    public void printState(){

        System.out.println(state);
    }

}
