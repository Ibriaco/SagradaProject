package it.polimi.se2018.View;

/**
 * Event that returns the number and the side of a chosen Card by the player
 * @author Ibrahim El Shemy
 * @author Marco Gasperini
 */
public class ChooseCardEvent extends VCEvent {

    private String windowName;

    /**
     *
     * @param username username of the current player.
     * @param windowName refers to the name of the Card
     */
    public ChooseCardEvent(String username,String windowName) {

        super(username);
        this.windowName = windowName;
    }

    public String getWindowName() {

        return windowName;
    }
}
