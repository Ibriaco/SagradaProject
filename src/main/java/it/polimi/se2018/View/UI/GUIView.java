package it.polimi.se2018.View.UI;


import it.polimi.se2018.Model.Event.*;
import it.polimi.se2018.Model.InvalidConnectionException;
import it.polimi.se2018.Model.InvalidViewException;
import it.polimi.se2018.MyObservable;
import it.polimi.se2018.MyObserver;
import it.polimi.se2018.Network.Client.NetworkHandler;
import it.polimi.se2018.View.ViewEvents.VCEvent;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ArrayList;

/**
 * Graphic user interface
 * @author Gregorio Galletti
 */
public class GUIView extends Application implements ViewInterface {

    private ArrayList<MyObserver> observerCollection;
    private NetworkHandler nh;


    @Override
    public void updateWindowCard() {

    }

    @Override
    public void showUI() {
        /* Intentionally left void (for now)*/
    }

    @Override
    public void receiveEvent(VCEvent event) {

    }

    @Override
    public void loginScreen() {

    }

    @Override
    public void handleMVEvent(LoggedUserEvent event) {

    }

    @Override
    public void handleMVEvent(PrivateCardEvent privateCardEvent) {

    }

    @Override
    public void handleMVEvent(WindowCardEvent event) {

    }

    @Override
    public void handleMVEvent(NewGameEvent newGameEvent) {

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
    public void start(Stage primaryStage) throws Exception {
        URL url = new File("src/main/java/it/polimi/se2018/View/UI/GUIUtils/loginJFoenix.fxml").toURI().toURL();
        Parent root = FXMLLoader.load(url);


        Scene scene = new Scene(root, 580, 380);

        primaryStage.centerOnScreen();
        primaryStage.setResizable(false);
        primaryStage.setTitle("Welcome to Sagrada Game");
        primaryStage.setScene(scene);

        primaryStage.show();
    }

    public void createNH(int choice) throws RemoteException{
        //nh = new NetworkHandler(choice);
        nh.registerObserver(this);
    }

    @Override
    public void update(MyObservable o, VCEvent arg) throws RemoteException, InvalidConnectionException, InvalidViewException {

    }

    @Override
    public void update(MyObservable o, MVEvent arg) throws RemoteException, InvalidConnectionException, InvalidViewException {

    }


/*
    public void showTimer(int timer){

    }
    public PlacedDieEvent createPlaceDieEvent(Player p, Game g){

    }
    public SkipTurnEvent createSkipTurnEvent (Player p, Game g) {

    }
    public UseToolCardEvent createUseToolCardEvent (Player p, Game g){

    }
    public RollDiceEvent createRollDieEvent (Player p,Game g){

    }
    public SeePlayerWindowEvent createSeePlayerWindowEvent (Player p, Game g){

    }
    public RoundTrackEvent createCheckRoundTrackEvent(Player p, Game g){

    }*/
}
