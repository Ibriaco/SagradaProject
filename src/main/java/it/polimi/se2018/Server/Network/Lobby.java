package it.polimi.se2018.Server.Network;

import it.polimi.se2018.Server.Model.Player;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class Lobby {

    private int onlinePlayersN;
    private ArrayList<Player> onlinePlayers;
    private int timer;

    public Lobby(){
        System.out.println("Lobby creata");
        onlinePlayersN = 0;
        onlinePlayers = new ArrayList<>();
        timer = 10;
        startTimer();
    }

    private void startTimer(){
        for(int t=timer ; t >= 0; t--){
            System.out.println(t);
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
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
