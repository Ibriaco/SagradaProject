package it.polimi.se2018.Controller;

import it.polimi.se2018.Model.InvalidConnectionException;
import it.polimi.se2018.Model.InvalidViewException;
import it.polimi.se2018.Model.WindowCardAssociationException;
import it.polimi.se2018.View.ViewEvents.*;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.rmi.RemoteException;

public interface ControllerInterface {
    void handleVCEvent(LoginEvent event) throws InvalidConnectionException, RemoteException, InvalidViewException, WindowCardAssociationException;
    void handleVCEvent(PlaceDieEvent event);
    void handleVCEvent(RollDiceEvent event);
    void handleVCEvent(SkipTurnEvent event);
    void handleVCEvent(SelectDieEvent event);
    void handleVCEvent(UseToolEvent event);
    void handleVCEvent(ChooseCardEvent event) throws InvalidConnectionException, IOException, InvalidViewException, WindowCardAssociationException, ParseException;
}
