package it.polimi.se2018.Server.Network;

import it.polimi.se2018.Server.Network.RMI.RMIServer;
import it.polimi.se2018.Server.Network.Socket.SocketServer;

public class Server {

    private static final Integer socketPort = 10000;
    private static final Integer rmiPort = 10001;

    private static Server serverInstance;

    private static SocketServer socketServer;
    private static RMIServer rmiServer;

    private static Lobby lobby;

    public Server() {

        socketServer = new SocketServer(socketPort);
        rmiServer = new RMIServer(rmiPort);
        lobby = new Lobby();
    }

}