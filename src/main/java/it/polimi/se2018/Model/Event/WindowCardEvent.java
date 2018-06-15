package it.polimi.se2018.Model.Event;

import it.polimi.se2018.Model.InvalidConnectionException;
import it.polimi.se2018.Model.InvalidViewException;
import it.polimi.se2018.Model.WindowCard;
import it.polimi.se2018.Model.WindowCardAssociationException;
import it.polimi.se2018.View.UI.ViewInterface;

import java.rmi.RemoteException;
import java.util.List;

public class WindowCardEvent implements MVEvent {
    private String username;
    private List<WindowCard> windowCards;


    public WindowCardEvent(String username, List<WindowCard> windowCards){
        this.username = username;
        this.windowCards = windowCards;
    }

    public List<WindowCard> getWindowCards() {
        return windowCards;
    }

    @Override
    public void accept(ViewInterface vi) throws RemoteException, InvalidConnectionException, WindowCardAssociationException, InvalidViewException {
        vi.handleMVEvent(this);
    }

    @Override
    public String getUsername() {
        return username;
    }
}
