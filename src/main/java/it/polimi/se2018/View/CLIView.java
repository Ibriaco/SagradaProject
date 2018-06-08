package it.polimi.se2018.View;


import it.polimi.se2018.MyObservable;
import it.polimi.se2018.MyObserver;
import it.polimi.se2018.Network.NetworkHandler;


import java.rmi.RemoteException;
import java.util.ArrayList;

import static it.polimi.se2018.View.CLIUtils.consoleErrorWriter;
import static it.polimi.se2018.View.CLIUtils.consoleScanner;


public class CLIView implements ViewInterface {

    private NetworkHandler nh;
    private String command;
    public String getCommand() {

        return command;
    }

    @Override
    public void updateWindowCard() {

    }

    @Override
    public void showUI() throws RemoteException {

        boolean validInput = false;
        while(!validInput) {
            int choice = printConnectionChoice();
            if (choice == 1 || choice == 2) {
                nh = new NetworkHandler(choice);
                nh.registerObserver(this);
                validInput = true;
            } else
                consoleErrorWriter.println("Invalid input, please try again!");
        }
        try {
            nh.loginScreen();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
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
