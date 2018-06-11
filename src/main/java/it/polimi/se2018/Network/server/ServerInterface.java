package it.polimi.se2018.Network.server;

import it.polimi.se2018.Model.InvalidConnectionException;
import it.polimi.se2018.Model.InvalidViewException;
import it.polimi.se2018.Network.LobbyController;
import it.polimi.se2018.Network.client.ClientInterface;
import it.polimi.se2018.View.ViewEvents.VCEvent;

import java.rmi.RemoteException;

public interface ServerInterface{


    //void removeClient(RMIClientInterface client) throws RemoteException;

    //void send(Message message) throws RemoteException;

    void vceTransport(VCEvent event) throws RemoteException, InvalidConnectionException, InvalidViewException;
    void sendUser(String username, ClientInterface client) throws RemoteException, InvalidConnectionException, InvalidViewException;
   // void startGame() throws RemoteException, InvalidConnectionException, InvalidViewException, WindowCardAssociationException;

    //void sendPrivateObjective(VCEvent event) throws RemoteException;

    LobbyController getLobbyController() throws RemoteException;
}
