package it.polimi.se2018.model.event;

import it.polimi.se2018.model.Die;
import it.polimi.se2018.model.InvalidConnectionException;
import it.polimi.se2018.model.InvalidDieException;
import it.polimi.se2018.model.InvalidViewException;
import it.polimi.se2018.org.json.simple.parser.ParseException;
import it.polimi.se2018.view.ui.ViewInterface;

import java.io.IOException;

public class ChangedDieEvent implements MVEvent {

    private String username;
    private Die d;

    public ChangedDieEvent(String user, Die die){
        this.username = user;
        this.d = die;
    }

    @Override
    public void accept(ViewInterface vi) throws IOException, InvalidConnectionException, InvalidViewException, ParseException, InvalidDieException {
        vi.handleMVEvent(this);
    }

    @Override
    public String getUsername() {
        return username;
    }

    public Die getDie(){
        return d;
    }
}
