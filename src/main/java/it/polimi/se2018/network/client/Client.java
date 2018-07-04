package it.polimi.se2018.network.client;

import it.polimi.se2018.ClientParser;
import it.polimi.se2018.model.InvalidConnectionException;
import it.polimi.se2018.model.InvalidDieException;
import it.polimi.se2018.model.InvalidViewException;
import it.polimi.se2018.org.json.simple.parser.ParseException;
import it.polimi.se2018.view.ui.CLIView;
import it.polimi.se2018.view.ui.GUIView;
import it.polimi.se2018.view.ui.ViewInterface;
import javafx.application.Application;

import java.io.IOException;
import java.util.Scanner;

import static it.polimi.se2018.view.ui.CLIUtils.*;

public class Client {

    private static ViewInterface vi;

    public static void main(String[] args) throws IOException, InvalidConnectionException, InvalidViewException, ParseException, InvalidDieException {
        ClientParser cp = new ClientParser();
        cp.reader();
        printSplashArt();
        String choice = null;

        boolean validInput = false;
        while(!validInput){
            choice = printChoice();
            if(choice.equals("1") || choice.equals("2"))
                validInput = true;
            else
                consoleErrorWriter.println("Invalid input, please try again!");
        }

        if(choice.equals("1")){
            vi = new CLIView();
            vi.showUI();
        }
        else {
            vi = new GUIView();
            Application.launch(GUIView.class, args);
        }

    }

    private static String printChoice() {
        Scanner scanner = new Scanner(System.in);
        printOnConsole("Select the ui:");
        printOnConsole("1) CLI");
        printOnConsole("2) GUI");
        return scanner.nextLine();
    }


    private static String loginScreen(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Inserire username:");
        return scanner.nextLine();

    }

}

