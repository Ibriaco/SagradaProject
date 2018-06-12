package it.polimi.se2018.Model.Event;

/**
 *
 * @author Ibrahim El Shemy
 * @author Marco Gasperini
 */
public class LoggedUserEvent extends MVEvent {

    private String username;
    private Boolean approved;
    private String state = "";

    /**
     *
     * @param username refers to the current player in game
     */
    public LoggedUserEvent(String username, Boolean approved) {
        super(username);
        this.username = username;
        this.approved = approved;
    }

    public String getUsername() {

        return username;
    }
    public Boolean getApproved() {

        return approved;
    }

    public void setState(String state) {

        this.state = state;
    }

    public void printState(){

        System.out.println(state);
    }

}
