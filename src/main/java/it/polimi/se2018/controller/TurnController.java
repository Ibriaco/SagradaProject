package it.polimi.se2018.controller;

import it.polimi.se2018.model.*;
import it.polimi.se2018.model.event.IsTurnEvent;
import it.polimi.se2018.model.event.MVEvent;
import it.polimi.se2018.model.event.UpdateGameEvent;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import static it.polimi.se2018.ServerConfig.*;

public class TurnController {
    private EventsController eventsController;
    private Game game;
    private boolean reverse = false;
    private MVEvent tempMVEvent;
    private static final Logger LOGGER = Logger.getGlobal();


    public TurnController(EventsController eventsController){
        this.eventsController = eventsController;
    }

    /**
     * Method that handles concretely the skin turn
     * @param playerIndex index of the current player
     * @throws InvalidConnectionException exception
     * @throws InvalidViewException exception
     * @throws IOException exception
     */
    protected void handleSkipTurn (int playerIndex) throws InvalidConnectionException, org.json.simple.parser.ParseException, InvalidViewException, IOException {
        game = eventsController.getGame();
        // IF DEL FINE ULTIMO TURNO. INIZIO NUOVO ROUND
        if (game.getTurn() == game.getPlayerNumber()*TWO_VALUE){
            System.out.println("Sono nel caso di inizio nuovo round");
            game.getRoundCells().add(new RoundCell(game.getRound()));
            for (Die die: game.getRolledDice()) {
                game.getRoundCells().get(game.getRound()-ONE_VALUE).addDie(die);
            }
            game.getRolledDice().removeAll(game.getRolledDice());
            game.setRolledDice();
            checkRound(playerIndex);
        }

        //IF DEL FINE PRIMO GIRO(SONO A META' ROUND). SI INVERTE IL GIRO
        else if ((game.getTurn() - game.getPlayerNumber())==ZERO_VALUE && !eventsController.getVirtualView().getRemovedClients().contains(game.getPlayers().get(playerIndex).getUsername())){
            LOGGER.log(Level.INFO,"Sono nel caso di fine primo giro con client corrente online");
            eventsController.setMvEvent(new IsTurnEvent(game.getPlayers().get(playerIndex).getUsername(), BOOL_TRUE));
            reverse = BOOL_TRUE;
            game.nextTurn();
        }
        else if((game.getTurn() - game.getPlayerNumber())==ZERO_VALUE && eventsController.getVirtualView().getRemovedClients().contains(game.getPlayers().get(playerIndex).getUsername())){
            LOGGER.log(Level.INFO,"Sono nel caso di fine primo giro con client corrente offline");
            reverse = BOOL_TRUE;
            checkSkip(playerIndex);
        }
        //GESTIONE CLASSICA DELLO SKIP TURN SE NON CI SONO CASI LIMITE DA GESTIRE
        else {
            LOGGER.log(Level.INFO,"Sono nel caso di skip");
            checkSkip(playerIndex);
        }

        LOGGER.log(Level.INFO," turno in uscita:" + game.getTurn());
        eventsController.setPlayerIndex(game.getPlayers().indexOf(game.findPlayer(eventsController.getMvEvent().getUsername())));
        eventsController.setTimer(new TimerThread(eventsController,eventsController.getPlayerIndex()));
        eventsController.getTimer().start();
        tempMVEvent = eventsController.getMvEvent();
        //MANDO UPDATE EVENT
        eventsController.setMvEvent(new UpdateGameEvent(game.getWindowCardList(), eventsController.getLobbyController().getUsername(), game.getRolledDice(), game.getRoundCells()));
        eventsController.notifyObservers();
        //MANDO SKIP TURN EVENT
        eventsController.setMvEvent(tempMVEvent);
        eventsController.notifyObservers();
    }

