package it.polimi.se2018.model.event;

import it.polimi.se2018.model.InvalidConnectionException;
import it.polimi.se2018.model.InvalidViewException;
import it.polimi.se2018.view.ui.ViewInterface;

import java.rmi.RemoteException;
import java.util.List;

public class PublicCardEvent implements MVEvent {

    private List<String> publicName;
    private List<String> publicDesc;
    private List<String> publicScore;

    private String username;

    public PublicCardEvent(String username, List<String> publicCardName, List<String> publicCardDesc, List<String> publicCardScore){
        this.username = username;
        this.publicName = publicCardName;
        this.publicDesc = publicCardDesc;
        this.publicScore = publicCardScore;
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

    public List<String> getPublicName() {
        return publicName;
    }

    public List<String> getPublicDesc() {
        return publicDesc;
    }

    public List<String> getPublicScore() {
        return publicScore;
    }
}
