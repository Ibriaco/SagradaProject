package it.polimi.se2018.View;


import it.polimi.se2018.MyObservable;
import it.polimi.se2018.MyObserver;
import it.polimi.se2018.Network.client.ClientInterface;
import it.polimi.se2018.Network.client.rmi.RMIClient;

import java.rmi.RemoteException;
import java.util.ArrayList;

import static it.polimi.se2018.View.CLIUtils.consoleErrorWriter;
import static it.polimi.se2018.View.CLIUtils.consoleScanner;
import static it.polimi.se2018.View.CLIUtils.printOnConsole;


public class CLIView implements ViewInterface {

    private ArrayList<MyObserver> observerCollection;
    private ClientInterface selectedClient;
    private String command;

    public String getCommand() {

        return command;
    }

    @Override
    public void updateWindowCard() {

    }

    @Override
    public void showUI() {

        int insertedPort;
        String insertedIP;
        boolean validInput = false;
        while(!validInput){
            int choice = printConnectionChoice();
            if(choice == 1){
                try {
                    insertedPort = requestPort();
                    selectedClient = new RMIClient();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                validInput = true;
            }
            else if(choice == 2){
                //selectedClient = new SocketClient();
                validInput = true;
            }
            else
                consoleErrorWriter.println("Invalid input, please try again!");
        }

        try {
            loginScreen();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    private void loginScreen() throws RemoteException{
        String user;
        printOnConsole("~~~~~~~~~~ Login page ~~~~~~~~~~");
        printOnConsole("Insert your username here: ");
        user = consoleScanner.next();
        selectedClient.loginRequest(user);
    }

    private int requestPort() {
        System.out.println("Select the port which you want to use:");
        return consoleScanner.nextInt();
    }

    private int printConnectionChoice() {
        System.out.println("Select the Connection type you want to use:");
        System.out.println("1) RMI");
        System.out.println("2) Socket");
        return consoleScanner.nextInt();
    }

    @Override
    public void registerObserver(MyObserver observer) {

    }

    @Override
    public void unregisterObserver(MyObserver observer) {

    }

    @Override
    public void notifyObservers() {

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

    public UseToolEvent createUseToolEvenet(String username, String command){
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
