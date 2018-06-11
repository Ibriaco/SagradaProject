package it.polimi.se2018.View.UI;


import it.polimi.se2018.Model.InvalidConnectionException;
import it.polimi.se2018.Model.InvalidViewException;
import it.polimi.se2018.Model.WindowCardAssociationException;
import it.polimi.se2018.MyObservable;
import it.polimi.se2018.MyObserver;
import it.polimi.se2018.Network.NetworkHandler;
import it.polimi.se2018.View.ViewEvents.LoginEvent;
import it.polimi.se2018.View.ViewEvents.VCEvent;


import java.rmi.RemoteException;
import java.util.ArrayList;

import static it.polimi.se2018.View.UI.CLIUtils.consoleErrorWriter;
import static it.polimi.se2018.View.UI.CLIUtils.consoleScanner;
import static it.polimi.se2018.View.UI.CLIUtils.printOnConsole;


public class CLIView implements ViewInterface {

    private NetworkHandler nh;
    private VCEvent vcEvent;
    private int choice;
    private String user;
    private ArrayList<MyObserver> observersCollection = new ArrayList<>();

    @Override
    public void updateWindowCard() {

    }

    @Override
    public void showUI(){

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

     public void loginScreen(){
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
    public void notifyObservers() {
        for (MyObserver o : observersCollection) {
            update(this, vcEvent);
        }
    }

    @Override
    public void update(MyObservable o, Object arg) {
        System.out.println(arg.toString());

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
