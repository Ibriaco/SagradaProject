package it.polimi.se2018.View;


import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import it.polimi.se2018.MyObservable;
import it.polimi.se2018.MyObserver;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;

public class GUIView extends Application implements ViewInterface {

    private ArrayList<MyObserver> observerCollection;

    @Override
    public void updateWindowCard() {

    }

    @Override
    public void showUI() {
        /* Intentionally left void (for now)*/
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

    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        URL url = new File("src/main/java/it/polimi/se2018/View/loginJFoenix.fxml").toURI().toURL();
        Parent root = FXMLLoader.load(url);

        Scene scene = new Scene(root, 600, 400);

        primaryStage.centerOnScreen();
        primaryStage.setResizable(false);
        primaryStage.setTitle("Welcome to Sagrada Game");
        primaryStage.setScene(scene);

        primaryStage.show();
    }


/*
    public void showTimer(int timer){

    }
    public PlaceDieEvent createPlaceDieEvent(Player p, Game g){

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
