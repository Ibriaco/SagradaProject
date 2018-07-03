package it.polimi.se2018.model.event;

import it.polimi.se2018.model.InvalidConnectionException;
import it.polimi.se2018.model.InvalidViewException;
import it.polimi.se2018.view.ui.ViewInterface;

import java.rmi.RemoteException;
import java.util.List;

public class ToolCardEvent implements MVEvent {
    private String username;
    private List<String> toolCards;

    public ToolCardEvent(String username, List<String> toolCards) {
        this.username = username;
        this.toolCards = toolCards;
    }

    @Override
    public void accept(ViewInterface vi) throws RemoteException, InvalidConnectionException, InvalidViewException {
        vi.handleMVEvent(this);
    }

    @Override
    public String getUsername() {
        return username;
    }

    public void printToolCards(){
        for (String s: toolCards) {
            System.out.println(s);
        }
    }

    public List<String> getToolCards() {
        return toolCards;
    }
}
