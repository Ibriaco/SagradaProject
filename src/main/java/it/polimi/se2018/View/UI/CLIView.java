package it.polimi.se2018.View.UI;


import it.polimi.se2018.Model.Event.MVEvent;
import it.polimi.se2018.Model.InvalidConnectionException;
import it.polimi.se2018.Model.InvalidViewException;
import it.polimi.se2018.MyObservable;
import it.polimi.se2018.MyObserver;
import it.polimi.se2018.Network.NetworkHandler;
import it.polimi.se2018.View.ViewEvents.LoginEvent;
import it.polimi.se2018.View.ViewEvents.VCEvent;

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
    private MVEvent mvEvent;
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
    public void showUI() throws RemoteException, InvalidConnectionException, InvalidViewException {

        boolean validInput = false;
        while(!validInput) {
            choice = printConnectionChoice();
            if (choice == 1 || choice == 2) {
                nh = new NetworkHandler(choice, this);
                registerObserver(nh);
                validInput = true;
            } else
                consoleErrorWriter.println("Invalid input, please try again!");
        }
            loginScreen();
    }

    /**
     * Shows the login interface to the user
     * @throws RemoteException thrown exception
     * @throws InvalidConnectionException thrown exception
     * @throws InvalidViewException thrown exception
     */
     public void loginScreen() throws RemoteException, InvalidConnectionException, InvalidViewException {
        printOnConsole("~~~~~~~~~~ Login page ~~~~~~~~~~");
        printOnConsole("Insert your username here: ");
        user = consoleScanner.next();
        if (choice == 1)
            vcEvent = new LoginEvent("RMI", user);
        else if (choice == 2)
            vcEvent = new LoginEvent("Socket", user);

        notifyObservers();
        //selectedClient.loginRequest(user);
    }

    @Override
    public void getEvent(VCEvent event) {

    }


    private int printConnectionChoice() {
        System.out.println("Select the Connection type you want to use:");
        System.out.println("1) RMI");
        System.out.println("2) Socket");
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
    public void notifyObservers() throws RemoteException, InvalidConnectionException, InvalidViewException {
        for (MyObserver o : observersCollection) {
            o.update(this, vcEvent);
        }
    }

    @Override
    public void update(MyObservable o, Object arg) {
        mvEvent = (MVEvent) arg;
        //metodo per gestire eventi MV
        //showEventResult();
        System.out.println("evento MV di login tornato");
    }


/*
    public void writeOnLogger(String c){

    }

    public void selectEvent(String c){

    }

    public LoginEvent createLoginEvent(String username, String command){
        LoginEvent event = new LoginEvent();
        return event;
    }

    public PlaceDieEvent createPlaceDieEvent(String username, String command){
        PlaceDieEvent event = new PlaceDieEvent();
        return event;
    }

    public SkipTurnEvent createSkipTurnEvent(String username, String command){
        SkipTurnEvent event = new SkipTurnEvent();
        return event;
    }

    public UseToolEvent createUseToolEvent(String username, String command){
        UseToolEvent event = new UseToolEvent();
        return event;
    }

    public RollDiceEvent createRollDiceEvent(String username, String command){
        RollDiceEvent event = new RollDiceEvent();
        return event;
    }

    public ChooseCardEvent createChooseCardEvent(String username, String command){
        ChooseCardEvent event = new ChooseCardEvent();
        return event;
    }

    public RoundTrackEvent createCheckRoundTrackEvent(String username, String command){
        RoundTrackEvent event = new RoundTrackEvent();
        return event;
    }
*/


}
