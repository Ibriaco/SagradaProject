package it.polimi.se2018.controller;

import it.polimi.se2018.model.*;
import it.polimi.se2018.model.event.IsTurnEvent;
import it.polimi.se2018.model.event.MVEvent;
import it.polimi.se2018.model.event.UpdateGameEvent;
import it.polimi.se2018.org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import static it.polimi.se2018.ServerConfig.*;

public class TurnController {
    private EventsController eventsController;

    public void setGame(Game game) {
        this.game = game;
    }

    private Game game;
    private boolean reverse = false;
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
    protected void handleSkipTurn (int playerIndex) throws InvalidConnectionException, ParseException, InvalidViewException, IOException, InvalidDieException {
         if (game.getTurn() == game.getPlayerNumber()*TWO_VALUE){
            game.getRoundCells().add(new RoundCell(game.getRound()));
            for (Die die: game.getRolledDice()) {
                game.getRoundCells().get(game.getRound()-ONE_VALUE).addDie(die);
            }
            game.getRolledDice().removeAll(game.getRolledDice());
            game.setRolledDice();
            checkRound(playerIndex);
        }
        else if ((game.getTurn() - game.getPlayerNumber())==ZERO_VALUE && !eventsController.getVirtualView().getRemovedClients().contains(game.getPlayers().get(playerIndex).getUsername())){
            eventsController.setMvEvent(new IsTurnEvent(game.getPlayers().get(playerIndex).getUsername(), BOOL_TRUE));
            reverse = BOOL_TRUE;
            game.nextTurn();
        }
        else if((game.getTurn() - game.getPlayerNumber())==ZERO_VALUE && eventsController.getVirtualView().getRemovedClients().contains(game.getPlayers().get(playerIndex).getUsername())){
            reverse = BOOL_TRUE;
            checkSkip(playerIndex);
        }
        else {
            checkSkip(playerIndex);
        }
        eventsController.setPlayerIndex(game.getPlayers().indexOf(game.findPlayer(eventsController.getMvEvent().getUsername())));
        eventsController.setTimer(new TimerThread(eventsController,eventsController.getPlayerIndex()));
        eventsController.getTimer().start();
        MVEvent tempMVEvent = eventsController.getMvEvent();
        game.findPlayer(tempMVEvent.getUsername()).setPlacedDie(false);
        if(game.findPlayer(tempMVEvent.getUsername()).isRunningPliers()) {
            game.findPlayer(tempMVEvent.getUsername()).setRunningPliers(false);
            eventsController.getVirtualView().createSkipTurnEvent(tempMVEvent.getUsername());
        }
        else {
            eventsController.setMvEvent(new UpdateGameEvent(game.getWindowCardList(), eventsController.getLobbyController().getUsername(), game.getRolledDice(), game.getRoundCells()));
            eventsController.notifyObservers();
            eventsController.setMvEvent(tempMVEvent);
            eventsController.notifyObservers();
        }
    }

    /**
     * Method that checks rounds and sets the first player to play in the current round
     * @param playerIndex index of the current player
     * @throws InvalidConnectionException exception
     * @throws InvalidViewException exception
     * @throws IOException exception
     */
    private void checkRound(int playerIndex) throws InvalidConnectionException, InvalidViewException, ParseException, IOException, InvalidDieException {
        if(playerIndex==game.getPlayerNumber()-ONE_VALUE) {
            game.setFirstPlayer(game.getPlayers().get(ZERO_VALUE));
        }
        else{
            playerIndex++;
            game.setFirstPlayer(game.getPlayers().get(playerIndex));
        }
        reverse = BOOL_FALSE;
        game.nextTurn();
        if(game.getRound() != 11) {
            if (eventsController.getVirtualView().getRemovedClients().contains(game.getFirstPlayer().getUsername())) {
                checkSkip(playerIndex);
            } else
                eventsController.setMvEvent(new IsTurnEvent(game.getFirstPlayer().getUsername(), BOOL_TRUE));
        }
        else{
            endGame();
        }

    }

    public void endGame() throws InvalidConnectionException, InvalidViewException, ParseException, IOException {
        game.getLastOnlinePlayers();

        if (game.getOnlinePlayers().size() == 1) {
            game.getOnlinePlayers().get(0).setPlayerScore(100);
        }
        else {
            for (Player p : game.getOnlinePlayers()) {
                p.getPrivateObjective().calculateBonus(p);

                for (PublicObjective po : game.getPublicCards()) {
                    po.calculateBonus(p);
                }
                LOGGER.log(Level.INFO,"player: " + p.getUsername() + " score: " + p.getPlayerScore());
            }
        }

        for (Player p : game.getNotOnlinePlayers()) {
            p.setPlayerScore(-20);
        }

        game.setFinished(true);

        calculateWinnerAndLoser();
    }

    private void calculateWinnerAndLoser() {

    }

    /**
     * Method that checks if turn can skipped to the incoming/previous player
     * @param playerIndex index of the current player
     * @throws InvalidConnectionException exception
     * @throws InvalidViewException exception
     * @throws IOException exception
     */
    private void checkSkip(int playerIndex) throws InvalidConnectionException, InvalidViewException, ParseException, IOException, InvalidDieException {
        if (!reverse&&playerIndex!=game.getPlayerNumber()-1 && !eventsController.getVirtualView().getRemovedClients().contains(game.getPlayers().get(playerIndex+1).getUsername()))
            eventsController.setMvEvent(new IsTurnEvent(game.getPlayers().get(playerIndex + 1).getUsername(), BOOL_TRUE));
        else if(!reverse&&playerIndex!=game.getPlayerNumber()-1&&eventsController.getVirtualView().getRemovedClients().contains(game.getPlayers().get(playerIndex+1).getUsername())){
            game.nextTurn();
            handleSkipTurn(playerIndex+1);
        }
        else if (!reverse&&playerIndex==game.getPlayerNumber()-1&&!eventsController.getVirtualView().getRemovedClients().contains(game.getPlayers().get(0).getUsername()))
            eventsController.setMvEvent(new IsTurnEvent(game.getPlayers().get(0).getUsername(), BOOL_TRUE));
        else if(!reverse&&playerIndex==game.getPlayerNumber()-1&&eventsController.getVirtualView().getRemovedClients().contains(game.getPlayers().get(0).getUsername())) {
            game.nextTurn();
            handleSkipTurn(0);
        }

        else if(reverse&&playerIndex!=0&&!eventsController.getVirtualView().getRemovedClients().contains(game.getPlayers().get(playerIndex-1).getUsername()))
            eventsController.setMvEvent(new IsTurnEvent(game.getPlayers().get(playerIndex - 1).getUsername(), BOOL_TRUE));
        else if(reverse&&playerIndex!=0&&eventsController.getVirtualView().getRemovedClients().contains(game.getPlayers().get(playerIndex-1).getUsername())) {
            game.nextTurn();
            handleSkipTurn(playerIndex - 1);
        }
        else if(reverse&&playerIndex==0&&!eventsController.getVirtualView().getRemovedClients().contains(game.getPlayers().get(game.getPlayerNumber()-1).getUsername()))
            eventsController.setMvEvent(new IsTurnEvent(game.getPlayers().get(game.getPlayerNumber()-1).getUsername(), BOOL_TRUE));
        else if (reverse&&playerIndex==0&&eventsController.getVirtualView().getRemovedClients().contains(game.getPlayers().get(game.getPlayerNumber()-1).getUsername())) {
            game.nextTurn();
            handleSkipTurn(game.getPlayerNumber() - 1);
        }
        game.nextTurn();
    }
}
