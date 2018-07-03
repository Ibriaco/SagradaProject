package it.polimi.se2018.controller;

import it.polimi.se2018.controller.effects.*;
import it.polimi.se2018.model.*;
import it.polimi.se2018.model.event.*;
import it.polimi.se2018.view.viewevents.MovingDieEvent;
import it.polimi.se2018.view.viewevents.SelectDieEvent;
import it.polimi.se2018.view.viewevents.UseToolEvent;
import org.json.simple.parser.ParseException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ToolCardController{

    private Game game;
    private String user;
    private int pos;
    private Die d;
    private MVEvent mvEvent;
    private int newX;
    private int newY;
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
        if(game.getToolCards().get(pos).getType().equals("AD"))
            mvEvent = new ChangeDieEvent(user);
        else if(game.getToolCards().get(pos).getType().equals("OW"))
            mvEvent = new MoveDieEvent(user);

        eventsController.setMvEvent(mvEvent);
        eventsController.notifyObservers();
    }


    public void checkApplyEffect(ReverseDieEffect reverseDieEffect) throws InvalidDieException {
        //controllo token disponibili
        reverseDieEffect.applyEffect(d);


    }

    public void checkApplyEffect(RollDieEffect rollDieEffect){
        try {
            rollDieEffect.applyEffect(d);
        } catch (InvalidDieException e) {
            LOGGER.log(Level.SEVERE, "Invalid die exception!");
        }
    }

    public void checkApplyEffect(IncDecEffect incDecEffect) {
    }

    public void checkApplyEffect(MoveDieEffect moveDieEffect) {

        moveDieEffect.applyEffect(w, d, newX, newY);
    }

    public void handleVCEvent(SelectDieEvent event) throws InvalidConnectionException, ParseException, InvalidViewException, IOException {

        d = game.getRolledDice().get(event.getPosition());

        try {
            game.getToolCards().get(pos).getEffectList().get(0).accept(this);
        } catch (InvalidDieException e) {
            LOGGER.log(Level.SEVERE, "Invalid die exception!");
        }
        mvEvent = new ChangedDieEvent(user, d);
        eventsController.setMvEvent(mvEvent);
        eventsController.notifyObservers();

        mvEvent = new ModifiedPlaceEvent(user, event.getPosition());
        eventsController.setMvEvent(mvEvent);
        eventsController.notifyObservers();
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
            LOGGER.log(Level.SEVERE, "Invalid die exception!");
        }
        eventsController.setMvEvent(new UpdateGameEvent(game.getWindowCardList(), lc.getUsername(), game.getRolledDice(), game.getRoundCells()));
        eventsController.notifyObservers();
    }


    public void setGame(Game game) {

        this.game = game;
    }


}
