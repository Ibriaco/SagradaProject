package it.polimi.se2018.controller;

import it.polimi.se2018.model.event.GameUpdateEvent;
import it.polimi.se2018.model.event.IsTurnEvent;
import it.polimi.se2018.model.event.LoggedUserEvent;
import it.polimi.se2018.model.*;
import it.polimi.se2018.model.event.UpdateGameEvent;
import it.polimi.se2018.network.server.VirtualView;
import it.polimi.se2018.view.viewevents.ChooseCardEvent;
import it.polimi.se2018.view.viewevents.VCEvent;
import org.json.simple.parser.ParseException;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

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
    private ArrayList<String> username = new ArrayList<>();
    private static final Logger LOGGER = Logger.getLogger(LobbyController.class.getName());

    public LobbyController(EventsController ec, VirtualView virtualView) {
        waitingLobby = new Lobby();
        this.virtualView = virtualView;
        printOnConsole("Lobby controller creato");
        eventsController = ec;
    }


    public List<String> getUsername() {
        return username;
    }


    /**
     * Handles the login event
     * @param event notified by the Virtual view
     * @throws RemoteException thrown exception
     */
    public void handleLogin(VCEvent event) throws IOException, InvalidConnectionException, InvalidViewException, ParseException {
        String username = event.getUsername();
        LoggedUserEvent logEvent;
        launchTimer();

        if(checkUser(username) && checkOnlinePlayers() && checkTime()) {
            virtualView.addClientToMap(username, virtualView.getClientTemp());
            //virtualView.serverBeat(username);
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
                eventsController.setReconnection(true);
                setupGame();
            } catch (IOException | ParseException e) {
                LOGGER.log(Level.SEVERE, e.toString(), e);
            }
        }
    }

    /**
     * Method that handles reconnection of a player
     * @author Marco Gasperini
     * @param event event
     * @throws InvalidConnectionException exception
     * @throws ParseException exception
     * @throws InvalidViewException exception
     * @throws IOException exception
     */
    public void handleReconnection (VCEvent event) throws InvalidConnectionException, ParseException, InvalidViewException, IOException {
        String username = event.getUsername();
        if (virtualView.getRemovedClients().contains(username)){
            virtualView.getClients().put(username, virtualView.getClientTemp());
            virtualView.getRemovedClients().remove(username);
            //virtualView.serverBeat(username);
            UpdateGameEvent updateGameEvent = new UpdateGameEvent(game.getWindowCardList(), this.username, game.getRolledDice());
            updateGameEvent.setUsername(username);
            eventsController.setMvEvent(updateGameEvent);
            eventsController.notifyObservers();
        }
        else {
            LoggedUserEvent logEvent = new LoggedUserEvent(username, false);
            logEvent.setState("INVALID USERNAME!");
            virtualView.getClientTemp().sendMVEvent(logEvent);
        }
    }

    private void launchTimer(){
        new Thread(()->{
            synchronized (waitingLobby.getLock()) {
                while (waitingLobby.getOnlinePlayersN() < 2) {

                    try {
                        waitingLobby.getLock().wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }

            System.out.println("almeno 2 giocatori connessi: parte timer");

            while (timer > 0) {
            System.out.println("Timer: " + timer);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            timer--;
            }


        }).start();
    }

    /**
     * Checks if there are no occurrences of the same username in the lobby
     * @param user username of the player
     * @return true if there are no occurrences, else, false is returned
     */
    private boolean checkUser(String user) {
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
    private boolean checkOnlinePlayers() {
        return waitingLobby.getOnlinePlayersN() != 3;
    }
    //DOBBIAMO RIMETTERLO A 4!!!!!!!!!!!!

    /**
     * Checks if the timer's expired
     * @return true if the timer's expired, else, false is returned
     */
    private boolean checkTime() {
        return waitingLobby.getOnlinePlayersN() < 2 || getTimer() != 0;
    }

    /**
     * Adds a player in the lobby
     * @param user username of the player
     */
    private synchronized void addInLobby(String user) {

        waitingLobby.addOnlinePlayer(user);

    }

    /**
     * Method that checks if a game can start
     * @return true if game can start, false otherwise
     */
    private boolean checkStartGame(){

        return !checkOnlinePlayers() || !checkTime();
    }

    /**
     * Method that sets up a game
     * @throws InvalidViewException exception
     * @throws InvalidConnectionException exception
     * @throws IOException exception
     * @throws ParseException exception
     */
    private void setupGame() throws InvalidViewException, InvalidConnectionException, IOException, ParseException {
        game = new Game(virtualView.getClients().size());
        game.registerObserver(virtualView);
        for (String s: virtualView.getClients().keySet()) {
            game.getPlayers().add(new Player(s, "CLI"));
        }
        game.setPlayerNumber(game.getPlayers().size());
        game.dealPrivateCards();
        game.dealWindowCards();
        eventsController.setGame(game);
        eventsController.getToolCardController().setGame(game);
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

    /**
     * Method that adds a chosen card in the list of window cards
     * @param event choose card event
     * @throws InvalidConnectionException exception
     * @throws RemoteException exception
     * @throws InvalidViewException exception
     */
    public void handleWindowCard (ChooseCardEvent event) throws InvalidConnectionException, RemoteException, InvalidViewException {
        game.findPlayer(event.getUsername()).setWindowCard(event.getWindowCard());
        game.getWindowCardList().add(event.getWindowCard());
        username.add(event.getUsername());
        /*setReady();

        if (ready == game.getPlayers().size()) {
            UpdateGameEvent newGameEvent = new UpdateGameEvent(windowCardList, username);
            eventsController.setMvEvent(newGameEvent);
            eventsController.notifyObservers();
        }*/
    }

    public void newGame() throws InvalidConnectionException, IOException, InvalidViewException, ParseException {
        for (int i=0; i<username.size()*2+1; i++) {
            game.getRolledDice().add(new Die(game.getColorList()));
        }
        UpdateGameEvent updateGameEvent = new UpdateGameEvent(game.getWindowCardList(),username,game.getRolledDice());
        eventsController.setMvEvent(updateGameEvent);
        eventsController.notifyObservers();
        game.setFirstPlayer(game.getPlayers().get(0));
        IsTurnEvent isTurnEvent = new IsTurnEvent(game.getPlayers().get(0).getUsername(), true);
        eventsController.setMvEvent(isTurnEvent);
        //System.out.println("lancio il tread del primo player");
        //eventsController.launchThread(0);
        eventsController.notifyObservers();
    }



}
