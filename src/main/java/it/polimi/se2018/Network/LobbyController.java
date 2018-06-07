package it.polimi.se2018.Network;

import it.polimi.se2018.Controller.EventsController;
import it.polimi.se2018.Message;
import it.polimi.se2018.Model.Event.LoggedUserEvent;
import it.polimi.se2018.Network.client.ClientInterface;
import it.polimi.se2018.Network.server.VirtualView;
import it.polimi.se2018.View.VCEvent;

public class LobbyController {

    private Lobby waitingLobby;
    private static int timer = 10;
    private EventsController eventsController;
    private VirtualView virtualView;

    public LobbyController() {
        virtualView = new VirtualView();
        waitingLobby = new Lobby(virtualView);
        System.out.println("Lobby controller creato");
    }

    public boolean checkUser(String user) {
        for (String u : waitingLobby.getOnlinePlayers()) {
            if (user.equals(u))
                return false;
        }
        return true;
    }

    public boolean checkOnlinePlayers() {
        if (waitingLobby.getOnlinePlayersN() == 4)
            return false;
        else
            return true;
    }

    public boolean checkTime() {
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





    public int getTimer() {
        return timer;
    }

    public void setTimer(int timer) {
        LobbyController.timer = timer;
    }

    public Lobby getLobby() {
        return waitingLobby;
    }
}
