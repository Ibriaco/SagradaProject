package it.polimi.se2018.model.event;

import it.polimi.se2018.view.ui.ViewInterface;

public class PrivateCardEvent implements MVEvent {

    private String privateName;
    private String username;

    public PrivateCardEvent(String username, String privateName) {
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

    public String getPrivateName() {
        return privateName;
    }

    public void printPrivateName(){

        System.out.println(privateName);
    }
}

