package it.polimi.se2018.Client.View;

import it.polimi.se2018.Server.Model.Game;
import it.polimi.se2018.Server.Model.Player;

public abstract class VCEvent {

    private String username;

    public VCEvent(Player player) {
        this.username = player.getUsername();
    }

    public String getUsername() {

        return username;
    }




}
