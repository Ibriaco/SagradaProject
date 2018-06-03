package it.polimi.se2018.Network;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Lobby {

    private int onlinePlayersN;
    private ArrayList<String> onlinePlayers;

    public Lobby(){
        System.out.println("Lobby creata");
        onlinePlayersN = 0;
        onlinePlayers = new ArrayList<>();
        //startTimer();
    }



    public void addOnlinePlayer(String p){
        onlinePlayers.add(p);
        onlinePlayersN++;
    }

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
