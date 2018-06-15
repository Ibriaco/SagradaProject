package it.polimi.se2018.View.UI;


import it.polimi.se2018.Model.*;
import it.polimi.se2018.Model.Event.*;
import it.polimi.se2018.MyObservable;
import it.polimi.se2018.MyObserver;
import it.polimi.se2018.Network.Client.NetworkHandler;
import it.polimi.se2018.View.ViewEvents.*;
import it.polimi.se2018.View.ViewEvents.RollDiceEvent;

import java.rmi.RemoteException;
import java.util.ArrayList;

import static it.polimi.se2018.App.*;
import static it.polimi.se2018.View.UI.CLIUtils.*;

/**
 * Command line interface
 * @author Ibrahim El Shemy
 * @author Marco Gasperini
 */
public class CLIView implements ViewInterface {

    private NetworkHandler nh;
    private VCEvent vcEvent;
    private int choice;
    private String user;
    private ArrayList<MyObserver> observersCollection = new ArrayList<>();

    /**
     * Updates a window card
     */
    @Override
    public void updateWindowCard() {

    }

    /**
     * Shows the user interface
     * @throws RemoteException thrown exception
     * @throws InvalidConnectionException thrown exception
     * @throws InvalidViewException thrown exception
     */
    @Override
    public void showUI() throws RemoteException, InvalidConnectionException, InvalidViewException, WindowCardAssociationException {

        boolean validInput = false;
        while(!validInput) {
            choice = printConnectionChoice();
            if (choice == 1 || choice == 2) {
                nh = new NetworkHandler(choice);
                registerObserver(nh);
                nh.registerObserver(this);
                validInput = true;
                loginScreen();
            } else
                consoleErrorWriter.println("Invalid input, please try again!");
        }
    }

    /**
     * Shows the login interface to the user
     * @throws RemoteException thrown exception
     * @throws InvalidConnectionException thrown exception
     * @throws InvalidViewException thrown exception
     */
     public void loginScreen() throws RemoteException, InvalidConnectionException, InvalidViewException, WindowCardAssociationException {
        printOnConsole("~~~~~~~~~~ Login page ~~~~~~~~~~");
        printOnConsole("Insert your username here: ");
        user = consoleScanner.next();
        createLoginEvent(choice);
    }


    @Override
    public void receiveEvent(VCEvent event) {

    }

    private int printConnectionChoice() {
        printOnConsole("Select the Connection type you want to use:");
        printOnConsole("1) " + rmi);
        printOnConsole("2) " + socket);
        return consoleScanner.nextInt();
    }

    @Override
    public void registerObserver(MyObserver observer) {
        observersCollection.add(observer);
    }

    @Override
    public void unregisterObserver(MyObserver observer) {
        observersCollection.remove(observer);
    }

    @Override
    public void notifyObservers() throws RemoteException, InvalidConnectionException, InvalidViewException, WindowCardAssociationException {
        for (MyObserver o : observersCollection) {
            o.update(this, vcEvent);
        }
    }

    @Override
    public void update(MyObservable o, VCEvent arg) throws RemoteException, InvalidConnectionException, InvalidViewException {

    }

    @Override
    public void update(MyObservable o, MVEvent arg) throws RemoteException, InvalidConnectionException, WindowCardAssociationException, InvalidViewException {

        arg.accept(this);
    }


    //METODI PER CREARE EVENTI VC
    private void createLoginEvent(int connectionType) throws InvalidConnectionException, RemoteException, InvalidViewException, WindowCardAssociationException {
        vcEvent = new LoginEvent(user);
        notifyObservers();
    }

    public void createPlaceDieEvent() throws InvalidConnectionException, RemoteException, InvalidViewException, WindowCardAssociationException {
        int pos = getNumber();
        int coordX = getNumber();
        int coordY = getNumber();
        vcEvent = new PlaceDieEvent(user, pos, coordX, coordY);
        notifyObservers();
    }

    public void createSkipTurnEvent() throws InvalidConnectionException, RemoteException, InvalidViewException, WindowCardAssociationException {
        vcEvent = new SkipTurnEvent(user);
        notifyObservers();
    }

