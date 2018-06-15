package it.polimi.se2018.View.ViewEvents;

import it.polimi.se2018.Controller.ControllerInterface;
import it.polimi.se2018.Model.InvalidConnectionException;
import it.polimi.se2018.Model.InvalidViewException;
import it.polimi.se2018.Model.WindowCard;
import it.polimi.se2018.Model.WindowCardAssociationException;

import java.rmi.RemoteException;

/**
 * Event that returns the number and the side of a chosen Card by the player
 * @author Ibrahim El Shemy
 * @author Marco Gasperini
 */
public class ChooseCardEvent implements VCEvent {
    private WindowCard windowCard;
    private String username;

    /**
     *
     * @param username username of the current player.
     * @param windowCard refers to the name of the Card
     */
    public ChooseCardEvent(String username,WindowCard windowCard) {

        this.username = username;
        this.windowCard = windowCard;
    }

    public WindowCard getWindowCard() {
        return windowCard;
    }

    @Override
    public void accept(ControllerInterface controller) throws RemoteException, InvalidConnectionException, WindowCardAssociationException, InvalidViewException {
        controller.handleVCEvent(this);
    }

    public String getUsername(){
        return username;
    }
}
