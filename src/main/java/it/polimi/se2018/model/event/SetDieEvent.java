package it.polimi.se2018.model.event;

import it.polimi.se2018.model.Die;
import it.polimi.se2018.model.InvalidConnectionException;
import it.polimi.se2018.model.InvalidDieException;
import it.polimi.se2018.model.InvalidViewException;
import it.polimi.se2018.org.json.simple.parser.ParseException;
import it.polimi.se2018.view.ui.ViewInterface;

import java.io.IOException;

public class SetDieEvent implements MVEvent {

    private String username;
    private Die die;
    private int pos;

    public SetDieEvent(String user, Die d, int pos) {
        this.username = user;
        this.die = d;
        this.pos = pos;
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
        return die;
    }

    public int getPos(){
        return pos;
    }
}
