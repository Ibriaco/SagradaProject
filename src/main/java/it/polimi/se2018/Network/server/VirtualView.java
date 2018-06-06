package it.polimi.se2018.Network.server;

import it.polimi.se2018.MyObservable;
import it.polimi.se2018.MyObserver;
import it.polimi.se2018.View.ViewInterface;

import java.util.ArrayList;
import java.util.Observable;

public class VirtualView implements ViewInterface {

    private ArrayList<MyObserver> observerCollection;

    @Override
    public void updateWindowCard() {

    }

    @Override
    public void showUI() {

    }

    @Override
    public void update(MyObservable o, Object arg) {

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

    // mi serve array list observerCOllection per tenere traccia degli observers
}
