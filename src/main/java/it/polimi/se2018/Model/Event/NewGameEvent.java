package it.polimi.se2018.Model.Event;

import it.polimi.se2018.Model.WindowCard;
import it.polimi.se2018.View.UI.ViewInterface;

import java.util.List;

public class NewGameEvent implements MVEvent {

    private String username = "ALL";
    private List<WindowCard> windowCardList;
    private List<String> user;

    public NewGameEvent(List<WindowCard> windowCardList, List<String> username){
     this.windowCardList = windowCardList;
     user = username;
    }

    public List<WindowCard> getWindowCardList() {
        return windowCardList;
    }

    public List<String> getUser() {
        return user;
    }

    @Override
    public void accept(ViewInterface vi) {
        vi.handleMVEvent(this);
    }

    @Override
    public String getUsername() {
        return username;
    }
}
