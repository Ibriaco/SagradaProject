package it.polimi.se2018.Model.Event;

import it.polimi.se2018.View.UI.ViewInterface;

public class SetupGameEvent implements MVEvent {

    private String privateName;
    private String username;

    public SetupGameEvent(String username, String privateName) {
        this.username = username;
        this.privateName = privateName;
    }

    @Override
    public void accept(ViewInterface vi){
        vi.handleMVEvent(this);
    }

    @Override
    public String getUsername() {
        return username;
    }

    public void printPrivateName(){

        System.out.println(privateName);
    }
}

