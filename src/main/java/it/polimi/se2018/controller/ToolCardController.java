package it.polimi.se2018.controller;

import it.polimi.se2018.controller.effects.*;
import it.polimi.se2018.model.*;
import it.polimi.se2018.model.event.MVEvent;
import it.polimi.se2018.model.event.ModifiedPlaceEvent;
import it.polimi.se2018.model.event.UpdateGameEvent;
import it.polimi.se2018.view.viewevents.PlaceModifiedDie;
import it.polimi.se2018.view.viewevents.SelectDieEvent;
import it.polimi.se2018.view.viewevents.UseToolEvent;
import org.json.simple.parser.ParseException;
import java.io.IOException;
import java.util.ArrayList;

public class ToolCardController{

    private ArrayList<ToolCard> toolCards;
    private Game game;
    private String user;
    private int pos;
    private Die d;
    private int diePos;
    MVEvent mvEvent;
    private EventsController eventsController;
    private LobbyController lc;

    public ToolCardController(EventsController controller, LobbyController lobbyController) {
        this.eventsController = controller;
        this.lc = lobbyController;
    }

    /*
    ORDINE DI ESECUZIONE
    1)
    2)
    3)
    4)
    5)
     */

    public void handleVCEvent(UseToolEvent event) throws InvalidConnectionException, ParseException, InvalidViewException, IOException {
        user = event.getUsername();
        pos = event.getToolCardNumber();
        mvEvent = new ChangeDieEvent(user);
        eventsController.setMvEvent(mvEvent);
        eventsController.notifyObservers();
    }


    public void checkApplyEffect(ReverseDieEffect reverseDieEffect) throws InvalidDieException {
        //controllo token disponibili
        reverseDieEffect.applyEffect(d);
        System.out.println("Valore del dado ribaltato: " + d.getValue());


    }

    public void checkApplyEffect(RollDieEffect rollDieEffect){
        try {
            rollDieEffect.applyEffect(d);
        } catch (InvalidDieException e) {
            e.printStackTrace();
        }
    }

    public void checkApplyEffect(IncDecEffect incDecEffect) {
    }

    public void checkApplyEffect(MoveDieEffect moveDieEffect) {
    }

    public void handleVCEvent(SelectDieEvent event) throws InvalidConnectionException, ParseException, InvalidViewException, IOException {

        System.out.println("Valore del dado: " + game.getRolledDice().get(event.getPosition()).getValue());
        d = new Die(game.getColorList());
        d = game.getRolledDice().get(diePos);
        System.out.println("Dimensione lista effetti: " + game.getToolCards().get(pos).getEffectList().size());
        System.out.println(game.getToolCards().get(pos).getTitle());
        try {
            game.getToolCards().get(pos).getEffectList().get(0).accept(this);
        } catch (InvalidDieException e) {
            e.printStackTrace();
        }
        mvEvent = new ModifiedPlaceEvent(user, diePos);
        eventsController.setMvEvent(mvEvent);
        eventsController.notifyObservers();
    }

    public void handleVCEvent(PlaceModifiedDie placeModifiedDie){
        System.out.println("Sto per piazzare il dado");
        game.findPlayer(placeModifiedDie.getUsername()).getWindowCard().placeDie(game.getRolledDice().get(placeModifiedDie.getPos()), placeModifiedDie.getY(), placeModifiedDie.getX(), true, true);
        System.out.println("Sto per aggiornare la card");
        game.updateWindowCardList();
        System.out.println("Ho aggiornato la carta");
        game.getRolledDice().remove(placeModifiedDie.getPos());
        mvEvent = new UpdateGameEvent(game.getWindowCardList(), lc.getUsername(), game.getRolledDice());
        eventsController.setMvEvent(mvEvent);
        try {
            eventsController.notifyObservers();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidConnectionException e) {
            e.printStackTrace();
        } catch (InvalidViewException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    public void setGame(Game game) {

        this.game = game;
    }
}
