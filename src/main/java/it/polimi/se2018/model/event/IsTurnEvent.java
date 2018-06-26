package it.polimi.se2018.model.event;

import it.polimi.se2018.model.InvalidConnectionException;
import it.polimi.se2018.model.InvalidViewException;
import it.polimi.se2018.view.ui.ViewInterface;

import java.io.IOException;
import java.rmi.RemoteException;

public class IsTurnEvent implements MVEvent {
    private String username;
    private boolean connected;

    public IsTurnEvent (String username, boolean connected){
        this.username = username;
        this.connected = connected;
    }


    @Override
    public void accept(ViewInterface vi) throws IOException, InvalidConnectionException, InvalidViewException, org.json.simple.parser.ParseException {
        vi.handleMVEvent(this);
    }

    @Override
    public String getUsername() {
        return username;
    }

    public boolean isConnected() {
        return connected;
    }
}
