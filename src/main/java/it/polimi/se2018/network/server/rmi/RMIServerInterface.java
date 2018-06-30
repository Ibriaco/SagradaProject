package it.polimi.se2018.network.server.rmi;

import it.polimi.se2018.model.InvalidConnectionException;
import it.polimi.se2018.model.InvalidDieException;
import it.polimi.se2018.model.InvalidViewException;
import it.polimi.se2018.network.client.ClientInterface;
import it.polimi.se2018.network.server.ServerInterface;
import it.polimi.se2018.view.viewevents.VCEvent;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Interface that refers to the connection (server side) using rmi
 */
public interface RMIServerInterface extends Remote, ServerInterface {
    void sendUser(ClientInterface client) throws RemoteException, InvalidViewException;
    void vceTransport(VCEvent event) throws IOException, InvalidViewException, ParseException, InvalidConnectionException, InvalidDieException;

    void broadcast() throws RemoteException;
}
