package it.polimi.se2018.network.server;

import it.polimi.se2018.model.InvalidConnectionException;
import it.polimi.se2018.model.InvalidDieException;
import it.polimi.se2018.model.InvalidViewException;
import it.polimi.se2018.network.client.ClientInterface;
import it.polimi.se2018.view.viewevents.VCEvent;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.rmi.RemoteException;

/**
 * Interface that will be implemented by rmi/socket server
 */
public interface ServerInterface{

    void vceTransport(VCEvent event) throws IOException, InvalidViewException, ParseException, InvalidConnectionException, InvalidDieException;
    void sendUser(ClientInterface client) throws RemoteException, InvalidViewException;
}
