package it.polimi.se2018.controller;

import it.polimi.se2018.model.*;
import it.polimi.se2018.model.event.GameUpdateEvent;
import it.polimi.se2018.model.event.MVEvent;
import it.polimi.se2018.MyObservable;
import it.polimi.se2018.MyObserver;
import it.polimi.se2018.model.event.UpdateGameEvent;
import it.polimi.se2018.network.server.VirtualView;
import it.polimi.se2018.view.viewevents.*;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EventsController implements ControllerInterface, MyObserver, MyObservable {

    private Game game;

    private LobbyController lobbyController;
    private ArrayList<MyObserver> observerCollection = new ArrayList<>();
    private MVEvent mvEvent;
    private int counter = 0;
    private static final Logger LOGGER = Logger.getLogger(EventsController.class.getName());

    public EventsController(VirtualView virtualView){
        lobbyController = new LobbyController(this, virtualView);
    }

    private boolean checkPlayer(String username) {

        return (game.getPlayers().indexOf(game.findPlayer(username)) == game.getTurn());

    }

    /*private boolean checkValidPlacementMove(PlacedDieEvent e) {
        control1 = this.checkPlayer(e.getUsername());
        Die die = null;
        for (Die d : game.getRolledDice()) {
            if (d.getValue() == e.getValue() && d.getColor() == e.getColor()) {
                die = d;
                control2 = true;
                break;
            }
        }
        if (!game.findPlayer(e.getUsername()).getWindowCard().checkLegalPlacement(die, e.getCoordX(), e.getCoordY(), true, true))
            return false;
        else
            return (control1 && control2);
    }*/

    private boolean checkValidSkip(SkipTurnEvent e) {

        return this.checkPlayer(e.getUsername());
    }

    private boolean checkLoginEvent(LoginEvent e) {
        boolean control1 = this.checkPlayer(e.getUsername());
        boolean control2;
        if (game.getPlayerNumber() < 4) {
            control2 = true;
        } else
            return false;
        for (Player p : game.getPlayers()) {
            if (p.getUsername().equals(e.getUsername())) {
                return false;
            }
        }
        return (control1 && control2);
    }

    public void checkSelectDieEvent (SelectDieEvent e){

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
            o.update(this, mvEvent);
        }
    }

    @Override
    public void update(MyObservable o, VCEvent arg) throws IOException, InvalidConnectionException, InvalidViewException, ParseException {

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

    //HANDLE VCEVENT
    @Override
    public void handleVCEvent(LoginEvent event) throws InvalidConnectionException, IOException, InvalidViewException, ParseException {
        lobbyController.handleLogin(event);
    }

    @Override
    public void handleVCEvent(PlaceDieEvent event) throws InvalidConnectionException, ParseException, InvalidViewException, IOException {
        game.findPlayer(event.getUsername()).getWindowCard().placeDie(game.getRolledDice().get(event.getPos()),event.getCoordY(),event.getCoordX(),false, false);
        game.updateWindowCardList();
        System.out.println(game.findPlayer(event.getUsername()).getWindowCard().getGridCell(0,0).isPlaced());
        game.getRolledDice().remove(event.getPos());
        //devo aggiungere round track al construttore di updateGameEvent-->game.getRoundCells()
        mvEvent = new UpdateGameEvent(game.getWindowCardList(),lobbyController.getUsername(),game.getRolledDice());
        notifyObservers();
    }

    @Override
    public void handleVCEvent(RollDiceEvent event) {
        //still needs to be implemented
    }

    @Override
    public void handleVCEvent(SkipTurnEvent event) {
        //still needs to be implemented
    }

    @Override
    public void handleVCEvent(SelectDieEvent event) {
        //still needs to be implemented
    }

    @Override
    public void handleVCEvent(UseToolEvent event) {
        //still needs to be implemented
    }

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

    public void setGame(Game game) {
        this.game = game;
    }
}
