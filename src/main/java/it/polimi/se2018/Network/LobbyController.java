package it.polimi.se2018.Network;

import it.polimi.se2018.Controller.EventsController;
import it.polimi.se2018.Message;
import it.polimi.se2018.MyObservable;
import it.polimi.se2018.MyObserver;
import it.polimi.se2018.Network.server.VirtualView;

import java.rmi.RemoteException;
import java.util.ArrayList;

public class LobbyController implements MyObserver, MyObservable {

    private Lobby waitingLobby;
    private static int timer = 10;
    private EventsController eventsController;
    private VirtualView virtualView;
    private ArrayList<MyObserver> observerCollection;

    public LobbyController() {
        virtualView = new VirtualView();
        waitingLobby = new Lobby(virtualView);
        System.out.println("Lobby controller creato");
    }

    public void handleLogin(String username) throws RemoteException {
        if(checkUser(username)&&checkOnlinePlayers()&&checkTime()) {
            addInLobby(username);
            System.out.println("User " + username + " logged in successfully!");
            System.out.println("Online players " + String.valueOf(this.getLobby().getOnlinePlayers().size()));
            this.getLobby().getVirtualView().getClients().get(username).notify("User " + username + " loggato");
        }
        else if(!checkTime())
            this.getLobby().getVirtualView().getClients().get(username).notify("Timer expired!");
        else if(!checkOnlinePlayers())
            this.getLobby().getVirtualView().getClients().get(username).notify("Lobby is already full! Please join another lobby!");
        else if(!checkUser(username)) {
            this.getLobby().getVirtualView().getClients().get(username).notify("Invalid username! Please try again!");
        }
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

    @Override
    public void registerObserver(MyObserver observer) throws RemoteException {
        observerCollection.add(virtualView);
    }

    @Override
    public void unregisterObserver(MyObserver observer) throws RemoteException {
        observerCollection.remove(virtualView);
    }

    @Override
    public void notifyObservers() throws RemoteException {
        for (MyObserver observer: observerCollection) {
            update(this, toString());
        }
    }

    @Override
    public void update(MyObservable o, Object arg) throws RemoteException {
        System.out.println(arg.toString());
    }
}
