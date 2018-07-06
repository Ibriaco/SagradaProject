package it.polimi.se2018.controller;

import it.polimi.se2018.MyObservable;
import it.polimi.se2018.MyObserver;
import it.polimi.se2018.model.*;
import it.polimi.se2018.model.event.*;
import it.polimi.se2018.network.server.VirtualView;
import it.polimi.se2018.org.json.simple.parser.ParseException;
import it.polimi.se2018.view.viewevents.*;
import it.polimi.se2018.view.viewevents.RollDiceEvent;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import static it.polimi.se2018.ServerConfig.RUNNING_PLIERS;
import static it.polimi.se2018.ServerConfig.STOP_MESSAGE;
import static it.polimi.se2018.ServerConfig.TURN_TIMER;

/**
 * Controller class that handles events
 * @author Ibrahim El Shemy
 * @author Marco Gasperini
 * @author Gregorio Galletti
 */
public class EventsController implements ControllerInterface, MyObserver, MyObservable {

    private Game game;
    private boolean reconnection = false;

    private LobbyController lobbyController;
    private TurnController turnController;
    private ToolCardController toolCardController;
    private Player p;
    private ArrayList<MyObserver> observerCollection = new ArrayList<>();
    private MVEvent mvEvent;
    private VirtualView virtualView;
    private TimerThread timer;
    private int counter = 0;
    private int playerIndex;
    private static final Logger LOGGER = Logger.getLogger(EventsController.class.getName());

    public EventsController(VirtualView virtualView){
        this.virtualView = virtualView;
        lobbyController = new LobbyController(this, virtualView);
        toolCardController = new ToolCardController(this, lobbyController);
        timer = new TimerThread(this, 0);
        turnController = new TurnController(this);
    }

    public void setReconnection(boolean reconnection) {

        this.reconnection = reconnection;
    }
    public Game getGame() {
        return game;
    }
    public MVEvent getMvEvent() {
        return mvEvent;
    }
    public VirtualView getVirtualView() {
        return virtualView;
    }
    public ToolCardController getToolCardController() {
        return toolCardController;
    }
    public LobbyController getLobbyController() {
        return lobbyController;
    }
    public TimerThread getTimer() {
        return timer;
    }


    public void setMvEvent(MVEvent mvEvent) {
        this.mvEvent = mvEvent;
    }

    public void setPlayerIndex(int playerIndex) {
        this.playerIndex = playerIndex;
    }
    public void setTimer(TimerThread timer) {
        this.timer = timer;
    }



    @Override
    public void registerObserver(MyObserver observer) {
        observerCollection.add(observer);
    }

    @Override
    public void unregisterObserver(MyObserver observer) {
        observerCollection.remove(observer);
    }

    @Override
    public void notifyObservers() throws IOException, InvalidConnectionException, InvalidViewException, ParseException {
        for (MyObserver o: observerCollection) {
            try {
                o.update(this, mvEvent);
            } catch (InvalidDieException e) {
                LOGGER.log(Level.SEVERE, e.toString(), e);
            }
        }
    }

    @Override
    public void update(MyObservable o, VCEvent arg) throws IOException, InvalidConnectionException, InvalidViewException, ParseException, InvalidDieException {
        if(game != null) {
            if (!gameIsOver())
                arg.accept(this);
        }
        else
            arg.accept(this);
    }

    @Override
    public void update(MyObservable o, MVEvent arg){
        /*Intentionally left empty*/
    }

    /**
     * Method that handles a login event
     * @param event login event
     * @throws InvalidConnectionException exception
     * @throws IOException exception
     * @throws InvalidViewException exception
     * @throws ParseException exception
     */
    @Override
    public void handleVCEvent(LoginEvent event) throws InvalidConnectionException, IOException, InvalidViewException, ParseException {
        if (!reconnection)
            lobbyController.handleLogin(event);
        else
            lobbyController.handleReconnection(event);
    }

