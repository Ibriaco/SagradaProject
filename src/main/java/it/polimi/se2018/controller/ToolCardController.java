package it.polimi.se2018.controller;

import it.polimi.se2018.controller.effects.*;
import it.polimi.se2018.model.*;
import it.polimi.se2018.model.event.*;
import it.polimi.se2018.model.event.RollingDiceEvent;
import it.polimi.se2018.org.json.simple.parser.ParseException;
import it.polimi.se2018.view.viewevents.*;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import static it.polimi.se2018.ServerConfig.*;

public class ToolCardController{

    private Game game;
    private String user;
    private int pos;
    private int index;
    private int diePositionDraftPool;
    private Die d;
    private MVEvent mvEvent;
    private int newX;
    private int newY;
    boolean ok = BOOL_FALSE;
    private WindowCard w;
    private EventsController eventsController;
    private LobbyController lc;
    private static final Logger LOGGER = Logger.getGlobal();


    public ToolCardController(EventsController controller, LobbyController lobbyController) {
        this.eventsController = controller;
        this.lc = lobbyController;
    }

    public void handleVCEvent(UseToolEvent event) throws InvalidConnectionException, ParseException, InvalidViewException, IOException {
        user = event.getUsername();
        pos = event.getToolCardNumber();
        index = game.getPlayers().indexOf(game.findPlayer(user));
        Player p = game.getPlayers().get(index);
        tokenCheck();
        if (ok&&game.getToolCards().get(pos).getType().equals(AFTER_DRAFTING)&&!p.isAd())
            mvEvent = new ChangeDieEvent(user);
        else if(ok&&game.getToolCards().get(pos).getType().equals(AFTER_DRAFTING)&&p.isAd()){
            mvEvent = new RetryToolEvent(user);
        }
        else if (ok&&game.getToolCards().get(pos).getType().equals(ON_WINDOW))
            mvEvent = new MoveDieEvent(user);
        else if(ok&&game.getToolCards().get(pos).getType().equals(ON_DRAFT)&&game.getTurn()>game.getPlayerNumber()){
           mvEvent = new RollingDiceEvent(user);
        }
        else
            mvEvent = new InvalidToolEvent(user);

        eventsController.setMvEvent(mvEvent);
        eventsController.notifyObservers();
    }

    private void tokenCheck(){
        boolean used = game.getToolCards().get(pos).isUsed();
        ok = BOOL_FALSE;
        if(game.getPlayers().get(index).getTokens()>ZERO_VALUE && !used){
            game.getToolCards().get(pos).setUsed(BOOL_TRUE);
            game.getPlayers().get(index).setTokens(game.getPlayers().get(index).getTokens()-ONE_VALUE);
            ok = BOOL_TRUE;
        }
        else if (game.getPlayers().get(index).getTokens()>ONE_VALUE && used){
            game.getPlayers().get(index).setTokens(game.getPlayers().get(index).getTokens()-TWO_VALUE);
            ok = BOOL_TRUE;
        }
        else if(game.getPlayers().get(index).getTokens()<TWO_VALUE && used || !used&&game.getPlayers().get(index).getTokens()<1)
            mvEvent = new InvalidToolEvent(user);
    }
    public void checkApplyEffect(ReverseDieEffect reverseDieEffect) throws InvalidDieException {
        //controllo token disponibili
        reverseDieEffect.applyEffect(d);
    }

    public void checkApplyEffect(RollDieEffect rollDieEffect){
        try {
            rollDieEffect.applyEffect(d);
        } catch (InvalidDieException e) {
            LOGGER.log(Level.SEVERE, e.toString(), e);
        }
    }

    public void checkApplyEffect(IncDecEffect incDecEffect) throws InvalidConnectionException, ParseException, InvalidViewException, IOException {
        System.out.println("invio evento incremento o decremento da checkApplyEffect");
        eventsController.setMvEvent(new IncDecEvent(user));
        eventsController.notifyObservers();
    }

    public void checkApplyEffect(MoveDieEffect moveDieEffect) {

        moveDieEffect.applyEffect(w, d, newX, newY);
    }

    public void handleVCEvent(SelectDieEvent event) throws InvalidConnectionException, ParseException, InvalidViewException, IOException {

        d = game.getRolledDice().get(event.getPosition());
        diePositionDraftPool = event.getPosition();

        try {
            System.out.println("dioooooo");
            game.getToolCards().get(pos).getEffectList().get(ZERO_VALUE).accept(this);
        } catch (InvalidDieException e) {
            LOGGER.log(Level.SEVERE, e.toString(), e);
        }
        if(!game.getToolCards().get(pos).getTitle().equals("Grozing Pliers")) {
            eventsController.setMvEvent(new ChangedDieEvent(user, d));
            System.out.println("mando evento sbagliato");
            eventsController.notifyObservers();

            eventsController.setMvEvent(new ModifiedPlaceEvent(user, event.getPosition()));
            eventsController.notifyObservers();
        }
    }

