package it.polimi.se2018.Model.Event;

import it.polimi.se2018.View.UI.ViewInterface;

public class SetupGameEvent implements MVEvent {

    private String privateName;
    private String username;
    public SetupGameEvent(String username) {

        this.username = username;
    }


    @Override
    public void accept(ViewInterface vi) {

    }

    @Override
    public String getUsername() {
        return username;
    }
}

