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
     * @param connectionType refers to the connection type (either RMI/Socket).
     * @param player refers to the current player.
     */
    public LoginEvent(String connectionType, Player player) {

        super(player);
        this.connectionType = player.getConnectionType();
    }

    public String getConnectionType() {

        return connectionType;
    }

}
