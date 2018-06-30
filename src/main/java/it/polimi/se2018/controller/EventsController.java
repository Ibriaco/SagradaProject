package it.polimi.se2018.controller;

import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import it.polimi.se2018.model.*;
import it.polimi.se2018.model.event.*;
import it.polimi.se2018.MyObservable;
import it.polimi.se2018.MyObserver;
import it.polimi.se2018.network.server.VirtualView;
import it.polimi.se2018.view.viewevents.*;
import it.polimi.se2018.view.viewevents.RollDiceEvent;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EventsController implements ControllerInterface, MyObserver, MyObservable {

    private Game game;
    private boolean reverse = false;
    private boolean reconnection = false;
    private boolean stopThread = false;
    private LobbyController lobbyController;
    private ArrayList<MyObserver> observerCollection = new ArrayList<>();
    private MVEvent mvEvent;
    private VirtualView virtualView;
    private int counter = 0;
    private int playerIndex=0;
    private static final Logger LOGGER = Logger.getLogger(EventsController.class.getName());

    public EventsController(VirtualView virtualView){
        this.virtualView = virtualView;
        lobbyController = new LobbyController(this, virtualView);
    }

    private boolean checkPlayer(String username) {

        return (game.getPlayers().indexOf(game.findPlayer(username)) == game.getTurn());

    }

    private boolean checkValidSkip(SkipTurnEvent e) {

        return this.checkPlayer(e.getUsername());
    }

    public void setReconnection(boolean reconnection) {
        this.reconnection = reconnection;
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
        if (!reconnection)
            lobbyController.handleLogin(event);
        else
            lobbyController.handleReconnection(event);
    }

    @Override
    public void handleVCEvent(PlaceDieEvent event) throws InvalidConnectionException, ParseException, InvalidViewException, IOException {
        System.out.println("player index : " + playerIndex + "corrispondente a : " + game.getPlayers().get(playerIndex).getUsername());
        System.out.println("nome giocatore che ha mandato evento: " + event.getUsername());
        if (event.getUsername().equals(game.getPlayers().get(playerIndex).getUsername())) {
            game.findPlayer(event.getUsername()).getWindowCard().placeDie(game.getRolledDice().get(event.getPos()), event.getCoordY(), event.getCoordX(), true, true);
            game.updateWindowCardList();
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

    @Override
    public void handleVCEvent(SkipTurnEvent event) throws InvalidConnectionException, ParseException, InvalidViewException, IOException {
        //System.out.println("player index : " + playerIndex + "\tcorrispondente a : " + game.getPlayers().get(playerIndex).getUsername());
        System.out.println("nome giocatore che ha mandato evento: " + event.getUsername());
        System.out.println("turno in arrivo: " + game.getTurn());
        if (event.getUsername().equals(game.getPlayers().get(playerIndex).getUsername())){
            handleSkipTurn(game.getPlayers().indexOf(game.findPlayer(event.getUsername())));
            stopThread = true;
        }
    }

    private void handleSkipTurn (int playerIndex) throws InvalidConnectionException, ParseException, InvalidViewException, IOException {
        //BISOGNA GESIRE MEGLIO A CHI VIENE ASSEGNATO IL FIRST PLAYER
        // IF DEL FINE ULTIMO TURNO. INIZIO NUOVO ROUND
        if (game.getTurn() == game.getPlayerNumber()*2){
            System.out.println("Sono nel caso di inizio nuovo round");
            //System.out.println("Valore di player index: " + playerIndex );
            checkRound(playerIndex);
        }

        //IF DEL FINE PRIMO GIRO(SONO A META' ROUND). SI INVERTE IL GIRO
        else if ((game.getTurn() - game.getPlayerNumber())==0 && !virtualView.getRemovedClients().contains(game.getPlayers().get(playerIndex).getUsername())){
            System.out.println("Sono nel caso di fine primo giro con client corrente online");
           // System.out.println("Valore di player index: " + playerIndex );
            mvEvent = new IsTurnEvent(game.getPlayers().get(playerIndex).getUsername(), true);
            reverse = true;
            game.nextTurn();
        }
        else if(game.getTurn()-game.getPlayerNumber()==0 && virtualView.getRemovedClients().contains(game.getPlayers().get(playerIndex).getUsername())){
            System.out.println("Sono nel caso di fine primo giro con client corrente offline");
           // System.out.println("Valore di player index: " + playerIndex );
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


        System.out.println(" turno in uscita:" + game.getTurn());
        this.playerIndex = game.getPlayers().indexOf(game.findPlayer(mvEvent.getUsername()));
        //System.out.println("PLAYER INDEX AGGIORNATO PRIMA DI NOTIFICARE EVENTO. ULTIMO AGGIORNAMENTO PRIMA DEL CHECKEVENT: " + this.playerIndex);
        //launchThread(this.playerIndex);
        System.out.println(" ROUND: " + game.getRound());
        notifyObservers();
    }

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
            }
            stopThread = false;
        }

    }).start();
    }

    private void checkRound(int playerIndex) throws InvalidConnectionException, InvalidViewException, ParseException, IOException {
        System.out.println("sono in checkRound");
        if(playerIndex==game.getPlayerNumber()-1) {
            game.setFirstPlayer(game.getPlayers().get(0));
            System.out.println("checkRound su ultimo player");
        }
        else{
            playerIndex++;
            game.setFirstPlayer(game.getPlayers().get(playerIndex));
        }

        //roudtrack e gestione distribuzione dadi
        reverse = false;
        game.nextTurn();
        if(virtualView.getRemovedClients().contains(game.getFirstPlayer().getUsername())){
            System.out.println("il primo giocatore del primo turno è offline");
            checkSkip(playerIndex);
        }

        else
          //  mvEvent = new IsTurnEvent(game.getPlayers().get(playerIndex).getUsername(), true);
            mvEvent = new IsTurnEvent(game.getFirstPlayer().getUsername(), true);
    }

    /*private boolean checkBound(int index, boolean reverse) {
        return (reverse || index == game.getPlayerNumber() - 1) && (!reverse && index == game.getPlayerNumber() - 1 || !reverse || index == 0);
    }*/


    private void checkSkip(int playerIndex) throws InvalidConnectionException, InvalidViewException, ParseException, IOException {
        if (!reverse&&playerIndex!=game.getPlayerNumber()-1&&!virtualView.getRemovedClients().contains(game.getPlayers().get(playerIndex+1).getUsername())) {
            mvEvent = new IsTurnEvent(game.getPlayers().get(playerIndex + 1).getUsername(), true);
            System.out.println("CS\tPROX ONLINE/CRESCENTE/NON ULTIMO");
        }
        else if(!reverse&&playerIndex!=game.getPlayerNumber()-1&&virtualView.getRemovedClients().contains(game.getPlayers().get(playerIndex+1).getUsername())){
            game.nextTurn();
            System.out.println("CS\tNEXT TURN/ PROX OFFLINE/CRESCENTE/NON ULTIMO ----> CHIAMO HANDLE SKIP TURN SUL PROX");
            handleSkipTurn(playerIndex+1);
        }
        else if (!reverse&&playerIndex==game.getPlayerNumber()-1&&!virtualView.getRemovedClients().contains(game.getPlayers().get(0).getUsername())) {
            mvEvent = new IsTurnEvent(game.getPlayers().get(0).getUsername(), true);
            System.out.println("CS\tPLAYER IN 0 ONLINE/CRESCENTE/ULTIMO");
        }
        else if(!reverse&&playerIndex==game.getPlayerNumber()-1&&virtualView.getRemovedClients().contains(game.getPlayers().get(0).getUsername())) {
            game.nextTurn();
            System.out.println("CS\tNEXT TURN/PLAYER IN 0 OFFLINE/CRESCENTE/ULTIMO ----> CHIAMO HANDLE SKIP TURN SU 0");
            handleSkipTurn(0);
        }

        else if(reverse&&playerIndex!=0&&!virtualView.getRemovedClients().contains(game.getPlayers().get(playerIndex-1).getUsername()))
        {mvEvent = new IsTurnEvent(game.getPlayers().get(playerIndex - 1).getUsername(), true);
            System.out.println("CS\tPREV ONLINE/DECRESCENTE/NON PRIMO");
        }
        else if(reverse&&playerIndex!=0&&virtualView.getRemovedClients().contains(game.getPlayers().get(playerIndex-1).getUsername())) {
            game.nextTurn();
            System.out.println("CS\tNEXT TURN/ PREV OFFLINE/DECRESCENTE/NON PRIMO ----> CHIAMO HANDLE SKIP TURN SUL PREV");
            handleSkipTurn(playerIndex - 1);
        }
        else if(reverse&&playerIndex==0&&!virtualView.getRemovedClients().contains(game.getPlayers().get(game.getPlayerNumber()-1).getUsername())) {
            mvEvent = new IsTurnEvent(game.getPlayers().get(game.getPlayerNumber() - 1).getUsername(), true);
            System.out.println("CS\tULTIMO PLAYER ONLINE/DECRESCENTE/PRIMO");
        }
        else if (reverse&&playerIndex==0&&virtualView.getRemovedClients().contains(game.getPlayers().get(game.getPlayerNumber()-1).getUsername())) {
            game.nextTurn();
            System.out.println("CS\tNEXT TURN/ULTIMO PLAYER OFFLINE/DECRESCENTE/PRIMO ----> CHIAMO HANDLE SKIP TURN SU ULTIMO");
            handleSkipTurn(game.getPlayerNumber() - 1);
        }
        game.nextTurn();
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

    public int getPlayerIndex() {
        return playerIndex;
    }
}
