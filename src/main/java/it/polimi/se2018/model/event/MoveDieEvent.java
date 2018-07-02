package it.polimi.se2018.model.event;

import it.polimi.se2018.model.InvalidConnectionException;
import it.polimi.se2018.model.InvalidDieException;
import it.polimi.se2018.model.InvalidViewException;
import it.polimi.se2018.view.ui.ViewInterface;
import org.json.simple.parser.ParseException;

import java.io.IOException;

public class MoveDieEvent implements MVEvent {

    private String username;

    public MoveDieEvent(String user){
        this.username = user;
    }

    @Override
    public void accept(ViewInterface vi) throws IOException, InvalidConnectionException, InvalidViewException, ParseException, InvalidDieException {
        vi.handleMVEvent(this);
    }

    @Override
    public String getUsername() {

        return username;
    }
}
