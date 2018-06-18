package it.polimi.se2018.model.event;

import it.polimi.se2018.model.InvalidConnectionException;
import it.polimi.se2018.model.InvalidViewException;
import it.polimi.se2018.model.WindowCard;
import it.polimi.se2018.view.ui.ViewInterface;

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
    public void accept(ViewInterface vi) throws RemoteException, InvalidConnectionException, InvalidViewException {
        vi.handleMVEvent(this);
    }

    @Override
    public String getUsername() {
        return username;
    }
}
