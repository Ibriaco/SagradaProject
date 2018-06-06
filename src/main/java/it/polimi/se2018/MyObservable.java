package it.polimi.se2018;

public interface MyObservable {

    void registerObserver(MyObserver observer);
    void unregisterObserver(MyObserver observer);
    void notifyObservers();
}
