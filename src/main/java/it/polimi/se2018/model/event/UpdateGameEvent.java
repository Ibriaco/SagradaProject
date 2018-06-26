package it.polimi.se2018.model.event;

import it.polimi.se2018.model.Die;
import it.polimi.se2018.model.RoundCell;
import it.polimi.se2018.model.WindowCard;
import it.polimi.se2018.view.ui.ViewInterface;

import java.util.List;

public class UpdateGameEvent implements MVEvent {
    //aggiungere draftpool e roundtrack
    private String username = "ALL";
    private List<WindowCard> windowCardList;
    private List<String> user;
    private List<Die> dice;
    private List<RoundCell> roundTrack;

    public UpdateGameEvent(List<WindowCard> windowCardList, List<String> username, List<Die> dice, List<RoundCell> roundTrack){
     this.windowCardList = windowCardList;
     this.user = username;
     this.dice = dice;
     this.roundTrack = roundTrack;
    }
    public UpdateGameEvent(List<WindowCard> windowCardList, List<String> username, List<Die> dice){
     this.windowCardList = windowCardList;
     this.user = username;
     this.dice = dice;
    }
    public UpdateGameEvent(List<WindowCard> windowCardList, List<String> username, List<Die> dice, String privateObjective){

    }

    public List<WindowCard> getWindowCardList() {
        return windowCardList;
    }

    public List<String> getUser() {
        return user;
    }

    public List<Die> getDice() {
        return dice;
    }

    public List<RoundCell> getRoundTrack() {
        return roundTrack;
    }

    @Override
    public void accept(ViewInterface vi) {
        vi.handleMVEvent(this);
    }

    @Override
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}
