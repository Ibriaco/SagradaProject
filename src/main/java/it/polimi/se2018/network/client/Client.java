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

import static it.polimi.se2018.ClientConfig.*;
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
                consoleErrorWriter.println(INVALID_INPUT);
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
        printOnConsole(SELECT_UI);
        printOnConsole(CLI_UI);
        printOnConsole(GUI_UI);
        return scanner.nextLine();
    }


}

