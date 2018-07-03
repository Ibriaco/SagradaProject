package it.polimi.se2018.view.ui;


import it.polimi.se2018.controller.ChangeDieEvent;
import it.polimi.se2018.model.InvalidDieException;
import it.polimi.se2018.model.WindowCard;
import it.polimi.se2018.model.event.*;
import it.polimi.se2018.model.InvalidConnectionException;
import it.polimi.se2018.model.InvalidViewException;
import it.polimi.se2018.MyObservable;
import it.polimi.se2018.MyObserver;
import it.polimi.se2018.network.client.NetworkHandler;
import it.polimi.se2018.view.ui.guicontrollers.*;
import it.polimi.se2018.view.viewevents.ChooseCardEvent;
import it.polimi.se2018.view.viewevents.LoginEvent;
import it.polimi.se2018.view.viewevents.VCEvent;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Graphic user interface
 * @author Gregorio Galletti
 */
public class GUIView extends Application implements ViewInterface {

    private static final String SAGRADA_TITLE = "Welcome to Sagrada Game";
    private List<MyObserver> observersCollection;
    private NetworkHandler nh;
    private VCEvent vcEvent;
    private String user;
    private List<WindowCard> myCardList;
    private GUILoginController guiLoginController;
    private GUIWaitingLobbyController guiWaitingLobbyController;
    private GUIChoiceController guiChoiceController;
    private GUIGameScreenController guiGameScreenController;
    private static final Logger LOGGER = Logger.getGlobal();

    @Override
    public void start(Stage primaryStage) throws Exception {
        URL url = new File("./src/main/resources/GUIUtils/loginJFoenix.fxml").toURI().toURL();
        FXMLLoader loader = new FXMLLoader(url);
        Parent root = loader.load();

        observersCollection = new ArrayList<>();

        setGuiLoginController(loader.getController());

        Scene scene = new Scene(root, 580, 380);

        primaryStage.centerOnScreen();
        primaryStage.setResizable(false);
        primaryStage.setTitle(SAGRADA_TITLE);
        primaryStage.setScene(scene);
        primaryStage.setOnCloseRequest(event -> System.exit(0));
        primaryStage.show();
    }

    @Override
    public void updateWindowCard() {
        /* Intentionally left void (for now)*/
    }

    @Override
    public void showUI() {
        /* Intentionally left void (for now)*/
    }

    @Override
    public void receiveEvent(VCEvent event) {
        /*Intentionally left void, used only in VirtualView and GUIView*/
    }

    @Override
    public void loginScreen() {
        /* Intentionally left void (for now)*/
    }