    public void handleVCEvent(MovingDieEvent event) throws InvalidConnectionException, ParseException, InvalidViewException, IOException {
        int index;
        index = game.getPlayers().indexOf(game.findPlayer(event.getUsername()));
        d = game.getPlayers().get(index).getWindowCard().getGridCell(event.getOldY(), event.getOldX()).getPlacedDie();
        game.getPlayers().get(index).getWindowCard().removeDie(event.getOldY(), event.getOldX());
        game.updateWindowCardList();
        w = game.getPlayers().get(index).getWindowCard();
        newX = event.getNewX();
        newY = event.getNewY();
        System.out.println("valore e colore cella su cui piazzo il dado: " + w.getGridCell(newY,newX).getShade() +  "  " + w.getGridCell(newY,newX).getColor());
        try {
            game.getToolCards().get(pos).getEffectList().get(0).accept(this);
            game.updateWindowCardList();
        } catch (InvalidDieException e) {
            LOGGER.log(Level.SEVERE, e.toString(), e);
        }
        System.out.println("MADNO EVENTO DI UPDATE");
        eventsController.setMvEvent(new UpdateGameEvent(game.getWindowCardList(), lc.getUsername(), game.getRolledDice(), game.getRoundCells()));
        eventsController.notifyObservers();
        System.out.println("MANDO EVENTO PERFORMOACTIONEVENT");
        eventsController.setMvEvent(new PerformActionEvent(event.getUsername()));
        eventsController.notifyObservers();
    }


    public void setGame(Game game) {

        this.game = game;
    }


    public void handleVCEvent(IncrementDecrementDieEvent event) throws InvalidConnectionException, ParseException, InvalidViewException, IOException, InvalidDieException {
        int choose = event.getChoice();
        System.out.println("handlo evento increment,decrement in toolcardcontroller");
        if (choose == ONE_VALUE && (d.getValue() != SIX_VALUE)){
            System.out.println("valore dado prima incremento: " + d.getValue());
            d.setValue(d.getValue()+ONE_VALUE);
            System.out.println("valore dado dopo incremento: " + d.getValue());
            //invio all'utente dado modificato
            eventsController.setMvEvent(new ChangedDieEvent(user, d));
            eventsController.notifyObservers();
            //invio evento per posizionare dado cambiato
            eventsController.setMvEvent(new ModifiedPlaceEvent(user, diePositionDraftPool));
            eventsController.notifyObservers();

        }
        else if ((choose == ONE_VALUE && d.getValue() == SIX_VALUE) || (choose == TWO_VALUE && d.getValue() == ONE_VALUE) ){
            eventsController.setMvEvent(new WrongPlaceEvent(user));
            eventsController.notifyObservers();
        }
        else if (choose == TWO_VALUE && d.getValue() != ONE_VALUE){
            d.setValue(d.getValue()-ONE_VALUE);
            //invio all'utente dado modificato
            eventsController.setMvEvent(new ChangedDieEvent(user, d));
            eventsController.notifyObservers();
            //invio evento per posizionare dado cambiato
            eventsController.setMvEvent(new ModifiedPlaceEvent(user, diePositionDraftPool));
            eventsController.notifyObservers();
        }

    }

    public void checkApplyEffect(RollDiceEffect rollDiceEffect) throws InvalidDieException, InvalidConnectionException, ParseException, InvalidViewException, IOException {
        rollDiceEffect.applyEffect(game.getRolledDice());
    }

    public void handleVCEvent(RollDiceEvent event) throws InvalidConnectionException, ParseException, InvalidViewException, IOException {
        try {
            game.getToolCards().get(pos).getEffectList().get(0).accept(this);
        } catch (IOException | InvalidConnectionException | ParseException | InvalidDieException | InvalidViewException e) {
            e.printStackTrace();
        }

        eventsController.setMvEvent(new UpdateGameEvent(game.getWindowCardList(), lc.getUsername(), game.getRolledDice(), game.getRoundCells()));
        eventsController.notifyObservers();
        eventsController.setMvEvent(new PerformActionEvent(event.getUsername()));
        eventsController.notifyObservers();
    }
}
