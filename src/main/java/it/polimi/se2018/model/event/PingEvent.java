package it.polimi.se2018.model.event;

import it.polimi.se2018.model.InvalidConnectionException;
import it.polimi.se2018.model.InvalidDieException;
import it.polimi.se2018.model.InvalidViewException;
import it.polimi.se2018.org.json.simple.parser.ParseException;
import it.polimi.se2018.view.ui.ViewInterface;

import java.io.IOException;

public class PingEvent implements MVEvent{
    public PingEvent() {
    }

    @Override
    public void accept(ViewInterface vi) throws IOException, InvalidConnectionException, InvalidViewException, ParseException, InvalidDieException {

    }

    @Override
    public String getUsername() {
        return null;
    }
}
