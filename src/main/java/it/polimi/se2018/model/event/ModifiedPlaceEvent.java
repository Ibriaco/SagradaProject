package it.polimi.se2018.model.event;

import it.polimi.se2018.model.InvalidConnectionException;
import it.polimi.se2018.model.InvalidDieException;
import it.polimi.se2018.model.InvalidViewException;
import it.polimi.se2018.org.json.simple.parser.ParseException;
import it.polimi.se2018.view.ui.ViewInterface;

import java.io.IOException;

public class ModifiedPlaceEvent implements MVEvent {

    private String user;
    private int pos;

    public ModifiedPlaceEvent(String user, int pos){
        this.user = user;
        this.pos = pos;
    }
    @Override
    public void accept(ViewInterface vi) throws IOException, InvalidConnectionException, InvalidViewException, ParseException, InvalidDieException {
        vi.handleMVEvent(this);
    }

    @Override
    public String getUsername() {
        return user;
    }

    public int getPos(){
        return pos;
    }

}
