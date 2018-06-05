package it.polimi.se2018.Network;

import it.polimi.se2018.Controller.EventsController;
import it.polimi.se2018.Message;
import it.polimi.se2018.Network.RMI.RMISender;

import java.rmi.RemoteException;

public class LobbyController {

    private RMISender sender;
    private Lobby waitingLobby;
    private static int timer = 10;
    private EventsController eventsController;


    public LobbyController() {
        waitingLobby = new Lobby();
        System.out.println("Lobby controller creato");
    }

    public boolean checkUser(String user) {
        for (String u : waitingLobby.getOnlinePlayers()) {
            if (user.equals(u))
                return false;
        }
        return true;
    }

    public boolean checkOnlinePlayers(String user) {
        if (waitingLobby.getOnlinePlayersN() == 4)
            return false;
        else
            return true;
    }

    public boolean checkTime(String user) {
        if (waitingLobby.getOnlinePlayersN() >= 2 && getTimer() == 0)
            return false;
        else
            return true;
    }

    public synchronized void addInLobby(String user) {
        waitingLobby.addOnlinePlayer(user);
    }

    public void launchTimer() {
        new Thread(() -> {




            Message timeout = new Message("");
            boolean startGame = false;
            while (!startGame) {
                    while (timer != 0) {
                        timeout.setMessage(String.valueOf(timer));
                        //socketServer.send(timeout);

                        try {
                            sender.send(timeout);
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }

                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        timer--;
                        if(getTimer()==0)
                            startGame = true;
                    }
            }

            //eventsController = new EventsController(rmiServer, socketServer);
        }).start();
    }




    public void addSender(RMISender s) {
        sender = s;
        launchTimer();
        //this.start();
    }

    public int getTimer() {
        return timer;
    }

    public void setTimer(int timer) {
        LobbyController.timer = timer;
    }

    public RMISender getSender() {
        return sender;
    }

    public Lobby getLobby() {
        return waitingLobby;
    }
}
