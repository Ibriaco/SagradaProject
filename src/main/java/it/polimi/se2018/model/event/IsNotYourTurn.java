package it.polimi.se2018.model.event;

import it.polimi.se2018.model.InvalidConnectionException;
import it.polimi.se2018.model.InvalidDieException;
import it.polimi.se2018.model.InvalidViewException;
import it.polimi.se2018.view.ui.ViewInterface;
import org.json.simple.parser.ParseException;

import java.io.IOException;

public class IsNotYourTurn implements MVEvent {
    private String username;

    public IsNotYourTurn(String username){
        this.username = username;
    }

    @Override
    public void accept(ViewInterface vi) throws IOException, InvalidConnectionException, InvalidViewException, ParseException, InvalidDieException {
        vi.handleMVEvent(this);
    }

    @Override
    public String getUsername() {
        return username;
    }

    public void printMessage(){
        System.out.println("IS NOT YOUR TURN FAGGOT!");
    }
}
