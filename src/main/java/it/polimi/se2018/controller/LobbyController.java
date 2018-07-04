package it.polimi.se2018.controller;

import it.polimi.se2018.model.*;
import it.polimi.se2018.model.event.IsTurnEvent;
import it.polimi.se2018.model.event.LoggedUserEvent;
import it.polimi.se2018.model.event.UpdateGameEvent;
import it.polimi.se2018.network.server.VirtualView;
import it.polimi.se2018.org.json.simple.parser.ParseException;
import it.polimi.se2018.view.viewevents.ChooseCardEvent;
import it.polimi.se2018.view.viewevents.VCEvent;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static it.polimi.se2018.ServerConfig.*;
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
    private int timer = LOBBY_TIMER;
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
            addInLobby(username);
            logEvent = new LoggedUserEvent(username,BOOL_TRUE);
            logEvent.setState(LOGIN_SUCCESSFULLY);
            String playersNumber = String.valueOf(getLobby().getOnlinePlayers().size());
            printOnConsole(USER + username +" "+LOGIN_SUCCESSFULLY);
            printOnConsole(ONLINE_PLAYERS + playersNumber);
            eventsController.setMvEvent(logEvent);
            eventsController.notifyObservers();
        }
        else {
            logEvent = new LoggedUserEvent(username, BOOL_FALSE);
            if (!checkTime()) {
                logEvent.setState(TIMER_EXPIRED);
            } else if (!checkOnlinePlayers()) {
                logEvent.setState(FULL_LOBBY);
            } else if (!checkUser(username)) {
                logEvent.setState(USERNAME_ALREADY_USED);
            }
            virtualView.getClientTemp().sendMVEvent(logEvent);
        }
        if (checkStartGame()) {
            try {
                eventsController.setReconnection(BOOL_TRUE);
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
        String user = event.getUsername();
        if (virtualView.getRemovedClients().contains(user)){
            virtualView.getClients().put(user, virtualView.getClientTemp());
            virtualView.getRemovedClients().remove(user);
            UpdateGameEvent updateGameEvent = new UpdateGameEvent(game.getWindowCardList(), this.username, game.getRolledDice());
            updateGameEvent.setUsername(user);
            eventsController.setMvEvent(updateGameEvent);
            eventsController.notifyObservers();
        }
        else {
            LoggedUserEvent logEvent = new LoggedUserEvent(user, BOOL_FALSE);
            logEvent.setState("Invalid username!");
            virtualView.getClientTemp().sendMVEvent(logEvent);
        }
    }

    private void launchTimer(){
        new Thread(()->{
            synchronized (waitingLobby.getLock()) {
                while (waitingLobby.getOnlinePlayersN() < TWO_VALUE) {

                    try {
                        waitingLobby.getLock().wait();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            }

            while (timer > ZERO_VALUE) {
            //LOGGER.log(Level.INFO,"Timer: " + timer);
            try {
                Thread.sleep(SLEEP_TIME);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
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
                return BOOL_FALSE;
        }
        return BOOL_TRUE;
    }

    /**
     * Checks if the players in the lobby are not more than 4
     * @return true if the players are less than 4, else, false is returned
     */
    private boolean checkOnlinePlayers() {

        return waitingLobby.getOnlinePlayersN() != TWO_VALUE;
    }
    //DOBBIAMO RIMETTERLO A 4!!!!!!!!!!!!

    /**
     * Checks if the timer's expired
     * @return true if the timer's expired, else, false is returned
     */
    private boolean checkTime() {

        return waitingLobby.getOnlinePlayersN() < TWO_VALUE || getTimer() != ZERO_VALUE;
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
            game.getPlayers().add(new Player(s, CLI_UI));
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
        this.timer = timer;
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
    public void handleWindowCard (ChooseCardEvent event){
        game.findPlayer(event.getUsername()).setWindowCard(event.getWindowCard());
        game.getWindowCardList().add(event.getWindowCard());
        game.getPlayers().get(game.getPlayers().indexOf(game.findPlayer(event.getUsername()))).setTokens(event.getWindowCard().getDifficulty());
        username.add(event.getUsername());
    }

    public void newGame() throws InvalidConnectionException, IOException, InvalidViewException, ParseException {
        for (int i=0; i<username.size()*TWO_VALUE+ONE_VALUE; i++) {
            game.getRolledDice().add(new Die(game.getColorList()));
        }
        UpdateGameEvent updateGameEvent = new UpdateGameEvent(game.getWindowCardList(),username,game.getRolledDice(), game.getRoundCells());
        eventsController.setMvEvent(updateGameEvent);
        eventsController.notifyObservers();
        game.setFirstPlayer(game.getPlayers().get(0));
        IsTurnEvent isTurnEvent = new IsTurnEvent(game.getPlayers().get(0).getUsername(), BOOL_TRUE);
        eventsController.setMvEvent(isTurnEvent);
        eventsController.getTimer().start();
        eventsController.notifyObservers();
    }



}
