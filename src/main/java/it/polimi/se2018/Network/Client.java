package it.polimi.se2018.Network;


import it.polimi.se2018.Network.RMI.RMIClient;
import it.polimi.se2018.Network.Socket.SocketClient;

import java.rmi.RemoteException;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String choice = "0";

        while (!(choice.equals("1") || choice.equals("2"))) {
            System.out.println("Inserisci la connessione");
            System.out.println("1) RMI");
            System.out.println("2) Socket");
            choice = scanner.next();
        }
        String username = loginScreen();

        if (choice.equals("1")) {
            try {
                RMIClient rc = new RMIClient(username);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        } else if (choice.equals("2")) {
            SocketClient sc = new SocketClient("localhost", 10000, username);

        }

    }


    private static String loginScreen(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Inserire username:");
        return scanner.next();

    }

}

