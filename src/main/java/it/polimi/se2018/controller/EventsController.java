package it.polimi.se2018.controller;

import it.polimi.se2018.model.*;
import it.polimi.se2018.model.event.*;
import it.polimi.se2018.MyObservable;
import it.polimi.se2018.MyObserver;
import it.polimi.se2018.network.server.VirtualView;
import it.polimi.se2018.view.viewevents.*;
import it.polimi.se2018.view.viewevents.RollDiceEvent;
import org.json.simple.parser.ParseException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Controller class that handles events
 * @author Ibrahim El Shemy
 * @author Marco Gasperini
 * @author Gregorio Galletti
 */
public class EventsController implements ControllerInterface, MyObserver, MyObservable {

    private Game game;
    private boolean reverse = false;
    private boolean reconnection = false;
    private boolean stopThread = false;
    private LobbyController lobbyController;

    public ToolCardController getToolCardController() {
        return toolCardController;
    }

    private ToolCardController toolCardController;
    private ArrayList<MyObserver> observerCollection = new ArrayList<>();
    private MVEvent mvEvent;
    private VirtualView virtualView;
    private int counter = 0;
    private int playerIndex;
    private static final Logger LOGGER = Logger.getLogger(EventsController.class.getName());

    public EventsController(VirtualView virtualView){
        this.virtualView = virtualView;
        lobbyController = new LobbyController(this, virtualView);
        toolCardController = new ToolCardController(this, lobbyController);
    }

    /**
     *
     * @param username user of the player
     * @return return true if it's the player turn, false otherwise
     */
    private boolean checkPlayer(String username) {

        return (game.getPlayers().indexOf(game.findPlayer(username)) == game.getTurn());

    }


    public void setReconnection(boolean reconnection) {

        this.reconnection = reconnection;
    }


    public void setMvEvent(MVEvent mvEvent) {

        this.mvEvent = mvEvent;
    }


