package it.polimi.se2018.Server.Network;


import it.polimi.se2018.Message;
import it.polimi.se2018.Server.Network.RMI.RMIClient;
import it.polimi.se2018.Server.Network.RMI.RMIClientInterface;
import it.polimi.se2018.Server.Network.RMI.RMIServerInterface;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        RMIServerInterface server;
        try {
            server = (RMIServerInterface) Naming.lookup("//localhost/MyServer");

            RMIClient client = new RMIClient();
            RMIClientInterface remoteRef = (RMIClientInterface) UnicastRemoteObject.exportObject(client, 0);

            Scanner scanner = new Scanner(System.in);
            System.out.println("Inserire username:");
            String username = scanner.next();
            System.out.println("Inserire password:");
            String password = scanner.next();

            boolean status = client.authenticate(username, password);


            if (status) {
                server.addClient(remoteRef);
                System.out.println("Login effettuato con successo!");
            }
            else
                System.out.println("Accesso negato, riprova!");

            boolean loop = true;
            while(loop){

                System.out.println("Inserisci un messaggio!");
                String text = scanner.next();


                Message message = new Message(text);

                server.send(message);
            }
            scanner.close();

        } catch (MalformedURLException e) {
            System.err.println("URL non trovato!");
        } catch (RemoteException e) {
            System.err.println("Errore di connessione: " + e.getMessage() + "!");
        } catch (NotBoundException e) {
            System.err.println("Il riferimento passato non è associato a nulla!");
        }


    }

}

