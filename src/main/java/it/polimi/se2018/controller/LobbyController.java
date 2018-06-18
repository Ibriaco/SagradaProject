package it.polimi.se2018.controller;

import it.polimi.se2018.model.event.LoggedUserEvent;
import it.polimi.se2018.model.event.NewGameEvent;
import it.polimi.se2018.model.*;
import it.polimi.se2018.network.server.VirtualView;
import it.polimi.se2018.view.viewevents.ChooseCardEvent;
import it.polimi.se2018.view.viewevents.VCEvent;
import org.json.simple.parser.ParseException;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import static it.polimi.se2018.view.ui.CLIUtils.printOnConsole;

/**
 * Class that controls all the events concerned with the game lobby
 * @author Ibrahim El Shemy
 * @author Gregorio Galletti
 * @author Marco Gasperini
 */
public class LobbyController {

    private Lobby waitingLobby;
    private Game game;
    private static int timer = 10;
    private EventsController eventsController;
    private VirtualView virtualView;
    private static int ready=0;
    private ArrayList<WindowCard> windowCardList= new ArrayList<>();
    private ArrayList<String> username = new ArrayList<>();

    public LobbyController(EventsController ec, VirtualView virtualView) {
        waitingLobby = new Lobby();
        this.virtualView = virtualView;
        printOnConsole("Lobby controller creato");
        eventsController = ec;
    }

    /**
     * Handles the login event
     * @param event notified by the Virtual view
     * @throws RemoteException thrown exception
     */
    public void handleLogin(VCEvent event) throws RemoteException, InvalidConnectionException, InvalidViewException {
        String username = event.getUsername();
        LoggedUserEvent logEvent;
        if(checkUser(username)&&checkOnlinePlayers()&&checkTime()) {
            virtualView.addClientToMap(username, virtualView.getClientTemp());
            addInLobby(username);
            logEvent = new LoggedUserEvent(username,true);
            logEvent.setState("Logged in successfully!");
            // queste 3 stampe sono sul server. si potrebbero rimuovere??
            String playersNumber = String.valueOf(getLobby().getOnlinePlayers().size());
            printOnConsole("User " + username + " logged in successfully!");
            printOnConsole("Online players " + playersNumber);
            eventsController.setMvEvent(logEvent);
            eventsController.notifyObservers();
        }
        else {
            logEvent = new LoggedUserEvent(username, false);
            if (!checkTime()) {
                logEvent.setState("TIMER EXEPIRED!");
            } else if (!checkOnlinePlayers()) {
                logEvent.setState("LOBBY IS FULL!");
            } else if (!checkUser(username)) {
                logEvent.setState("USERNAME ALREADY USED!");
            }
            virtualView.getClientTemp().sendMVEvent(logEvent);
            virtualView.stampa();
        }
        if (checkStartGame()) {
            try {
                setupGame();
            } catch (IOException | ParseException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Checks if there are no occurrences of the same username in the lobby
     * @param user username of the player
     * @return true if there are no occurrences, else, false is returned
     */
    public boolean checkUser(String user) {
        for (String u : waitingLobby.getOnlinePlayers()) {
            if (user.equals(u))
                return false;
        }
        return true;
    }

    /**
     * Checks if the players in the lobby are not more than 4
     * @return true if the players are less than 4, else, false is returned
     */
    public boolean checkOnlinePlayers() {
        return waitingLobby.getOnlinePlayersN() != 2;
    }
    //DOBBIAMO RIMETTERLO A 4!!!!!!!!!!!!

    /**
     * Checks if the timer's expired
     * @return true if the timer's expired, else, false is returned
     */
    public boolean checkTime() {
        return waitingLobby.getOnlinePlayersN() < 2 || getTimer() != 0;
    }

    /**
     * Adds a player in the lobby
     * @param user username of the player
     */
    public synchronized void addInLobby(String user) {

        waitingLobby.addOnlinePlayer(user);

    }

    /**
     * Creates a thread and decrements an integer value second by second, simulating a timer
     * @author Gregorio Galletti
     */
    /*public void launchTimer() {
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
        }).start();
    }*/

    public boolean checkStartGame(){

        return !checkOnlinePlayers() || !checkTime();
    }

    //in setupGame creo evento setupGameEvent
    public void setupGame() throws InvalidViewException, InvalidConnectionException, IOException, ParseException {
        game = new Game(virtualView.getClients().size());
        game.registerObserver(virtualView);
        for (String s: virtualView.getClients().keySet()) {
            game.getPlayers().add(new Player(s, "CLI"));
        }
        game.dealPrivateCards();
        game.dealWindowCards();
        eventsController.setGame(game);
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

    public void handleWindowCard (ChooseCardEvent event) throws InvalidConnectionException, RemoteException, InvalidViewException {
        game.findPlayer(event.getUsername()).setWindowCard(event.getWindowCard());
        windowCardList.add(event.getWindowCard());
        username.add(event.getUsername());
        setReady();

        if (ready == game.getPlayers().size()) {
            NewGameEvent newGameEvent = new NewGameEvent(windowCardList, username);
            eventsController.setMvEvent(newGameEvent);
            eventsController.notifyObservers();
        }
        //mandare evento a tutti con tutte le carte settate
    }

    private static void setReady() {
        LobbyController.ready++;
    }

}