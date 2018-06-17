package it.polimi.se2018.Network.Client;

import it.polimi.se2018.Model.Event.MVEvent;
import it.polimi.se2018.Model.InvalidConnectionException;
import it.polimi.se2018.Model.InvalidViewException;
import it.polimi.se2018.Model.WindowCardAssociationException;
import it.polimi.se2018.MyObservable;
import it.polimi.se2018.MyObserver;
import it.polimi.se2018.View.ViewEvents.VCEvent;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.rmi.RemoteException;

/**
 * Interface that will be implemented by RMIClient/SocketClient
 */
public interface ClientInterface extends MyObservable, MyObserver {

    void sendMVEvent (MVEvent event) throws RemoteException, InvalidConnectionException, InvalidViewException, WindowCardAssociationException;

    void sendEvent(VCEvent event) throws IOException, InvalidConnectionException, InvalidViewException, WindowCardAssociationException, ParseException;
}
