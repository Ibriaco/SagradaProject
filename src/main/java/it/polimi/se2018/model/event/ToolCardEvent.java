package it.polimi.se2018.model.event;

import it.polimi.se2018.model.InvalidConnectionException;
import it.polimi.se2018.model.InvalidViewException;
import it.polimi.se2018.view.ui.ViewInterface;

import java.rmi.RemoteException;
import java.util.List;

public class ToolCardEvent implements MVEvent {
    private String username;
    private List<String> toolCards;
    private List<String> toolCardsDesc;

    public ToolCardEvent(String username, List<String> toolCardName, List<String> toolCardDesc) {
        this.username = username;
        this.toolCards = toolCardName;
        this.toolCardsDesc = toolCardDesc;
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
        System.out.println("\t\t\t\t TOOL CARDS");
        for (int i=0; i< toolCards.size();i++) {
            System.out.println("TITLE: "+ toolCards.get(i) + "\t\tDESCRIPTION: " + toolCardsDesc.get(i));
        }
    }

    public List<String> getToolCards() {
        return toolCards;
    }

    public List<String> getToolCardsDesc() {
        return toolCardsDesc;
    }
}
