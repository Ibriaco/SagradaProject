package it.polimi.se2018.Network;

import it.polimi.se2018.Network.RMI.RMIServer;
import it.polimi.se2018.Network.Socket.SocketServer;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

public class Server {

    private static final Integer socketPort = 10000;
    private static final Integer rmiPort = 1099;

    private static SocketServer socketServer;
    private static RMIServer rmiServer;

    private static Lobby lobby;

    public static void main(String[] args) {

        LobbyController lobbyController = new LobbyController();

        try {

            LocateRegistry.createRegistry(rmiPort);

        } catch (RemoteException e) {

            System.out.println("Registry già presente!");
        }

        try {
            RMIServer rmiServer = new RMIServer(lobbyController);
            Naming.rebind("//localhost/MyServer", rmiServer);


        } catch (MalformedURLException e) {
            System.err.println("Impossibile registrare l'oggetto indicato!");
        } catch (RemoteException e) {
            System.err.println("Errore di connessione: " + e.getMessage() + "!");
        }

    }

}