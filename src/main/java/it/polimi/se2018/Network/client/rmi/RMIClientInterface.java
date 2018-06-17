package it.polimi.se2018.Network.client.rmi;

import it.polimi.se2018.Model.Event.MVEvent;
import it.polimi.se2018.Model.InvalidConnectionException;
import it.polimi.se2018.Model.InvalidViewException;
import it.polimi.se2018.Model.WindowCardAssociationException;
import it.polimi.se2018.Network.client.ClientInterface;
import it.polimi.se2018.View.ViewEvents.VCEvent;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Interface that will be implemented by RMI Client
 */
public interface RMIClientInterface extends Remote, ClientInterface {

    void sendMVEvent (MVEvent event) throws RemoteException, InvalidConnectionException, InvalidViewException, WindowCardAssociationException;

    void sendEvent(VCEvent event) throws IOException, InvalidConnectionException, InvalidViewException, WindowCardAssociationException, ParseException;
}
