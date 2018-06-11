package it.polimi.se2018.Network;

import it.polimi.se2018.Model.Player;
import it.polimi.se2018.Network.server.VirtualView;

import java.util.ArrayList;
import java.util.List;

/**
 * Lobby class of the game
 * @author Gregorio Galletti
 */
public class Lobby {

    private int onlinePlayersN;
    private ArrayList<Player> playersList;
    private ArrayList<String> onlinePlayers;

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
}
