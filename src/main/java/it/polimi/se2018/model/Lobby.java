package it.polimi.se2018.model;

import it.polimi.se2018.model.event.MVEvent;
import it.polimi.se2018.MyObservable;
import it.polimi.se2018.MyObserver;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

/**
 * Lobby class of the game
 * @author Gregorio Galletti
 */
public class Lobby implements MyObservable{

    private int onlinePlayersN;
    private ArrayList<String> onlinePlayers;
    private ArrayList<MyObserver> observerCollection = new ArrayList<>();
    private MVEvent mvEvent;

    public Lobby(){
        System.out.println("Lobby creata");
        onlinePlayersN = 0;
        onlinePlayers = new ArrayList<>();
        //startTimer();
    }


    /**
     * Adds a player in the lobby
     * @param p player added in the lobby
     */
    public void addOnlinePlayer(String p){
        onlinePlayers.add(p);
        onlinePlayersN++;
    }

    /**
     * Removes a player from the lobby
     * @param p player removed from the lobby
     */
    public void removeOnlinePlayer(String p){

        onlinePlayers.remove(p);
        onlinePlayersN--;
    }

    public int getOnlinePlayersN(){
        return onlinePlayersN;
    }

    public List<String> getOnlinePlayers(){
        return onlinePlayers;
    }

    @Override
    public void registerObserver(MyObserver observer) {
        observerCollection.add(observer);
    }

    @Override
    public void unregisterObserver(MyObserver observer){
        observerCollection.remove(observer);
    }

    @Override
    public void notifyObservers() throws RemoteException, InvalidConnectionException, InvalidViewException {
        for (MyObserver o: observerCollection) {
            o.update(this, mvEvent);
        }
    }
}