    //METODI OBSERVER E OBSERVABLE

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
                e.printStackTrace();
            }
        }
    }

    @Override
    public void update(MyObservable o, VCEvent arg) throws IOException, InvalidConnectionException, InvalidViewException, ParseException, InvalidDieException {

        arg.accept(this);
    }

    @Override
    public void update(MyObservable o, MVEvent arg){

    }

    //METODI PER GENERARE EVENTI MV

    public void createGameUpdateEvent () throws InvalidConnectionException, IOException, InvalidViewException, ParseException {
        mvEvent = new GameUpdateEvent("ALL");
        notifyObservers();
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
    public void handleVCEvent(PlaceDieEvent event) throws InvalidConnectionException, ParseException, InvalidViewException, IOException {
        if (event.getUsername().equals(game.getPlayers().get(playerIndex).getUsername())) {
            game.findPlayer(event.getUsername()).getWindowCard().placeDie(game.getRolledDice().get(event.getPos()), event.getCoordY(), event.getCoordX(), true, true);
            game.updateWindowCardList();
            //if(game.findPlayer(event.getUsername()).getWindowCard().getGridCell(event.getCoordY(), event.getCoordX()).isPlaced())
            game.getRolledDice().remove(event.getPos());
            //devo aggiungere round track al construttore di updateGameEvent-->game.getRoundCells()
            mvEvent = new UpdateGameEvent(game.getWindowCardList(), lobbyController.getUsername(), game.getRolledDice());
            notifyObservers();
        }
    }

    @Override
    public void handleVCEvent(RollDiceEvent event) {
        //still needs to be implemented
    }

    /**
     * Method that handles a skip turn event
     * @param event skip turn event
     * @throws InvalidConnectionException exception
     * @throws ParseException exception
     * @throws InvalidViewException exception
     * @throws IOException exception
     */
    @Override
    public void handleVCEvent(SkipTurnEvent event) throws InvalidConnectionException, ParseException, InvalidViewException, IOException {
        if (event.getUsername().equals(game.getPlayers().get(playerIndex).getUsername())){
            handleSkipTurn(game.getPlayers().indexOf(game.findPlayer(event.getUsername())));
            stopThread = true;
        }
    }

    /**
     * Method that handles concretely the skin turn
     * @param playerIndex index of the current player
     * @throws InvalidConnectionException exception
     * @throws ParseException exception
     * @throws InvalidViewException exception
     * @throws IOException exception
     */
    private void handleSkipTurn (int playerIndex) throws InvalidConnectionException, ParseException, InvalidViewException, IOException {
        //BISOGNA GESIRE MEGLIO A CHI VIENE ASSEGNATO IL FIRST PLAYER
        // IF DEL FINE ULTIMO TURNO. INIZIO NUOVO ROUND
        if (game.getTurn() == game.getPlayerNumber()*2){
            System.out.println("Sono nel caso di inizio nuovo round");
            System.out.println("Valore di player index: " + playerIndex );
            checkRound(playerIndex);
        }

        //IF DEL FINE PRIMO GIRO(SONO A META' ROUND). SI INVERTE IL GIRO
        else if (game.getTurn()-game.getPlayerNumber()==0 && !virtualView.getRemovedClients().contains(game.getPlayers().get(playerIndex).getUsername())){
            System.out.println("Sono nel caso di fine primo giro con client corrente online");
            System.out.println("Valore di player index: " + playerIndex );
            mvEvent = new IsTurnEvent(game.getPlayers().get(playerIndex).getUsername(), true);
            reverse = true;
            game.nextTurn();
        }
        else if(game.getTurn()-game.getPlayerNumber()==0 && virtualView.getRemovedClients().contains(game.getPlayers().get(playerIndex).getUsername())){
            System.out.println("Sono nel caso di fine primo giro con client corrente offline");
            System.out.println("Valore di player index: " + playerIndex );
            reverse = true;
            game.nextTurn();
            checkSkip(playerIndex);
        }
        //GESTIONE CLASSICA DELLO SKIP TURN SE NON CI SONO CASI LIMITE DA GESTIRE
        else {
            System.out.println("Sono nel caso di skip");
            System.out.println("Valore di player index: " + playerIndex );
            checkSkip(playerIndex);
        }
        playerIndex = game.getPlayers().indexOf(game.findPlayer(mvEvent.getUsername()));
        //launchThread(playerIndex);
        notifyObservers();
    }

    /**
     * Method that launchs timer
     * @param playerIndex index of the current player
     */
    public void launchThread(int playerIndex) {new Thread(() -> {
        int cont=0;
        stopThread = false;
        System.out.println("prima del while del thread");
        while (cont < 10 && !stopThread) {
            try {
                Thread.sleep(1000);
                System.out.println(cont);
                cont++;

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if (playerIndex == getPlayerIndex()&& !stopThread)    {
            try {
                System.out.println("invio stopturnevent");
                mvEvent = new StopTurnEvent(game.getPlayers().get(playerIndex).getUsername());
                notifyObservers();
                System.out.println("invio skipturn a virtualview");
                virtualView.createSkipTurnEvent(game.getPlayers().get(playerIndex).getUsername());
            } catch (InvalidConnectionException | ParseException | InvalidViewException | IOException e) {
                e.printStackTrace();
            } catch (InvalidDieException e) {
                e.printStackTrace();
            }
            stopThread = false;
        }

    }).start();
    }

    /**
     * Method that checks rounds and sets the first player to play in the current round
     * @param playerIndex index of the current player
     * @throws InvalidConnectionException exception
     * @throws InvalidViewException exception
     * @throws ParseException exception
     * @throws IOException exception
     */
    private void checkRound(int playerIndex) throws InvalidConnectionException, InvalidViewException, ParseException, IOException {
        System.out.println("Valore di index in check round: " + playerIndex);
        if(playerIndex==game.getPlayerNumber()-1) {
            game.setFirstPlayer(game.getPlayers().get(0));
        }
        else{
            playerIndex++;
            game.setFirstPlayer(game.getPlayers().get(playerIndex));
        }

        //roudtrack e gestione distribuzione dadi
        reverse = false;
        game.nextTurn();
        if(virtualView.getRemovedClients().contains(game.getPlayers().get(playerIndex).getUsername()))
            checkSkip(playerIndex);
        else
            mvEvent = new IsTurnEvent(game.getPlayers().get(playerIndex).getUsername(), true);
    }


    /**
     * Method that checks if turn can skipped to the incoming/previous player
     * @param playerIndex index of the current player
     * @throws InvalidConnectionException exception
     * @throws InvalidViewException exception
     * @throws ParseException exception
     * @throws IOException exception
     */
    private void checkSkip(int playerIndex) throws InvalidConnectionException, InvalidViewException, ParseException, IOException {
        if (!reverse&&playerIndex!=game.getPlayerNumber()-1&&!virtualView.getRemovedClients().contains(game.getPlayers().get(playerIndex+1).getUsername()))
            mvEvent = new IsTurnEvent(game.getPlayers().get(playerIndex + 1).getUsername(), true);
        else if(!reverse&&playerIndex!=game.getPlayerNumber()-1&&virtualView.getRemovedClients().contains(game.getPlayers().get(playerIndex+1).getUsername())){
            game.nextTurn();
            handleSkipTurn(playerIndex+1);
        }
        else if (!reverse&&playerIndex==game.getPlayerNumber()-1&&!virtualView.getRemovedClients().contains(game.getPlayers().get(0).getUsername()))
            mvEvent = new IsTurnEvent(game.getPlayers().get(0).getUsername(), true);
        else if(!reverse&&playerIndex==game.getPlayerNumber()-1&&virtualView.getRemovedClients().contains(game.getPlayers().get(0).getUsername())) {
            game.nextTurn();
            handleSkipTurn(0);
        }

        else if(reverse&&playerIndex!=0&&!virtualView.getRemovedClients().contains(game.getPlayers().get(playerIndex-1).getUsername()))
            mvEvent = new IsTurnEvent(game.getPlayers().get(playerIndex - 1).getUsername(), true);
        else if(reverse&&playerIndex!=0&&virtualView.getRemovedClients().contains(game.getPlayers().get(playerIndex-1).getUsername())) {
            game.nextTurn();
            handleSkipTurn(playerIndex - 1);
        }
        else if(reverse&&playerIndex==0&&!virtualView.getRemovedClients().contains(game.getPlayers().get(game.getPlayerNumber()-1).getUsername()))
            mvEvent = new IsTurnEvent(game.getPlayers().get(game.getPlayerNumber()-1).getUsername(), true);
        else if (reverse&&playerIndex==0&&virtualView.getRemovedClients().contains(game.getPlayers().get(game.getPlayerNumber()-1).getUsername())) {
            game.nextTurn();
            handleSkipTurn(game.getPlayerNumber() - 1);
        }
        game.nextTurn();
    }

    @Override
    public void handleVCEvent(SelectDieEvent event) throws InvalidConnectionException, InvalidViewException, ParseException, IOException, InvalidDieException {
        toolCardController.handleVCEvent(event);
    }

    @Override
    public void handleVCEvent(UseToolEvent event) throws InvalidConnectionException, InvalidViewException, ParseException, IOException {

        toolCardController.handleVCEvent(event);
    }

    /**
     * Method that handles choice of a Window Card
     * @param event choose card event
     * @throws InvalidConnectionException exception
     * @throws IOException exception
     * @throws InvalidViewException exception
     * @throws ParseException exception
     */
    @Override
    public void handleVCEvent(ChooseCardEvent event) throws InvalidConnectionException, IOException, InvalidViewException, ParseException {
        counter++;
        LOGGER.log(Level.INFO, "Risposte ricevute: " + counter);
        lobbyController.handleWindowCard(event);
        if(counter == game.getPlayerNumber()) {
            game.dealPublicCards();
            game.dealToolCards();
            lobbyController.newGame();
        }
        LOGGER.log(Level.INFO, "Sono tornato nel lobbycontroller(windowcard)");
    }

    @Override
    public void handleVCEvent(PlaceModifiedDie placeModifiedDie) {

        toolCardController.handleVCEvent(placeModifiedDie);
    }

    public void setGame(Game game) {

        this.game = game;
    }

    public int getPlayerIndex() {
        return playerIndex;
    }
}
