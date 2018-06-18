package it.polimi.se2018.Model.Event;

import it.polimi.se2018.Model.InvalidConnectionException;
import it.polimi.se2018.Model.InvalidViewException;
import it.polimi.se2018.Model.WindowCardAssociationException;
import it.polimi.se2018.View.UI.ViewInterface;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public class ToolCardEvent implements MVEvent {
    private String username;
    private List<String> toolCards;

    public ToolCardEvent(String username, List<String> toolCards) {
        this.username = username;
        this.toolCards = toolCards;
    }

    @Override
    public void accept(ViewInterface vi) throws RemoteException, InvalidConnectionException, WindowCardAssociationException, InvalidViewException {
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
}
