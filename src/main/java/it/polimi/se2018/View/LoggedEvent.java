package it.polimi.se2018.View;

public class LoggedEvent extends VCEvent {

    /**
     * @param username username of the current player
     */
    public LoggedEvent(String username) {
        super(username);
    }
}
