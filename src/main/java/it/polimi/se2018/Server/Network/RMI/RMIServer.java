package it.polimi.se2018.Server.Network.RMI;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class RMIServer implements RMIServerInterface {
    private static Registry registry;
    private List<RMIConnection> rmiConnections;

    @Override
    public void sendMessage(String s) throws RemoteException {
        System.out.println(s);
    }

    @Override
    public String getMessage(String text) throws RemoteException {
        return "Your message is: " + text;
    }


    public RMIServer(int port){

        try {
            registry = LocateRegistry.createRegistry(port);

        } catch (RemoteException e) {
            System.out.println("Registry già presente!");
        }


        if (registry != null) {
            try {
                registry.rebind("RMIServer", this);
                UnicastRemoteObject.exportObject(this, port);
                rmiConnections = new ArrayList<>();
            } catch (RemoteException e) {
            }
        }
    }

}