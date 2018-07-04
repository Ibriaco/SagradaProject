package it.polimi.se2018.network.client.rmi;

import it.polimi.se2018.model.InvalidConnectionException;
import it.polimi.se2018.model.InvalidDieException;
import it.polimi.se2018.model.InvalidViewException;
import it.polimi.se2018.model.event.MVEvent;
import it.polimi.se2018.network.client.ClientInterface;
import it.polimi.se2018.org.json.simple.parser.ParseException;
import it.polimi.se2018.view.viewevents.VCEvent;

import java.io.IOException;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Interface that will be implemented by RMI Client
 */
public interface RMIClientInterface extends Remote, ClientInterface {

    void ping() throws RemoteException;
    void sendMVEvent (MVEvent event) throws IOException, InvalidConnectionException, InvalidViewException, ParseException;

    void sendEvent(VCEvent event) throws IOException, InvalidConnectionException, InvalidViewException, ParseException, InvalidDieException, ParseException;
}
