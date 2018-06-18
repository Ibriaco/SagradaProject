package it.polimi.se2018.network.client;

import it.polimi.se2018.model.InvalidConnectionException;
import it.polimi.se2018.model.InvalidViewException;
import it.polimi.se2018.view.ui.CLIView;
import it.polimi.se2018.view.ui.GUIView;
import it.polimi.se2018.view.ui.ViewInterface;
import javafx.application.Application;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.Scanner;

import static it.polimi.se2018.view.ui.CLIUtils.*;

public class Client {

    private static ViewInterface vi;

    public static void main(String[] args) throws IOException, InvalidConnectionException, InvalidViewException, ParseException {

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

        System.out.println("Select the ui:");
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

