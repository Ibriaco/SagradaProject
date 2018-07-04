package it.polimi.se2018.view.viewevents;

import it.polimi.se2018.controller.ControllerInterface;
import it.polimi.se2018.model.InvalidConnectionException;
import it.polimi.se2018.model.InvalidDieException;
import it.polimi.se2018.model.InvalidViewException;
import it.polimi.se2018.org.json.simple.parser.ParseException;


import java.io.IOException;
import java.io.Serializable;

/**
 * Class of the events that occur between ViewInterface and controller.
 * @author Ibrahim El Shemy
 * @author Marco Gasperini
 */
public interface VCEvent extends Serializable {

    static final long serialVersionUID = 42L;
    void accept(ControllerInterface controller) throws IOException, InvalidConnectionException, InvalidViewException, ParseException, InvalidDieException;
    String getUsername();
}
