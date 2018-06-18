package it.polimi.se2018.network.client.rmi;

import it.polimi.se2018.model.event.MVEvent;
import it.polimi.se2018.model.InvalidConnectionException;
import it.polimi.se2018.model.InvalidViewException;
import it.polimi.se2018.network.client.ClientInterface;
import it.polimi.se2018.view.viewevents.VCEvent;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Interface that will be implemented by RMI Client
 */
public interface RMIClientInterface extends Remote, ClientInterface {

    void sendMVEvent (MVEvent event) throws RemoteException, InvalidConnectionException, InvalidViewException;

    void sendEvent(VCEvent event) throws IOException, InvalidConnectionException, InvalidViewException, ParseException;
}
