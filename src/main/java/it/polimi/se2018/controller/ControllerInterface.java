package it.polimi.se2018.controller;

import it.polimi.se2018.model.InvalidConnectionException;
import it.polimi.se2018.model.InvalidViewException;
import it.polimi.se2018.view.viewevents.*;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.rmi.RemoteException;

public interface ControllerInterface {
    void handleVCEvent(LoginEvent event) throws InvalidConnectionException, RemoteException, InvalidViewException;
    void handleVCEvent(PlaceDieEvent event);
    void handleVCEvent(RollDiceEvent event);
    void handleVCEvent(SkipTurnEvent event);
    void handleVCEvent(SelectDieEvent event);
    void handleVCEvent(UseToolEvent event);
    void handleVCEvent(ChooseCardEvent event) throws InvalidConnectionException, IOException, InvalidViewException, ParseException;
}
