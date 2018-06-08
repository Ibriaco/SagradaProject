package it.polimi.se2018.View;

public class TimerExpiredEvent extends VCEvent {
    /**
     * @param username username of the current player
     */
    public TimerExpiredEvent(String username) {
        super(username);
    }
}
