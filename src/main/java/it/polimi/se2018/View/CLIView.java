package it.polimi.se2018.View;


import it.polimi.se2018.Model.Event.CheckRoundTrackEvent;


public class CLIView extends View {

    String command;

    public String getCommand() {

        return command;
    }

    public void writeOnLogger(String c){

    }

    public void selectEvent(String c){

    }

    public LoginEvent createLoginEvent(String username, String command){
        LoginEvent event = new LoginEvent();
        return event;
    }

    public PlaceDieEvent createPlaceDieEvent(String username, String command){
        PlaceDieEvent event = new PlaceDieEvent();
        return event;
    }

    public SkipTurnEvent createSkipTurnEvent(String username, String command){
        SkipTurnEvent event = new SkipTurnEvent();
        return event;
    }

    public UseToolEvent createUseToolEvenet(String username, String command){
        UseToolEvent event = new UseToolEvent();
        return event;
    }

    public RollDiceEvent createRollDiceEvent(String username, String command){
        RollDiceEvent event = new RollDiceEvent();
        return event;
    }

    public ChooseCardEvent createChooseCardEvent(String username, String command){
        ChooseCardEvent event = new ChooseCardEvent();
        return event;
    }

    public CheckRoundTrackEvent createCheckRoundTrackEvent(String username, String command){
        CheckRoundTrackEvent event = new CheckRoundTrackEvent();
        return event;
    }



}
