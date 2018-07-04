package it.polimi.se2018;

import it.polimi.se2018.model.InvalidDieException;
import it.polimi.se2018.model.event.MVEvent;
import it.polimi.se2018.model.InvalidConnectionException;
import it.polimi.se2018.model.InvalidViewException;
import it.polimi.se2018.org.json.simple.parser.ParseException;
import it.polimi.se2018.view.viewevents.VCEvent;

import java.io.IOException;

public interface MyObserver {

    void update(MyObservable o, VCEvent arg) throws IOException, InvalidConnectionException, InvalidViewException, ParseException, InvalidDieException;
    void update(MyObservable o, MVEvent arg) throws IOException, InvalidConnectionException, InvalidViewException, ParseException, InvalidDieException;

}
