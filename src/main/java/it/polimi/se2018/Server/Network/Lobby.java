package it.polimi.se2018.Server.Network;

import it.polimi.se2018.Server.Model.Player;

import java.util.ArrayList;

public class Lobby {

    private int onlinePlayersN;
    private ArrayList<Player> onlinePlayers;
    private int timer;

    public Lobby(Player firstPlayer){
        onlinePlayersN = 1;
        onlinePlayers = new ArrayList<>();
        onlinePlayers.add(firstPlayer);
        timer = 10;
    }

    public void addOnlinePlayer(Player p){

        onlinePlayers.add(p);
    }

    public void removeOnlinePlayer(Player p){

        onlinePlayers.remove(p);
    }

    public int getOnlinePlayersN(){
        return onlinePlayersN;
    }

    


}
