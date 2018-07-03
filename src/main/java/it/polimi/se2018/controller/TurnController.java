package it.polimi.se2018.controller;

import it.polimi.se2018.model.*;
import it.polimi.se2018.model.event.IsTurnEvent;
import it.polimi.se2018.model.event.MVEvent;
import it.polimi.se2018.model.event.UpdateGameEvent;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

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
        if (game.getTurn() == game.getPlayerNumber()*2){
            System.out.println("Sono nel caso di inizio nuovo round");
            game.getRoundCells().add(new RoundCell(game.getRound()));
            for (Die die: game.getRolledDice()) {
                game.getRoundCells().get(game.getRound()-1).addDie(die);
            }
            game.getRolledDice().removeAll(game.getRolledDice());
            game.setRolledDice();
            checkRound(playerIndex);
        }

        //IF DEL FINE PRIMO GIRO(SONO A META' ROUND). SI INVERTE IL GIRO
        else if ((game.getTurn() - game.getPlayerNumber())==0 && !eventsController.getVirtualView().getRemovedClients().contains(game.getPlayers().get(playerIndex).getUsername())){
            LOGGER.log(Level.INFO,"Sono nel caso di fine primo giro con client corrente online");
            eventsController.setMvEvent(new IsTurnEvent(game.getPlayers().get(playerIndex).getUsername(), true));
            reverse = true;
            game.nextTurn();
        }
        else if((game.getTurn() - game.getPlayerNumber())==0 && eventsController.getVirtualView().getRemovedClients().contains(game.getPlayers().get(playerIndex).getUsername())){
            LOGGER.log(Level.INFO,"Sono nel caso di fine primo giro con client corrente offline");
            reverse = true;
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
        if(playerIndex==game.getPlayerNumber()-1) {
            game.setFirstPlayer(game.getPlayers().get(0));
            LOGGER.log(Level.INFO,"checkRound su ultimo player");
        }
        else{
            playerIndex++;
            game.setFirstPlayer(game.getPlayers().get(playerIndex));
        }

        //roudtrack e gestione distribuzione dadi
        reverse = false;
        game.nextTurn();
        if(eventsController.getVirtualView().getRemovedClients().contains(game.getFirstPlayer().getUsername())){
            LOGGER.log(Level.INFO,"il primo giocatore del primo turno è offline");
            checkSkip(playerIndex);
        }
        else
            eventsController.setMvEvent(new IsTurnEvent(game.getFirstPlayer().getUsername(), true));
    }

    /**
     * Method that checks if turn can skipped to the incoming/previous player
     * @param playerIndex index of the current player
     * @throws InvalidConnectionException exception
     * @throws InvalidViewException exception
     * @throws IOException exception
     */
    private void checkSkip(int playerIndex) throws InvalidConnectionException, InvalidViewException, org.json.simple.parser.ParseException, IOException {
        if (!reverse&&playerIndex!=game.getPlayerNumber()-1&&!eventsController.getVirtualView().getRemovedClients().contains(game.getPlayers().get(playerIndex+1).getUsername()))
            eventsController.setMvEvent(new IsTurnEvent(game.getPlayers().get(playerIndex + 1).getUsername(), true));
        else if(!reverse&&playerIndex!=game.getPlayerNumber()-1&&eventsController.getVirtualView().getRemovedClients().contains(game.getPlayers().get(playerIndex+1).getUsername())){
            game.nextTurn();
            handleSkipTurn(playerIndex+1);
        }
        else if (!reverse&&playerIndex==game.getPlayerNumber()-1&&!eventsController.getVirtualView().getRemovedClients().contains(game.getPlayers().get(0).getUsername()))
            eventsController.setMvEvent(new IsTurnEvent(game.getPlayers().get(0).getUsername(), true));
        else if(!reverse&&playerIndex==game.getPlayerNumber()-1&&eventsController.getVirtualView().getRemovedClients().contains(game.getPlayers().get(0).getUsername())) {
            game.nextTurn();
            handleSkipTurn(0);
        }

        else if(reverse&&playerIndex!=0&&!eventsController.getVirtualView().getRemovedClients().contains(game.getPlayers().get(playerIndex-1).getUsername()))
            eventsController.setMvEvent(new IsTurnEvent(game.getPlayers().get(playerIndex - 1).getUsername(), true));
        else if(reverse&&playerIndex!=0&&eventsController.getVirtualView().getRemovedClients().contains(game.getPlayers().get(playerIndex-1).getUsername())) {
            game.nextTurn();
            handleSkipTurn(playerIndex - 1);
        }
        else if(reverse&&playerIndex==0&&!eventsController.getVirtualView().getRemovedClients().contains(game.getPlayers().get(game.getPlayerNumber()-1).getUsername()))
            eventsController.setMvEvent(new IsTurnEvent(game.getPlayers().get(game.getPlayerNumber()-1).getUsername(), true));
        else if (reverse&&playerIndex==0&&eventsController.getVirtualView().getRemovedClients().contains(game.getPlayers().get(game.getPlayerNumber()-1).getUsername())) {
            game.nextTurn();
            handleSkipTurn(game.getPlayerNumber() - 1);
        }
        game.nextTurn();
    }



}
