package it.polimi.se2018.Network.server;

import it.polimi.se2018.Model.InvalidConnectionException;
import it.polimi.se2018.Model.InvalidViewException;
import it.polimi.se2018.Model.WindowCardAssociationException;
import it.polimi.se2018.Network.client.ClientInterface;
import it.polimi.se2018.View.ViewEvents.VCEvent;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.rmi.RemoteException;

/**
 * Interface that will be implemented by rmi/socket server
 */
public interface ServerInterface{


    //void removeClient(RMIClientInterface client) throws RemoteException;

    //void send(Message message) throws RemoteException;

    void vceTransport(VCEvent event) throws IOException, InvalidConnectionException, InvalidViewException, WindowCardAssociationException, ParseException;
    void sendUser(ClientInterface client) throws RemoteException, InvalidConnectionException, InvalidViewException;
   // void startGame() throws RemoteException, InvalidConnectionException, InvalidViewException, WindowCardAssociationException;

    //void sendPrivateObjective(VCEvent event) throws RemoteException;

}
