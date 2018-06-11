package it.polimi.se2018.Network.server;

import it.polimi.se2018.Controller.EventsController;
import it.polimi.se2018.Network.LobbyController;
import it.polimi.se2018.Network.server.rmi.RMIServer;
import it.polimi.se2018.Network.server.socket.SocketServer;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

public class Server {

    private static final Integer socketPort = 10000;
    private static final Integer rmiPort = 1099;

    private static SocketServer socketServer;
    private static RMIServer rmiServer;

    private static LobbyController lobbyController;
    private static VirtualView virtualView;
    private static EventsController eventsController;

    public static void main(String[] args) throws RemoteException {
        virtualView = new VirtualView();
        eventsController = new EventsController(rmiServer,socketServer,virtualView);
        lobbyController = eventsController.getLobbyController();
        virtualView.registerObserver(eventsController);


        //RMI SERVERRRRRRRRRRRRRRRRRRRRRRRRRRRRR
        try {
            LocateRegistry.createRegistry(rmiPort);
        } catch (RemoteException e) {

            System.out.println("Registry già presente!");
        }

        try {
            rmiServer = new RMIServer(lobbyController, virtualView);
            Naming.rebind("//localhost/MyServer", rmiServer);


        } catch (MalformedURLException e) {
            System.err.println("Impossibile registrare l'oggetto indicato!");
        } catch (RemoteException e) {
            System.err.println("Errore di connessione: " + e.getMessage() + "!");
        }

        //SOCKET SERVERRRRRRRRRRRRRRRRRRRRRRRRRRRRR
        socketServer = new SocketServer(socketPort, lobbyController);

        //lobbyController.addServers(rmiServer, socketServer);


    }

}