    /**
     * Method that handles a place die event
     * @param event place die event
     * @throws InvalidConnectionException exception
     * @throws ParseException exception
     * @throws InvalidViewException exception
     * @throws IOException exception
     */
    @Override
    public void handleVCEvent(PlaceDieEvent event) throws InvalidConnectionException, ParseException, InvalidViewException, IOException, InvalidDieException {
        if (!game.findPlayer(event.getUsername()).isPlacedDie()) {
            game.findPlayer(event.getUsername()).setPlacedDie(true);

            if (event.getUsername().equals(game.getPlayers().get(playerIndex).getUsername())) {
                if (event.getPos() >= 0 && event.getPos() <= game.getRolledDice().size() - 1 && event.getCoordX() >= 0 && event.getCoordX() <= 4 &&
                        event.getCoordY() >= 0 && event.getCoordY() <= 3 && game.findPlayer(event.getUsername()).getWindowCard().checkLegalPlacement(game.getRolledDice().get(event.getPos()), event.getCoordY(), event.getCoordX(), true, true, true)) {
                    String user = event.getUsername();
                    game.findPlayer(event.getUsername()).getWindowCard().placeDie(game.getRolledDice().get(event.getPos()), event.getCoordY(), event.getCoordX(), true, true, true);
                    game.updateWindowCardList();
                    game.getRolledDice().remove(event.getPos());
                    int index = game.getPlayers().indexOf(game.findPlayer(event.getUsername()));
                    p = game.getPlayers().get(index);
                    p.setAd(true);
                    if (game.getPlayers().get(playerIndex).isRunningPliers()) {
                        mvEvent = new UpdateGameEvent(game.getWindowCardList(), lobbyController.getUsername(), game.getRolledDice(), game.getRoundCells());
                        notifyObservers();
                        virtualView.createSkipTurnEvent(event.getUsername());
                    } else if (!game.getToolCards().get(toolCardController.getPos()).getTitle().equals(RUNNING_PLIERS)) {
                        mvEvent = new UpdateGameEvent(game.getWindowCardList(), lobbyController.getUsername(), game.getRolledDice(), game.getRoundCells());
                        notifyObservers();
                        mvEvent = new MiniMenuEvent(event.getUsername());
                        notifyObservers();
                    } else if (game.getToolCards().get(toolCardController.getPos()).getTitle().equals(RUNNING_PLIERS)) {
                        game.getPlayers().get(playerIndex).setRunningPliers(true);
                        mvEvent = new UpdateGameEvent(game.getWindowCardList(), lobbyController.getUsername(), game.getRolledDice(), game.getRoundCells());
                        notifyObservers();
                        mvEvent = new DoublePlaceEvent(user);
                        notifyObservers();
                    }
                } else {
                    mvEvent = new WrongPlaceEvent(event.getUsername());
                    notifyObservers();
                }
            } else {
                mvEvent = new IsNotYourTurn(event.getUsername());
                notifyObservers();
            }
        }
        else{
            virtualView.createSkipTurnEvent(event.getUsername());
        }
    }

    @Override
    public void handleVCEvent(RollDiceEvent event) throws InvalidConnectionException, InvalidViewException, ParseException, IOException {
        toolCardController.handleVCEvent(event);
    }

    /**
     * Method that handles a skip turn event
     *
     * @param event skip turn event
     * @throws InvalidConnectionException exception
     * @throws ParseException             exception
     * @throws InvalidViewException       exception
     * @throws IOException                exception
     */
    @Override
    public void handleVCEvent(SkipTurnEvent event) throws InvalidConnectionException, ParseException, InvalidViewException, IOException, InvalidDieException {
        if (event.getUsername().equals(game.getPlayers().get(playerIndex).getUsername())) {
            if (timer.getCont() == TURN_TIMER-1) {
                timerExpired();
            } else {
                timer.interrupt();
                int index = game.getPlayers().indexOf(game.findPlayer(event.getUsername()));
                p = game.getPlayers().get(index);
                p.setAd(false);
                turnController.handleSkipTurn(game.getPlayers().indexOf(game.findPlayer(event.getUsername())));
            }
        } else {
            mvEvent = new IsNotYourTurn(event.getUsername());
            notifyObservers();
        }
    }

