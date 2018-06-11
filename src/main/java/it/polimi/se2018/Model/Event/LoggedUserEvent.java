package it.polimi.se2018.Model.Event;

/**
 *
 * @author Ibrahim El Shemy
 * @author Marco Gasperini
 */
public class LoggedUserEvent extends MVEvent {

    private String username;

    /**
     *
     * @param username refers to the current player in game
     */
    public LoggedUserEvent(String username) {
        super(username);
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

}
