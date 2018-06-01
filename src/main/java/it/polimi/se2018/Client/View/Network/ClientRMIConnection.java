package it.polimi.se2018.Client.View.Network;

import it.polimi.se2018.Server.Network.RMI.RMIServerInterface;

import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class ClientRMIConnection extends UnicastRemoteObject implements ClientConnection {

    private RMIServerInterface server = null;
    // in text ci salvo ciò che l'utente vuole mandare
    String text = "Ciao";

    //lookup
    public ClientRMIConnection(String serverIP, Integer port) throws RemoteException {
        super();

        try {
            Registry registry = LocateRegistry.getRegistry(serverIP, port);
            server = (RMIServerInterface) registry.lookup("RMIServer");
            UnicastRemoteObject.exportObject(this, 0);
            System.out.println("Connected to server!");
        } catch (NotBoundException e) {
            e.printStackTrace();
        } catch (AccessException e) {
            e.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
        }


        if (server != null) {
            try {
                server.sendMessage(text);
                System.out.println(server.getMessage(text));
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    //metodi per mandare al server e ricevere dal server
    @Override
    public void sendMessage(String s) throws RemoteException {
        System.out.println(s);
    }

    @Override
    public String getMessage(String text) throws RemoteException {
        return "Your message is: " + text;
    }




}
