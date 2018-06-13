package it.polimi.se2018.View.UI;


import it.polimi.se2018.Model.Event.LoggedUserEvent;
import it.polimi.se2018.Model.Event.MVEvent;
import it.polimi.se2018.Model.Event.SetupGameEvent;
import it.polimi.se2018.Model.InvalidConnectionException;
import it.polimi.se2018.Model.InvalidViewException;
import it.polimi.se2018.Model.WindowCardAssociationException;
import it.polimi.se2018.MyObservable;
import it.polimi.se2018.MyObserver;
import it.polimi.se2018.Network.Client.NetworkHandler;
import it.polimi.se2018.View.ViewEvents.*;

import java.rmi.RemoteException;
import java.util.ArrayList;

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
    public void update(MyObservable o, MVEvent arg) {

        arg.accept(this);
    }


    //METODI PER CREARE EVENTI VC
    public void createLoginEvent(int connectionType) throws InvalidConnectionException, RemoteException, InvalidViewException, WindowCardAssociationException {
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

    public void createChooseCardEvent(String username, String command) throws InvalidConnectionException, RemoteException, InvalidViewException, WindowCardAssociationException {
        String windowName = getString();
        vcEvent = new ChooseCardEvent(username, windowName);
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
    public void handleMVEvent (SetupGameEvent event){
         event.printPrivateName();
    }

}
