package it.polimi.se2018.Network;

import it.polimi.se2018.Network.client.ClientInterface;
import it.polimi.se2018.Network.server.VirtualView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Lobby {

    private int onlinePlayersN;
    private ArrayList<String> onlinePlayers;
    private VirtualView virtualView;

    public Lobby(VirtualView virtualView){
        System.out.println("Lobby creata");
        onlinePlayersN = 0;
        onlinePlayers = new ArrayList<>();
        this.virtualView = virtualView;
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


    public VirtualView getVirtualView() {
        return virtualView;
    }
}
