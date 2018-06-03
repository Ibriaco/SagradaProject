package it.polimi.se2018.Network;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Lobby {

    private int onlinePlayersN;
    private ArrayList<String> onlinePlayers;
    private int timer;

    public Lobby(){
        System.out.println("Lobby creata");
        onlinePlayersN = 0;
        onlinePlayers = new ArrayList<>();
        timer = 10;
        //startTimer();
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
    public void addOnlinePlayer(String p){
        onlinePlayers.add(p);

    }

    public void removeOnlinePlayer(String p){

        onlinePlayers.remove(p);
    }

    public int getOnlinePlayersN(){
        return onlinePlayersN;
    }

    public List<String> getOnlinePlayers(){
        return onlinePlayers;
    }


}