    public void timerExpired() throws InvalidConnectionException, ParseException, InvalidViewException, IOException {
        timer.interrupt();
        mvEvent = new StopTurnEvent(game.getPlayers().get(playerIndex).getUsername(), STOP_MESSAGE);
        notifyObservers();
        try {
            virtualView.createSkipTurnEvent(game.getPlayers().get(playerIndex).getUsername());
        } catch (InvalidDieException | InvalidViewException | InvalidConnectionException | IOException | ParseException e) {
            LOGGER.log(Level.SEVERE,e.toString(), e);
        }
    }


    @Override
    public void handleVCEvent(SelectDieEvent event) throws InvalidConnectionException, InvalidViewException, ParseException, IOException, InvalidDieException {
        toolCardController.handleVCEvent(event);
    }

    @Override
    public void handleVCEvent(UseToolEvent event) throws InvalidConnectionException, InvalidViewException, ParseException, IOException, InvalidDieException {

        toolCardController.handleVCEvent(event);
    }

    /**
     * Method that handles choice of a Window Card
     * @param event choose card event
     */

    @Override
    public void handleVCEvent(UpdateDieEvent event) throws InvalidDieException {
        game.getRolledDice().get(event.getPos()).setValue(event.getVal());
        mvEvent = new ModifiedPlaceEvent(event.getUsername(), event.getPos());
    }

    @Override
    public void handleVCEvent(SwappingDieEvent swappingDieEvent) {
        toolCardController.handleVCEvent(swappingDieEvent);
    }

    @Override
    public void handleVCEvent(ChooseCardEvent event) throws InvalidConnectionException, IOException, InvalidViewException, ParseException {
        counter++;
        lobbyController.handleWindowCard(event);
        if(counter == game.getPlayerNumber()) {
            game.dealPublicCards();
            game.dealToolCards();
            lobbyController.newGame();
        }
    }

    @Override
    public void handleVCEvent(PlaceModifiedDie placeModifiedDie) {
        /*Intentionally left empty in this class*/
    }

    @Override
    public void handleVCEvent(MovingDieEvent movingDieEvent) throws InvalidConnectionException, InvalidViewException, ParseException, IOException {
        toolCardController.handleVCEvent(movingDieEvent);
    }

    @Override
    public void handleVCEvent(IncrementDecrementDieEvent incrementDecrementDieEvent) throws InvalidDieException, InvalidConnectionException, InvalidViewException, ParseException, IOException {
        toolCardController.handleVCEvent(incrementDecrementDieEvent);
    }

    @Override
    public void handleVCEvent(RemovedUser removedUserEvent) throws InvalidConnectionException, InvalidViewException, ParseException, IOException {
        String removed = removedUserEvent.getUsername();

        lobbyController.getLobby().removeOnlinePlayer(removed);

        if (game != null) {
            game.findPlayer(removed).setDisconnected(true);
            if (removedUserEvent.getRemaining() == 1)
                turnController.endGame();
        }
    }

    @Override
    public void handleVCEvent(PlaceDieWithRestriction placeDieWithRestriction) throws InvalidDieException, InvalidConnectionException, InvalidViewException, ParseException, IOException {
        toolCardController.handleVCEvent(placeDieWithRestriction);
    }

    @Override
    public void handleVCEvent(ColorAndNumberEvent event) throws InvalidConnectionException, ParseException, InvalidViewException, IOException {
        if (game.getRoundCells().isEmpty()){
            mvEvent = new RetryToolEvent(event.getUsername());
            notifyObservers();
        }
        else  {
            Die die = game.getRoundCells().get(event.getPositionRoundTrack()).getDiceList().get(event.getPosition());
            Color color = die.getColor();
            toolCardController.setColor(color);
            toolCardController.handleTapWheel(event);
        }

    }


    public void setGame(Game game) {

        this.game = game;
    }

    public int getPlayerIndex() {
        return playerIndex;
    }

    private boolean gameIsOver(){
        return game.isFinished();
    }


    public TurnController getTurnController() {
        return turnController;
    }
}
