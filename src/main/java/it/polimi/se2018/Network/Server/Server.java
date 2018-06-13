package it.polimi.se2018.Network.Server;

import it.polimi.se2018.Controller.EventsController;
import it.polimi.se2018.Model.InvalidConnectionException;
import it.polimi.se2018.Model.InvalidViewException;
import it.polimi.se2018.Controller.LobbyController;
import it.polimi.se2018.Network.Server.RMI.RMIServer;
import it.polimi.se2018.Network.Server.Socket.SocketServer;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

/**
 * Mai Server class that creates a RMI/Socket Server
 * @author Ibrahim El Shemy
 * @author Gregorio Galletti
 */
public class Server {

    private static final Integer socketPort = 10000;
    private static final Integer rmiPort = 1099;

    private static SocketServer socketServer;
    private static RMIServer rmiServer;

    private static LobbyController lobbyController;
    private static VirtualView virtualView;
    private static EventsController eventsController;

    public static void main(String[] args) throws RemoteException, InvalidConnectionException, InvalidViewException {

        virtualView = new VirtualView();
        eventsController = new EventsController(virtualView);

        virtualView.registerObserver(eventsController);
        eventsController.registerObserver(virtualView);


        //RMI SERVERRRRRRRRRRRRRRRRRRRRRRRRRRRRR
        try {
            LocateRegistry.createRegistry(rmiPort);
        } catch (RemoteException e) {

            System.out.println("Registry già presente!");
        }

        try {
            rmiServer = new RMIServer(virtualView);
            Naming.rebind("//localhost/MyServer", rmiServer);


        } catch (MalformedURLException e) {
            System.err.println("Impossibile registrare l'oggetto indicato!");
        } catch (RemoteException e) {
            System.err.println("Errore di connessione: " + e.getMessage() + "!");
        }

        //SOCKET SERVERRRRRRRRRRRRRRRRRRRRRRRRRRRRR
        socketServer = new SocketServer(socketPort, virtualView);

        //lobbyController.addServers(rmiServer, socketServer);


    }

}