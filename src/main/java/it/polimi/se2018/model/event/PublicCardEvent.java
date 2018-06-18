package it.polimi.se2018.model.event;

import it.polimi.se2018.model.InvalidConnectionException;
import it.polimi.se2018.model.InvalidViewException;
import it.polimi.se2018.view.ui.ViewInterface;

import java.rmi.RemoteException;
import java.util.List;

public class PublicCardEvent implements MVEvent {

    private List<String> publicName;
    private String username;

    public PublicCardEvent(String username, List<String> publicName){
        this.username = username;
        this.publicName = publicName;
    }

    @Override
    public void accept(ViewInterface vi) throws RemoteException, InvalidConnectionException, InvalidViewException {
        vi.handleMVEvent(this);
    }

    @Override
    public String getUsername() {
        return username;
    }

    public void printPublicName(){
        for (String s: publicName) {
            System.out.println(s);
        }
    }
}
