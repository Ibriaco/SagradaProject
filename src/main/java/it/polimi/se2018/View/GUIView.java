package it.polimi.se2018.View;


import it.polimi.se2018.MyObservable;
import it.polimi.se2018.MyObserver;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

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
        primaryStage.setTitle("Hello World!");
        Button btn = new Button();
        btn.setText("Say 'Hello World'");
        btn.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                System.out.println("Hello World!");
            }
        });

        StackPane root = new StackPane();
        root.getChildren().add(btn);
        primaryStage.setScene(new Scene(root, 300, 250));
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
