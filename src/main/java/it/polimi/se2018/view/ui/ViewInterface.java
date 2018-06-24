package it.polimi.se2018.view.ui;

import it.polimi.se2018.model.event.*;
import it.polimi.se2018.model.InvalidConnectionException;
import it.polimi.se2018.model.InvalidViewException;
import it.polimi.se2018.MyObservable;
import it.polimi.se2018.MyObserver;
import it.polimi.se2018.view.viewevents.VCEvent;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.rmi.RemoteException;

/**
 * Represents the view interface
 * Observed by the network Handler
 * Observes the network handler
 */
public interface ViewInterface extends MyObserver, MyObservable {

    void updateWindowCard();

    void showUI() throws IOException, InvalidConnectionException, InvalidViewException, ParseException;

    void receiveEvent(VCEvent event) throws InvalidConnectionException, IOException, InvalidViewException, ParseException;

    void loginScreen() throws IOException, InvalidConnectionException, InvalidViewException, ParseException;

    void handleMVEvent(LoggedUserEvent event);

    void handleMVEvent(PrivateCardEvent privateCardEvent);

    void handleMVEvent(WindowCardEvent event) throws RemoteException, InvalidConnectionException, InvalidViewException;

    void handleMVEvent(UpdateGameEvent updateGameEvent);

    void handleMVEvent(PublicCardEvent publicCardEvent);

    void handleMVEvent(ToolCardEvent toolCardEvent);

    void createNH(String choice) throws RemoteException;

    void setUsername(String u);

    void createLoginEvent() throws InvalidConnectionException, IOException, InvalidViewException, ParseException;

    void handleMVEvent(IsTurnEvent isTurnEvent) throws InvalidConnectionException, InvalidViewException, ParseException, IOException;



    /*
    public void updateRoundTrack(Game game){

    }
    public void updateRoundDice(Game game){

    }
    public void updateRolledDice(Game game){

    }

    public void updateToolCard(){

    }

    public void showRoundTrack(Game game){

    }
    public void showRoundDice(Game game){

    }
    public void showRolledDice(Game game){

    }
    public void showPublicCards(Game game){

    }
    public void showToolCards(Game game){

    }
    public void showPrivateCard(Game game){

    }
    public void showPlayersWindow(Game game){

    }
    public void rollDice(){

    }
    public void skipTurn(){

    }

    public void update(VCEvent event) {

    }
*/

}

