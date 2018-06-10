package it.polimi.se2018.View.ViewEvents;

public class TimerExpiredEvent extends VCEvent {
    /**
     * @param username username of the current player
     */
    public TimerExpiredEvent(String username) {
        super(username);
    }
}
