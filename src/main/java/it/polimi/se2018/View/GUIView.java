package it.polimi.se2018.View;


import it.polimi.se2018.MyObservable;
import it.polimi.se2018.MyObserver;
import java.util.ArrayList;

public class GUIView implements ViewInterface {

    private ArrayList<MyObserver> observerCollection;

    @Override
    public void updateWindowCard() {

    }

    @Override
    public void showUI() {
        System.out.println("GUI");
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
    public void showTimer(int timer){

    }
    public PlaceDieEvent createPlaceDieEvent(Player p, Game g){

    }
    public SkipTurnEvent createSkipTurnEvent (Player p, Game g) {

    }
    public UseToolCardEvent createUseToolCardEvent (Player p, Game g){

    }
    public RollDiceEvent createRollDieEvent (Player p,Game g){

    }
    public SeePlayerWindowEvent createSeePlayerWindowEvent (Player p, Game g){

    }
    public RoundTrackEvent createCheckRoundTrackEvent(Player p, Game g){

    }*/
}