    @Override
    public void handleMVEvent(LoggedUserEvent event) {
        if (event.isApproved()) {
            guiLoginController.changeScene();
            setUsername(event.getUsername());
        }
        else {
            try {
                destroyNH();
            } catch (RemoteException e) {
            }
            Platform.runLater(() -> {
                try {
                    guiLoginController.reLogin(event.getState());
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            });
        }
    }

    @Override
    public void handleMVEvent(DisconnectedEvent event) {
        Platform.runLater(()->guiGameScreenController.showDisconnectDialog(event.getUsername()));
    }

    @Override
    public void handleMVEvent(PrivateCardEvent privateCardEvent) {
        Platform.runLater(()->guiWaitingLobbyController.changeScene(privateCardEvent));
    }

    @Override
    public void handleMVEvent(WindowCardEvent event) {
        myCardList = event.getWindowCards();
        Platform.runLater(()->guiChoiceController.setEvent(event));
    }
    
    @Override
    public void handleMVEvent(UpdateGameEvent updateGameEvent) {
        if(guiChoiceController != null)
            Platform.runLater(()->guiGameScreenController.updateScreen(updateGameEvent));
        else
            Platform.runLater(()->guiLoginController.returnToGame(updateGameEvent));

    }

    @Override
    public void handleMVEvent(PublicCardEvent publicCardEvent) {
        Platform.runLater(()->guiChoiceController.setEvent(publicCardEvent));
    }

    @Override
    public void handleMVEvent(ToolCardEvent toolCardEvent) {
        Platform.runLater(()->guiChoiceController.setEvent(toolCardEvent));
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
    public void notifyObservers() throws InvalidConnectionException, ParseException, InvalidViewException, IOException, InvalidDieException {
        for (MyObserver o : observersCollection) {
            o.update(this, vcEvent);
        }
    }

    @Override
    public void createNH(String choice) throws RemoteException{
        nh = new NetworkHandler(choice);
        registerObserver(nh);
        nh.registerObserver(this);
    }

    public void destroyNH() throws RemoteException {
        unregisterObserver(nh);
        nh.unregisterObserver(this);
    }

    @Override
    public void setUsername(String u) {
        user = u;
    }

    public String getUser() {
        return user;
    }

    @Override
    public void createLoginEvent() throws InvalidConnectionException, IOException, InvalidViewException, ParseException, InvalidDieException {
        vcEvent = new LoginEvent(user);
        notifyObservers();
    }

    @Override
    public void handleMVEvent(IsTurnEvent isTurnEvent) throws InvalidConnectionException, InvalidViewException, ParseException, IOException {
        Platform.runLater(()->guiGameScreenController.showTurnDialog(isTurnEvent.getUser()));
    }

    @Override
    public void handleMVEvent(StopTurnEvent stopTurnEvent) {

    }

    @Override
    public void handleMVEvent(ChangeDieEvent changeDieEvent) throws InvalidConnectionException, ParseException, InvalidViewException, IOException {

    }

    @Override
    public void handleMVEvent(ModifiedPlaceEvent modifiedPlaceEvent) {

    }

    @Override
    public void handleMVEvent(IsNotYourTurn isNotYourTurn) {
        System.out.println("ricevuto is not");
        Platform.runLater(()->guiGameScreenController.showTurnDialog(isNotYourTurn.getUsername()));
    }

    @Override
    public void handleMVEvent(ChangedDieEvent changedDieEvent) {

    }

    @Override
    public void handleMVEvent(MoveDieEvent moveDieEffect) throws InvalidDieException, InvalidConnectionException, ParseException, InvalidViewException, IOException {

    }

    @Override
    public void handleMVEvent(WrongPlaceEvent wrongPlaceEvent) throws InvalidDieException, InvalidConnectionException, InvalidViewException, ParseException, IOException {

    }

    @Override
    public void handleMVEvent(IncDecEvent incDecEvent) throws InvalidDieException, InvalidConnectionException, ParseException, InvalidViewException, IOException {
        
    }


    public void createChooseCardEvent(WindowCard windowCard) throws InvalidConnectionException, IOException, InvalidViewException, ParseException, InvalidDieException {
        vcEvent = new ChooseCardEvent(user, windowCard);
        notifyObservers();
    }

    @Override
    public void update(MyObservable o, VCEvent arg) throws RemoteException, InvalidConnectionException, InvalidViewException {

    }

    @Override
    public void update(MyObservable o, MVEvent arg) throws IOException, InvalidConnectionException, InvalidViewException, ParseException {
        try {
            arg.accept(this);
        } catch (InvalidDieException e) {
            LOGGER.log(Level.SEVERE, e.toString(), e);
        }
    }

    public WindowCard findInCards(String n) {
        return myCardList.stream().filter(w -> w.getWindowName().equalsIgnoreCase(n)).findFirst().orElse(null);
    }


    public void setGuiLoginController(GUILoginController guiLoginController) {
        this.guiLoginController = guiLoginController;
        this.guiLoginController.setView(this);
    }

    public void setGuiWaitingLobbyController(GUIWaitingLobbyController guiWaitingLobbyController) {
        this.guiWaitingLobbyController = guiWaitingLobbyController;
        this.guiWaitingLobbyController.setView(this);
    }

    public void setGuiChoiceController(GUIChoiceController guiChoiceController) {
        this.guiChoiceController = guiChoiceController;
        this.guiChoiceController.setView(this);
    }

    public void setGuiGameScreenController(GUIGameScreenController guiGameScreenController) {
        this.guiGameScreenController = guiGameScreenController;
    }

    public GUILoginController getGuiLoginController() {
        return guiLoginController;
    }

    public GUIWaitingLobbyController getGuiWaitingLobbyController() {
        return guiWaitingLobbyController;
    }

    public GUIChoiceController getGuiChoiceController() {
        return guiChoiceController;
    }

    public GUIGameScreenController getGuiGameScreenController() {
        return guiGameScreenController;
    }

    /*
    public void showTimer(int timer){

    }
    public PlacingDieEvent createPlaceDieEvent(Player p, Game g){

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
