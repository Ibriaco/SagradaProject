package it.polimi.se2018.Client.View;
import it.polimi.se2018.Server.Model.Player;
import it.polimi.se2018.Server.Model.Game;

public class LoginEvent extends VCEvent {

    private String connectionType;
    private String username;

    public LoginEvent(String connectionType, Player player) {
        this.connectionType = connectionType;
        username = player.getUsername();
    }

    public String getConnectionType() {

        return connectionType;
    }
}
