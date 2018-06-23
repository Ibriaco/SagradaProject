package it.polimi.se2018.model.event;

import it.polimi.se2018.model.InvalidConnectionException;
import it.polimi.se2018.model.InvalidViewException;
import it.polimi.se2018.view.ui.ViewInterface;

import java.io.IOException;
import java.io.Serializable;
import java.rmi.RemoteException;

public interface MVEvent extends Serializable{

    void accept(ViewInterface vi) throws IOException, InvalidConnectionException, InvalidViewException, org.json.simple.parser.ParseException;

    String getUsername();
}
