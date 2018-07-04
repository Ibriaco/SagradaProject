package it.polimi.se2018.model.event;

import it.polimi.se2018.model.InvalidConnectionException;
import it.polimi.se2018.model.InvalidViewException;
import it.polimi.se2018.org.json.simple.parser.ParseException;
import it.polimi.se2018.view.ui.ViewInterface;

import java.io.IOException;

public class StopTurnEvent implements  MVEvent {
    private String username;

    public StopTurnEvent(String username){
        this.username = username;
    }

    @Override
    public void accept(ViewInterface vi) throws IOException, InvalidConnectionException, InvalidViewException, ParseException {
        vi.handleMVEvent(this);
    }

    @Override
    public String getUsername() {
        return username;
    }

    public void printMessage(){
        System.out.println("TIMER HAS EXPIRED. YOUR TURN IS FINISHED AND YOUR INPUT IS DISABILITATED!");
    }
}
