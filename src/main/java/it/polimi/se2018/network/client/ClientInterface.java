package it.polimi.se2018.network.client;

import it.polimi.se2018.model.InvalidDieException;
import it.polimi.se2018.model.event.MVEvent;
import it.polimi.se2018.model.InvalidConnectionException;
import it.polimi.se2018.model.InvalidViewException;
import it.polimi.se2018.MyObservable;
import it.polimi.se2018.MyObserver;
import it.polimi.se2018.view.viewevents.VCEvent;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.rmi.RemoteException;

/**
 * Interface that will be implemented by RMIClient/SocketClient
 */
public interface ClientInterface extends MyObservable, MyObserver {

    void sendMVEvent (MVEvent event) throws IOException, InvalidConnectionException, InvalidViewException, ParseException;

    void sendEvent(VCEvent event) throws IOException, InvalidConnectionException, InvalidViewException, ParseException, InvalidDieException;

    void ping() throws RemoteException;
}