    /**
     * Method that checks rounds and sets the first player to play in the current round
     * @param playerIndex index of the current player
     * @throws InvalidConnectionException exception
     * @throws InvalidViewException exception
     * @throws IOException exception
     */
    private void checkRound(int playerIndex) throws InvalidConnectionException, InvalidViewException, org.json.simple.parser.ParseException, IOException {
        LOGGER.log(Level.INFO,"sono in checkRound");
        if(playerIndex==game.getPlayerNumber()-ONE_VALUE) {
            game.setFirstPlayer(game.getPlayers().get(ZERO_VALUE));
            LOGGER.log(Level.INFO,"checkRound su ultimo player");
        }
        else{
            playerIndex++;
            game.setFirstPlayer(game.getPlayers().get(playerIndex));
        }

        //roudtrack e gestione distribuzione dadi
        reverse = BOOL_FALSE;
        game.nextTurn();
        if(eventsController.getVirtualView().getRemovedClients().contains(game.getFirstPlayer().getUsername())){
            LOGGER.log(Level.INFO,"il primo giocatore del primo turno è offline");
            checkSkip(playerIndex);
        }
        else
            eventsController.setMvEvent(new IsTurnEvent(game.getFirstPlayer().getUsername(), BOOL_TRUE));
    }

    /**
     * Method that checks if turn can skipped to the incoming/previous player
     * @param playerIndex index of the current player
     * @throws InvalidConnectionException exception
     * @throws InvalidViewException exception
     * @throws IOException exception
     */
    private void checkSkip(int playerIndex) throws InvalidConnectionException, InvalidViewException, org.json.simple.parser.ParseException, IOException {
        if (!reverse&&playerIndex!=game.getPlayerNumber()-ONE_VALUE && !eventsController.getVirtualView().getRemovedClients().contains(game.getPlayers().get(playerIndex+ONE_VALUE).getUsername()))
            eventsController.setMvEvent(new IsTurnEvent(game.getPlayers().get(playerIndex + ONE_VALUE).getUsername(), BOOL_TRUE));
        else if(!reverse&&playerIndex!=game.getPlayerNumber()-1&&eventsController.getVirtualView().getRemovedClients().contains(game.getPlayers().get(playerIndex+ONE_VALUE).getUsername())){
            game.nextTurn();
            handleSkipTurn(playerIndex+ONE_VALUE);
        }
        else if (!reverse&&playerIndex==game.getPlayerNumber()-ONE_VALUE&&!eventsController.getVirtualView().getRemovedClients().contains(game.getPlayers().get(ZERO_VALUE).getUsername()))
            eventsController.setMvEvent(new IsTurnEvent(game.getPlayers().get(ZERO_VALUE).getUsername(), BOOL_TRUE));
        else if(!reverse&&playerIndex==game.getPlayerNumber()-ONE_VALUE&&eventsController.getVirtualView().getRemovedClients().contains(game.getPlayers().get(ZERO_VALUE).getUsername())) {
            game.nextTurn();
            handleSkipTurn(ZERO_VALUE);
        }

        else if(reverse&&playerIndex!=ZERO_VALUE&&!eventsController.getVirtualView().getRemovedClients().contains(game.getPlayers().get(playerIndex-ONE_VALUE).getUsername()))
            eventsController.setMvEvent(new IsTurnEvent(game.getPlayers().get(playerIndex - ONE_VALUE).getUsername(), BOOL_TRUE));
        else if(reverse&&playerIndex!=ZERO_VALUE&&eventsController.getVirtualView().getRemovedClients().contains(game.getPlayers().get(playerIndex-ONE_VALUE).getUsername())) {
            game.nextTurn();
            handleSkipTurn(playerIndex - ONE_VALUE);
        }
        else if(reverse&&playerIndex==ZERO_VALUE&&!eventsController.getVirtualView().getRemovedClients().contains(game.getPlayers().get(game.getPlayerNumber()-ONE_VALUE).getUsername()))
            eventsController.setMvEvent(new IsTurnEvent(game.getPlayers().get(game.getPlayerNumber()-1).getUsername(), BOOL_TRUE));
        else if (reverse&&playerIndex==ZERO_VALUE&&eventsController.getVirtualView().getRemovedClients().contains(game.getPlayers().get(game.getPlayerNumber()-ONE_VALUE).getUsername())) {
            game.nextTurn();
            handleSkipTurn(game.getPlayerNumber() - ONE_VALUE);
        }
        game.nextTurn();
    }



}
