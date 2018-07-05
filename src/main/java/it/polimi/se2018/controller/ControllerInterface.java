package it.polimi.se2018.controller;

import it.polimi.se2018.model.InvalidConnectionException;
import it.polimi.se2018.model.InvalidDieException;
import it.polimi.se2018.model.InvalidViewException;
import it.polimi.se2018.org.json.simple.parser.ParseException;
import it.polimi.se2018.view.viewevents.*;

import java.io.IOException;

public interface ControllerInterface {
    void handleVCEvent(LoginEvent event) throws InvalidConnectionException, IOException, InvalidViewException, ParseException;
    void handleVCEvent(PlaceDieEvent event) throws InvalidConnectionException, ParseException, InvalidViewException, IOException;
    void handleVCEvent(RollDiceEvent event) throws InvalidConnectionException, InvalidViewException, ParseException, IOException;
    void handleVCEvent(SkipTurnEvent event) throws InvalidConnectionException, ParseException, InvalidViewException, IOException;
    void handleVCEvent(SelectDieEvent event) throws InvalidConnectionException, InvalidViewException, ParseException, IOException, InvalidDieException;
    void handleVCEvent(UseToolEvent event) throws InvalidConnectionException, InvalidViewException, ParseException, IOException, InvalidDieException;
    void handleVCEvent(ChooseCardEvent event) throws InvalidConnectionException, IOException, InvalidViewException, ParseException;

    void handleVCEvent(PlaceModifiedDie placeModifiedDie);

    void handleVCEvent(MovingDieEvent movingDieEvent) throws InvalidConnectionException, InvalidViewException, ParseException, IOException;

    void handleVCEvent(IncrementDecrementDieEvent incrementDecrementDieEvent) throws InvalidDieException, InvalidConnectionException, InvalidViewException, ParseException, IOException;

    void handleVCEvent(UpdateDieEvent updateDieEvent) throws InvalidDieException;

    void handleVCEvent(SwappingDieEvent swappingDieEvent);

    void handleVCEvent(RemovedUser removedUserEvent) throws InvalidConnectionException, InvalidViewException, ParseException, IOException;
}
