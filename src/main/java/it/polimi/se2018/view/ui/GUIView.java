package it.polimi.se2018.view.ui;


import it.polimi.se2018.model.WindowCard;
import it.polimi.se2018.model.event.*;
import it.polimi.se2018.model.InvalidConnectionException;
import it.polimi.se2018.model.InvalidViewException;
import it.polimi.se2018.MyObservable;
import it.polimi.se2018.MyObserver;
import it.polimi.se2018.network.client.NetworkHandler;
import it.polimi.se2018.view.ui.guicontrollers.GUIControllerIF;
import it.polimi.se2018.view.ui.guicontrollers.GUILoginController;
import it.polimi.se2018.view.viewevents.ChooseCardEvent;
import it.polimi.se2018.view.viewevents.LoginEvent;
import it.polimi.se2018.view.viewevents.VCEvent;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

/**
 * Graphic user interface
 * @author Gregorio Galletti
 */
public class GUIView extends Application implements ViewInterface {

    private List<MyObserver> observersCollection;
    private NetworkHandler nh;
    private List<GUIControllerIF> controllerList;
    private VCEvent vcEvent;
    private String user;
    private List<WindowCard> myCardList;
    private boolean something = false;

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
        if (event.isApproved())
            controllerList.get(0).changeScene();
        else
            Platform.runLater(()-> controllerList.get(0).reLogin(event.getState()));
            //controllerList.get(0).reLogin(event.getState());
    }

    @Override
    public void handleMVEvent(PrivateCardEvent privateCardEvent) {
        Platform.runLater(()->controllerList.get(1).changeScene(privateCardEvent));
    }

    @Override
    public void handleMVEvent(WindowCardEvent event) {
        myCardList = event.getWindowCards();
        Platform.runLater(()->controllerList.get(2).setEvent(event));
    }
    
    @Override
    public void handleMVEvent(NewGameEvent newGameEvent) {

    }

    @Override
    public void handleMVEvent(PublicCardEvent publicCardEvent) {

    }

    @Override
    public void handleMVEvent(ToolCardEvent toolCardEvent) {

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
    public void notifyObservers() throws InvalidConnectionException, ParseException, InvalidViewException, IOException {
        for (MyObserver o : observersCollection) {
            o.update(this, vcEvent);
        }
    }


    @Override
    public void start(Stage primaryStage) throws Exception {
        URL url = new File("./src/main/resources/GUIUtils/loginJFoenix.fxml").toURI().toURL();
        FXMLLoader loader = new FXMLLoader(url);
        Parent root = loader.load();

        controllerList = new ArrayList<>();
        observersCollection = new ArrayList<>();

        addGUIController(loader.getController());

        Scene scene = new Scene(root, 580, 380);

        primaryStage.centerOnScreen();
        primaryStage.setResizable(false);
        primaryStage.setTitle("Welcome to Sagrada Game");
        primaryStage.setScene(scene);

        primaryStage.show();
    }

    @Override
    public void createNH(int choice) throws RemoteException{
        nh = new NetworkHandler(choice);
        registerObserver(nh);
        nh.registerObserver(this);
    }

    @Override
    public void setUsername(String u) {
        user = u;
    }

    @Override
    public void createLoginEvent() throws InvalidConnectionException, IOException, InvalidViewException, ParseException {
        vcEvent = new LoginEvent(user);
        notifyObservers();
    }


    public void createChooseCardEvent(WindowCard windowCard) throws InvalidConnectionException, IOException, InvalidViewException, ParseException {
        vcEvent = new ChooseCardEvent(user, windowCard);
        notifyObservers();
    }

    @Override
    public void update(MyObservable o, VCEvent arg) throws RemoteException, InvalidConnectionException, InvalidViewException {

    }

    @Override
    public void update(MyObservable o, MVEvent arg) throws RemoteException, InvalidConnectionException, InvalidViewException {
        arg.accept(this);
    }

    public void addGUIController(GUIControllerIF gc){

        gc.setView(this);
        controllerList.add(gc);
    }

    public List<GUIControllerIF> getControllerList() {
        return controllerList;
    }

    public WindowCard findInCards(String n) {
        return myCardList.stream().filter(w -> w.getWindowName().equalsIgnoreCase(n)).findFirst().orElse(null);
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
