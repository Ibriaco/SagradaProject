package it.polimi.se2018.Model.Event;

import it.polimi.se2018.Model.InvalidConnectionException;
import it.polimi.se2018.Model.InvalidViewException;
import it.polimi.se2018.Model.WindowCardAssociationException;
import it.polimi.se2018.View.UI.ViewInterface;

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
    public void accept(ViewInterface vi) throws RemoteException, InvalidConnectionException, WindowCardAssociationException, InvalidViewException {
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
