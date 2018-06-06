package it.polimi.se2018.View;


import it.polimi.se2018.MyObservable;
import it.polimi.se2018.MyObserver;

import java.util.ArrayList;

public class CLIView implements ViewInterface {

    private ArrayList<MyObserver> observerCollection;

    String command;

    public String getCommand() {

        return command;
    }

    @Override
    public void updateWindowCard() {

    }

    @Override
    public void showUI() {

    }

    @Override
    public void registerObserver(MyObserver observer) {

    }

    @Override
    public void unregisterObserver(MyObserver observer) {

    }

    @Override
    public void notifyObservers() {

    }

    @Override
    public void update(MyObservable o, Object arg) {

    }
/*
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

    public RoundTrackEvent createCheckRoundTrackEvent(String username, String command){
        RoundTrackEvent event = new RoundTrackEvent();
        return event;
    }
*/


}
