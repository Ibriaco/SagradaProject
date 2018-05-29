package it.polimi.se2018.Client.View;
import it.polimi.se2018.Server.Model.Player;
import it.polimi.se2018.Server.Model.Game;

/**
 * Events that return connection type and the username of the player who logs in.
 * @author Ibrahim El Shemy
 * @author Marco Gasperini
 */
public class LoginEvent extends VCEvent {

    private String connectionType;

    /**
     *
     * @param connectionType connection type (either RMI/Socket).
     * @param username username of the current player.
     */
    public LoginEvent(String connectionType, String username) {

        super(username);
        this.connectionType = connectionType;
    }

    public String getConnectionType() {

        return connectionType;
    }

}
