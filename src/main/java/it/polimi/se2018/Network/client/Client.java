package it.polimi.se2018.Network.client;


import it.polimi.se2018.Network.client.rmi.RMIClient;
import it.polimi.se2018.Network.client.socket.SocketClient;
import it.polimi.se2018.View.CLIView;
import it.polimi.se2018.View.GUIView;
import it.polimi.se2018.View.ViewInterface;

import java.rmi.RemoteException;
import java.util.Scanner;

import static it.polimi.se2018.View.CLIUtils.consoleErrorWriter;
import static it.polimi.se2018.View.CLIUtils.consoleScaner;
import static it.polimi.se2018.View.CLIUtils.printSplashArt;

public class Client {

    private static ViewInterface vi;

    public static void main(String[] args) {

        printSplashArt();

        boolean validInput = false;
        while(!validInput){
            int choice = printChoice();
            if(choice == 1){
                vi = new CLIView();
                validInput = true;
            }
            else if(choice == 2){
                vi = new GUIView();
                validInput = true;
            }
            else
                consoleErrorWriter.println("Invalid input, please try again!");
        }

        vi.showUI();


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

        boolean a = true;
        while(a){

        }

    }

    private static int printChoice() {

        System.out.println("Select the UI:");
        System.out.println("1) CLI");
        System.out.println("2) GUI");
        return consoleScaner.nextInt();
    }


    private static String loginScreen(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Inserire username:");
        return scanner.next();

    }

}