    public void createUseToolEvent() throws InvalidConnectionException, RemoteException, InvalidViewException, WindowCardAssociationException {
        int pos = getNumber()-1;
        vcEvent = new UseToolEvent(user, pos);
        notifyObservers();
    }

    public void createRollDiceEvent() throws InvalidConnectionException, RemoteException, InvalidViewException, WindowCardAssociationException {
        vcEvent = new RollDiceEvent(user);
        notifyObservers();
    }

    private void createChooseCardEvent(WindowCard windowCard) throws InvalidConnectionException, RemoteException, InvalidViewException, WindowCardAssociationException {
        vcEvent = new ChooseCardEvent(user, windowCard);
        notifyObservers();
    }

    public String getString() {

        return consoleScanner.nextLine();
    }

    public int getNumber() {

        return consoleScanner.nextInt();
    }

    //METODI PER GESTIRE MVEVENT
    @Override
    public void handleMVEvent(LoggedUserEvent event) {
        event.printState();
    }

    @Override
    public void handleMVEvent (PrivateCardEvent event){
         event.printPrivateName();
    }


    @Override
    public void handleMVEvent(WindowCardEvent event) throws RemoteException, InvalidConnectionException, WindowCardAssociationException, InvalidViewException {
        Color color;
        int value;
        String windowName = "";
        boolean correctName = false;
        consoleWriter.println("[WINDOW CARD]\n");
        for (WindowCard w: event.getWindowCards()) {
            consoleWriter.println(w.getWindowName() + " " + w.getDifficulty());
            for(int i=0; i<4; i++){
                for(int j=0; j<5; j++){
                    color = w.getGridCell(i, j).getColor();
                    value = w.getGridCell(i, j).getShade();
                    printCell(color, value);
                }
                consoleWriter.println("");
            }
        }

        consoleWriter.println("INSERT THE NAME OF WINDOW CARD YOU WANT CHOOSE");
        while (!correctName) {
            windowName = getString();
            for (WindowCard w: event.getWindowCards()) {
                if (w.getWindowName().equals(windowName)) {
                    correctName = true;
                    createChooseCardEvent(w);
                }
            }
            consoleWriter.println("NOT-EXISTENT WINDOW CARD");
        }

    }

    @Override
    public void handleMVEvent(NewGameEvent event) {
        /*Color color;
        int value;
        int user=0;

        for (WindowCard w: event.getWindowCardList()) {
            consoleWriter.println(event.getUser().get(user));
            consoleWriter.println(w.getWindowName() + " " + w.getDifficulty());
            for(int i=0; i<4; i++){
                for(int j=0; j<5; j++){
                    color = w.getGridCell(i, j).getColor();
                    value = w.getGridCell(i, j).getShade();
                    printCell(color, value);
                }
                consoleWriter.println("");
                user++;
            }
        }*/
    }


    private void printCell(Color color, int value){
        String toPrint;
        String ok;
        if (value == 0) {
            toPrint = "\u25FC";
            if(color == null) {
                consoleWriter.print(toPrint);
            }
            else{
                switch (color) {
                    case BLUE:
                        consoleWriter.print(ANSI_BLUE + toPrint + ANSI_RESET);
                        break;
                    case RED:
                        consoleWriter.print(ANSI_RED + toPrint + ANSI_RESET);
                        break;
                    case GREEN:
                        consoleWriter.print(ANSI_GREEN + toPrint + ANSI_RESET);
                        break;
                    case YELLOW:
                        consoleWriter.print(ANSI_YELLOW + toPrint + ANSI_RESET);
                        break;
                    case PURPLE:
                        consoleWriter.print(ANSI_PURPLE + toPrint + ANSI_RESET);
                        break;
                    case WHITE:
                        consoleWriter.print(ANSI_WHITE + toPrint + ANSI_RESET);
                        break;
                    default:
                        break;
                }
            }
        } else {

            switch(value){
                case 1:
                    consoleWriter.print("\u2680");
                    break;
                case 2:
                    consoleWriter.print("\u2681");
                    break;
                case 3:
                    consoleWriter.print("\u2682");
                    break;
                case 4:
                    consoleWriter.print("\u2683");
                    break;
                case 5:
                    consoleWriter.print("\u2684");
                    break;
                case 6:
                    consoleWriter.print("\u2685");
                    break;
            }
        }
    }
}


