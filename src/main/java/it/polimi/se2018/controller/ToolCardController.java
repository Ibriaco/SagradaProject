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

    public int getPos() {
        return pos;
    }

    private int pos;
    private int index;
    private int diePositionDraftPool;
    private Die d;
    private Color color;
    private MVEvent mvEvent;
    private int newX;
    private int newY;
    private boolean ok = true;
    private WindowCard w;
    private EventsController eventsController;
    private LobbyController lc;
    private static final Logger LOGGER = Logger.getGlobal();


    public ToolCardController(EventsController controller, LobbyController lobbyController) {
        this.eventsController = controller;
        this.lc = lobbyController;
    }

    public Die getD(){

        return d;
    }

    public void setD(Die d){

        this.d = d;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void handleVCEvent(UseToolEvent event) throws InvalidConnectionException, ParseException, InvalidViewException, IOException, InvalidDieException {
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
        else if (ok&&game.getToolCards().get(pos).getType().equals(ON_WINDOW)){
            mvEvent = new MoveDieEvent(user);
        }
        else if(ok&&game.getToolCards().get(pos).getType().equals(ON_DRAFT)&&game.getTurn()>game.getPlayerNumber() && !game.getToolCards().get(pos).getTitle().equals("Tap Wheel")){
           mvEvent = new RollingDiceEvent(user);
        }
        else if (ok && game.getToolCards().get(pos).getTitle().equals("Tap Wheel")){
            mvEvent = new RequestColorAndNumberEvent(user);
        }
        else if (ok&&game.getToolCards().get(pos).getType().equals(SPECIAL)&&!p.isAd()&&game.getTurn()<=game.getPlayerNumber())
            mvEvent = new DoublePlaceEvent(user);

        else if(ok&&game.getToolCards().get(pos).getType().equals(SPECIAL)&&p.isAd()){
            mvEvent = new RetryToolEvent(user);
        }
        else {
            mvEvent = new InvalidToolEvent(user);
        }

        eventsController.setMvEvent(mvEvent);
        eventsController.notifyObservers();
        //game.getToolCards().get(pos).getType().equals(ON_DRAFT)&&
    }

    private void tokenCheck(){
        boolean used = game.getToolCards().get(pos).isUsed();
        ok = false;
        if(game.getPlayers().get(index).getTokens()>ZERO_VALUE && !used){
            game.getToolCards().get(pos).setUsed(true);
            game.getPlayers().get(index).setTokens(game.getPlayers().get(index).getTokens()-ONE_VALUE);
            ok = true;
        }
        else if (game.getPlayers().get(index).getTokens()>ONE_VALUE && used){
            game.getPlayers().get(index).setTokens(game.getPlayers().get(index).getTokens()-TWO_VALUE);
            ok = true;
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
        eventsController.setMvEvent(new IncDecEvent(user));
        eventsController.notifyObservers();
    }

    public void checkApplyEffect(MoveDieEffect moveDieEffect) throws InvalidConnectionException, ParseException, InvalidViewException, IOException {
        if (moveDieEffect.getAmount() == 1) {
            moveDieEffect.applyEffect(w, d, newX, newY);
            eventsController.setMvEvent(new UpdateGameEvent(game.getWindowCardList(), lc.getUsername(), game.getRolledDice(), game.getRoundCells()));
            eventsController.notifyObservers();
        } else if (moveDieEffect.getAmount() == 2) {
            moveDieEffect.applyEffect(w, d, newX, newY);
            eventsController.setMvEvent(new UpdateGameEvent(game.getWindowCardList(), lc.getUsername(), game.getRolledDice(), game.getRoundCells()));
            eventsController.notifyObservers();
            mvEvent = new MoveDieEvent(user);
            eventsController.setMvEvent(mvEvent);
            eventsController.notifyObservers();
        }

        eventsController.setMvEvent(new PerformActionEvent(user));
        eventsController.notifyObservers();

    }

    public void handleVCEvent(SelectDieEvent event) throws InvalidConnectionException, ParseException, InvalidViewException, IOException {

        d = game.getRolledDice().get(event.getPosition());
        diePositionDraftPool = event.getPosition();
        w = game.findPlayer(event.getUsername()).getWindowCard();

        try {
            game.getToolCards().get(pos).getEffectList().get(ZERO_VALUE).accept(this);
        } catch (InvalidDieException e) {
            LOGGER.log(Level.SEVERE, e.toString(), e);
        }


        if(!(game.getToolCards().get(pos).getTitle().equals("Grozing Pliers") || game.getToolCards().get(pos).getTitle().equals("Cork-backed Straightedge"))) {
            eventsController.setMvEvent(new ChangedDieEvent(user, d));
            eventsController.notifyObservers();
            eventsController.setMvEvent(new ModifiedPlaceEvent(user, event.getPosition()));
            eventsController.notifyObservers();
        }
    }

    public void handleVCEvent(MovingDieEvent event) throws InvalidConnectionException, ParseException, InvalidViewException, IOException {
        int playerIndex;
        playerIndex = game.getPlayers().indexOf(game.findPlayer(event.getUsername()));
        d = game.getPlayers().get(playerIndex).getWindowCard().getGridCell(event.getOldY(), event.getOldX()).getPlacedDie();
        game.getPlayers().get(playerIndex).getWindowCard().removeDie(event.getOldY(), event.getOldX());
        game.updateWindowCardList();
        w = game.getPlayers().get(playerIndex).getWindowCard();
        newX = event.getNewX();
        newY = event.getNewY();
        try {
            game.getToolCards().get(pos).getEffectList().get(ZERO_VALUE).accept(this);
        } catch (InvalidDieException e) {
            LOGGER.log(Level.SEVERE, e.toString(), e);
        }
    }


    public void setGame(Game game) {

        this.game = game;
    }


    public void handleVCEvent(IncrementDecrementDieEvent event) throws InvalidConnectionException, ParseException, InvalidViewException, IOException, InvalidDieException {
        int choose = event.getChoice();
        if (choose == ONE_VALUE && (d.getValue() != SIX_VALUE)){
            d.setValue(d.getValue()+ONE_VALUE);
            eventsController.setMvEvent(new ChangedDieEvent(user, d));
            eventsController.notifyObservers();
            eventsController.setMvEvent(new ModifiedPlaceEvent(user, diePositionDraftPool));
            eventsController.notifyObservers();

        }
        else if ((choose == ONE_VALUE && d.getValue() == SIX_VALUE) || (choose == TWO_VALUE && d.getValue() == ONE_VALUE) ){
            eventsController.setMvEvent(new WrongPlaceEvent(user));
            eventsController.notifyObservers();
        }
        else if (choose == TWO_VALUE && d.getValue() != ONE_VALUE){
            d.setValue(d.getValue()-ONE_VALUE);
            eventsController.setMvEvent(new ChangedDieEvent(user, d));
            eventsController.notifyObservers();
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
            LOGGER.log(Level.SEVERE,e.toString(), e);
        }

        eventsController.setMvEvent(new UpdateGameEvent(game.getWindowCardList(), lc.getUsername(), game.getRolledDice(), game.getRoundCells()));
        eventsController.notifyObservers();
        eventsController.setMvEvent(new PerformActionEvent(event.getUsername()));
        eventsController.notifyObservers();
    }

    public void checkApplyEffect(ReplaceDieEffect replaceDieEffect) throws InvalidConnectionException, ParseException, InvalidViewException, IOException {

        replaceDieEffect.applyEffect(d, eventsController.getGame(), this, diePositionDraftPool);
        System.out.println("dopo replace die apply effect");
        eventsController.setMvEvent(new SetDieEvent(user,d,diePositionDraftPool));
        eventsController.notifyObservers();
    }


    public void checkApplyEffect(SwapDieEffect swapDieEffect) throws InvalidConnectionException, ParseException, InvalidViewException, IOException {
        eventsController.setMvEvent(new SwapDieEvent(user));
        eventsController.notifyObservers();
    }

    public void handleVCEvent(SwappingDieEvent swappingDieEvent) {
        int cellPos = swappingDieEvent.getCellPos();
        int roundPos = swappingDieEvent.getRoundPos();
        SwapDieEffect effect = new SwapDieEffect();
        effect.applyEffect(d, game, diePositionDraftPool, roundPos, cellPos);
    }

    public void checkApplyEffect(PlaceRestriction placeRestriction) throws InvalidConnectionException, ParseException, InvalidViewException, IOException {
        eventsController.setMvEvent(new RequestCoordEvent(user));
        eventsController.notifyObservers();
    }

    public void handleVCEvent(PlaceDieWithRestriction placeDieWithRestriction) throws InvalidConnectionException, ParseException, InvalidViewException, IOException, InvalidDieException {
        w.placeDie(d,placeDieWithRestriction.getRow(),placeDieWithRestriction.getCol(),placeDieWithRestriction.isColor(),placeDieWithRestriction.isShade(),placeDieWithRestriction.isAround());
        game.getRolledDice().remove(diePositionDraftPool);
        game.findPlayer(placeDieWithRestriction.getUsername()).setAd(true);
        eventsController.setMvEvent(new UpdateGameEvent(game.getWindowCardList(), lc.getUsername(), game.getRolledDice(), game.getRoundCells()));
        eventsController.notifyObservers();
        eventsController.getVirtualView().createSkipTurnEvent(placeDieWithRestriction.getUsername());
        eventsController.notifyObservers();

    }

    public void handleTapWheel(ColorAndNumberEvent event) throws InvalidConnectionException, InvalidViewException, ParseException, IOException {
        w = game.findPlayer(event.getUsername()).getWindowCard();
        d = game.findPlayer(event.getUsername()).getWindowCard().getGridCell(event.getOldRow(), event.getOldCol()).getPlacedDie();
        newX = event.getNewCol();
        newY = event.getNewRow();
        MoveDieEffect moveDieEffect = new MoveDieEffect(true,true,true,event.getNumber());
        if (d.getColor().equals(color)) {
            game.findPlayer(event.getUsername()).getWindowCard().removeDie(event.getOldRow(),event.getOldCol());
            checkApplyEffect(moveDieEffect);
        }
        else{
            eventsController.setMvEvent(new RetryToolEvent(event.getUsername()));
            eventsController.notifyObservers();
        }

    }
}
