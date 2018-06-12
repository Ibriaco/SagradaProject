package it.polimi.se2018.Network.Client;

import it.polimi.se2018.Model.InvalidConnectionException;
import it.polimi.se2018.Model.InvalidViewException;
import it.polimi.se2018.View.UI.CLIView;
import it.polimi.se2018.View.UI.GUIView;
import it.polimi.se2018.View.UI.ViewInterface;
import javafx.application.Application;

import java.rmi.RemoteException;
import java.util.Scanner;

import static it.polimi.se2018.View.UI.CLIUtils.consoleErrorWriter;
import static it.polimi.se2018.View.UI.CLIUtils.consoleScanner;
import static it.polimi.se2018.View.UI.CLIUtils.printSplashArt;

public class Client {

    private static ViewInterface vi;

    public static void main(String[] args) throws RemoteException, InvalidConnectionException, InvalidViewException {

        printSplashArt();
        int choice = 0;

        boolean validInput = false;
        while(!validInput){
            choice = printChoice();
            if(choice == 1 || choice == 2)
                validInput = true;
            else
                consoleErrorWriter.println("Invalid input, please try again!");
        }

        if(choice == 1){
            vi = new CLIView();
            vi.showUI();
        }
        else {
            vi = new GUIView();
            Application.launch(GUIView.class, args);
        }

    }

    private static int printChoice() {

        System.out.println("Select the UI:");
        System.out.println("1) CLI");
        System.out.println("2) GUI");
        return consoleScanner.nextInt();
    }


    private static String loginScreen(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Inserire username:");
        return scanner.next();

    }

}

