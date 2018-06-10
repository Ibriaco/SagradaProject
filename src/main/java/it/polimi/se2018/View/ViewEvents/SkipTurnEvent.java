package it.polimi.se2018.View.ViewEvents;

/**
 * Event that notifies that a turn is skipped.
 * @author Ibrahim El Shemy
 * @author Marco Gasperini
 */
public class SkipTurnEvent extends VCEvent {

    /**
     * @param username username of the current player
     */
    public SkipTurnEvent(String username) {
        super(username);
    }
}
