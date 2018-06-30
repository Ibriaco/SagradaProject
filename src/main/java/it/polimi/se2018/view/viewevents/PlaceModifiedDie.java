package it.polimi.se2018.view.viewevents;

import it.polimi.se2018.controller.ControllerInterface;
import it.polimi.se2018.model.InvalidConnectionException;
import it.polimi.se2018.model.InvalidViewException;
import org.json.simple.parser.ParseException;

import java.io.IOException;

public class PlaceModifiedDie implements VCEvent {

    private String username;
    private int x;
    private int y;
    private int pos;

    public PlaceModifiedDie(String username, int pos, int x, int y){
        this.username = username;
    }

    @Override
    public void accept(ControllerInterface controller) throws IOException, InvalidConnectionException, InvalidViewException, ParseException {
        controller.handleVCEvent(this);
    }

    @Override
    public String getUsername() {
        return username;
    }

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }

    public int getPos(){return pos;}
}
