package it.polimi.se2018.network.server;

import it.polimi.se2018.ServerParser;
import it.polimi.se2018.controller.EventsController;
import it.polimi.se2018.controller.LobbyController;
import it.polimi.se2018.model.InvalidConnectionException;
import it.polimi.se2018.model.InvalidViewException;
import it.polimi.se2018.network.server.rmi.RMIServer;
import it.polimi.se2018.network.server.socket.SocketServer;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

import static it.polimi.se2018.ServerConfig.*;

/**
 * Mai server class that creates a rmi/socket server
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
        ServerParser sp = new ServerParser();
        sp.reader();
        virtualView = new VirtualView();
        eventsController = new EventsController(virtualView);

        virtualView.registerObserver(eventsController);
        eventsController.registerObserver(virtualView);


        //rmi SERVERRRRRRRRRRRRRRRRRRRRRRRRRRRRR
        try {
            LocateRegistry.createRegistry(RMI_PORT);
        } catch (RemoteException e) {

            System.out.println("Registry già presente!");
        }

        try {
            rmiServer = new RMIServer(virtualView);
            Naming.rebind(LOCAL_HOST, rmiServer);


        } catch (MalformedURLException e) {
            System.err.println("Impossibile registrare l'oggetto indicato!");
        } catch (RemoteException e) {
            System.err.println("Errore di connessione: " + e.getMessage() + "!");
        }

        //SOCKET SERVERRRRRRRRRRRRRRRRRRRRRRRRRRRRR
        socketServer = new SocketServer(SOCKET_PORT, virtualView);

        //lobbyController.addServers(rmiServer, socketServer);

        try {
            System.out.println(InetAddress.getLocalHost().getHostAddress());
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

}