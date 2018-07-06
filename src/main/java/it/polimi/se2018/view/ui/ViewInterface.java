package it.polimi.se2018.view.ui;

import it.polimi.se2018.controller.ChangeDieEvent;
import it.polimi.se2018.model.InvalidDieException;
import it.polimi.se2018.model.event.*;
import it.polimi.se2018.model.InvalidConnectionException;
import it.polimi.se2018.model.InvalidViewException;
import it.polimi.se2018.MyObservable;
import it.polimi.se2018.MyObserver;
import it.polimi.se2018.org.json.simple.parser.ParseException;
import it.polimi.se2018.view.viewevents.VCEvent;

import java.io.IOException;
import java.rmi.RemoteException;

/**
 * Represents the view interface
 * Observed by the network Handler
 * Observes the network handler
 */
public interface ViewInterface extends MyObserver, MyObservable {

    void updateWindowCard();

    void showUI() throws IOException, InvalidConnectionException, InvalidViewException, ParseException, InvalidDieException;

    void receiveEvent(VCEvent event) throws InvalidConnectionException, IOException, InvalidViewException, ParseException, InvalidDieException;

    void loginScreen() throws IOException, InvalidConnectionException, InvalidViewException, ParseException, InvalidDieException;

    void handleMVEvent(LoggedUserEvent event);

    void handleMVEvent(DisconnectedEvent event);

    void handleMVEvent(PrivateCardEvent privateCardEvent);

    void handleMVEvent(WindowCardEvent event) throws RemoteException, InvalidConnectionException, InvalidViewException;

    void handleMVEvent(UpdateGameEvent updateGameEvent);

    void handleMVEvent(PublicCardEvent publicCardEvent);

    void handleMVEvent(ToolCardEvent toolCardEvent);

    void createNH(String choice) throws RemoteException;

    void setUsername(String u);

    void createLoginEvent() throws InvalidConnectionException, IOException, InvalidViewException, ParseException, InvalidDieException;

    void handleMVEvent(IsTurnEvent isTurnEvent) throws InvalidConnectionException, InvalidViewException, ParseException, IOException, InvalidDieException;

    void handleMVEvent(StopTurnEvent stopTurnEvent);


    //void handleMVEvent(PlacingDieEvent placedDieEvent) throws InvalidConnectionException, ParseException, InvalidViewException, IOException;

    void handleMVEvent(ChangeDieEvent changeDieEvent) throws InvalidConnectionException, ParseException, InvalidViewException, IOException, InvalidDieException;

    void handleMVEvent(ModifiedPlaceEvent modifiedPlaceEvent) throws InvalidConnectionException, ParseException, InvalidViewException, IOException, InvalidDieException;

    void handleMVEvent(IsNotYourTurn isNotYourTurn);

    void handleMVEvent(ChangedDieEvent changedDieEvent) throws InvalidDieException, InvalidConnectionException, ParseException, InvalidViewException, IOException;

    void handleMVEvent(MoveDieEvent moveDieEffect) throws InvalidDieException, InvalidConnectionException, ParseException, InvalidViewException, IOException;

    void handleMVEvent(WrongPlaceEvent wrongPlaceEvent) throws InvalidDieException, InvalidConnectionException, InvalidViewException, ParseException, IOException;

    void handleMVEvent(IncDecEvent incDecEvent) throws InvalidDieException, InvalidConnectionException, ParseException, InvalidViewException, IOException;

    void handleMVEvent(InvalidToolEvent invalidToolEvent);

    void handleMVEvent(PerformActionEvent performActionEvent) throws InvalidDieException, InvalidConnectionException, InvalidViewException, ParseException, IOException;

    void handleMVEvent(RetryToolEvent retryToolEvent) throws InvalidDieException, InvalidConnectionException, InvalidViewException, ParseException, IOException;

    void handleMVEvent(RollingDiceEvent rollDiceEvent) throws InvalidDieException, InvalidConnectionException, ParseException, InvalidViewException, IOException;

    void handleMVEvent(SetDieEvent setDieEvent) throws InvalidDieException, InvalidConnectionException, ParseException, InvalidViewException, IOException;

    void handleMVEvent(SwapDieEvent swapDieEvent) throws InvalidDieException, InvalidConnectionException, ParseException, InvalidViewException, IOException;

    void handleMVEvent(MiniMenuEvent miniMenuEvent) throws InvalidDieException, InvalidConnectionException, InvalidViewException, ParseException, IOException;

    void handleMVEvent(DoublePlaceEvent doublePlaceEvent) throws InvalidDieException, InvalidConnectionException, ParseException, InvalidViewException, IOException;

    void handleMVEvent(EndGameEvent endGameEvent);
}

