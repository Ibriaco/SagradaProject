/*package it.polimi.se2018.Client.View.Network;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.Scanner;

public class Client {
    private static int n = 0;
    private static Scanner reader = null;
    private static ClientRMIConnection clientRMIConnection;
    private static ClientSocketConnection clientSocketConnection;
    private static String host;
    private static int port;

    //main
    public static void main (String args[]) throws RemoteException {
        while (n!=1 && n!=2){
            reader = new Scanner(System.in);
            System.out.println("Selezionare connessione che si desidera utilizzare: " +
                    "1-RMI " +
                    "12-SOCKET");
            n = (reader.nextInt());
        }
        System.out.println("inserire nome host:");
        host = reader.nextLine();
        System.out.println("inserire porta:");
        port = reader.nextInt();
        reader.close();
        try {
            createConnection(n);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
    //scelgo connessione e creo
    public static void createConnection(int connection) throws RemoteException {
        if (connection == 1){
            clientRMIConnection =  new ClientRMIConnection(port);
            System.out.println("client RMI creato");

        }
        else if (connection == 2){
            clientSocketConnection = new ClientSocketConnection(host, port);
            System.out.println("client Socket creato");

        }
    }
}*/