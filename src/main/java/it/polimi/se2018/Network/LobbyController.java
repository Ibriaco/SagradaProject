package it.polimi.se2018.Network;

import it.polimi.se2018.Message;
import it.polimi.se2018.Network.RMI.RMIServer;
import it.polimi.se2018.Network.Socket.SocketServer;

import java.rmi.RemoteException;
import java.util.concurrent.TimeUnit;

public class LobbyController extends Thread{

    private SocketServer socketServer;
    private RMIServer rmiServer;
    private Lobby waitingLobby;
    private static int timer = 10;


    public LobbyController() {
        waitingLobby = new Lobby();
        System.out.println("Lobby controller creato");
    }

    public boolean checkUser(String user){
        if(waitingLobby.getOnlinePlayersN() == 4)
            return false;
        for (String u : waitingLobby.getOnlinePlayers()) {
            if(user.equals(u))
                return false;
        }
        return true;
    }
    
    public void addInLobby(String user){
        waitingLobby.addOnlinePlayer(user);
    }

    @Override
    public void run() {
        Message timeout = new Message("");
        boolean startGame = false;
        while (!startGame) {
            //System.out.println(waitingLobby.getOnlinePlayersN());
            if (waitingLobby.getOnlinePlayersN() == 2) {
                while (timer != 0) {
                    timeout.setMessage(String.valueOf(timer));
                    socketServer.send(timeout);

                    try {
                        rmiServer.send(timeout);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }

                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    timer--;
                }
            }
        }
    }

    public void addServers(RMIServer rmiServer, SocketServer socketServer) {
        this.socketServer = socketServer;
        this.rmiServer = rmiServer;
        this.start();
    }

}
