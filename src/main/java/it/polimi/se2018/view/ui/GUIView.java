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
import it.polimi.se2018.org.json.simple.JSONArray;
import it.polimi.se2018.org.json.simple.parser.ParseException;
import it.polimi.se2018.view.ui.guicontrollers.*;
import it.polimi.se2018.view.viewevents.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import static it.polimi.se2018.ClientConfig.*;

/**
 * Graphic user interface
 * @author Gregorio Galletti
 */
public class GUIView extends Application implements ViewInterface {

    private List<MyObserver> observersCollection;
    private NetworkHandler nh;
    private VCEvent vcEvent;
    private String user;
    private List<WindowCard> myCardList;
    private GUILoginController guiLoginController;
    private GUIWaitingLobbyController guiWaitingLobbyController;
    private GUIChoiceController guiChoiceController;
    private GUIGameScreenController guiGameScreenController;
    private GUIEndScreenController guiEndScreenController;
    private static final Logger LOGGER = Logger.getGlobal();
    private int setDieEventPos;

    @Override
    public void start(Stage primaryStage) throws Exception {
        //URL url = new File("./src/main/resources/GUIUtils/loginJFoenix.fxml").toURI().toURL();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/GUIUtils/loginJFoenix.fxml"));
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
                LOGGER.log(Level.SEVERE, e.toString(), e);
            }
            Platform.runLater(() -> {
                try {
                    guiLoginController.reLogin(event.getState());
                } catch (RemoteException e) {
                    LOGGER.log(Level.SEVERE, e.toString(), e);
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
        Platform.runLater(()-> {
            guiGameScreenController.showTurnDialog(isTurnEvent.getPlayerInTurn());
            guiGameScreenController.enableButtons();
        });
    }

    @Override
    public void handleMVEvent(StopTurnEvent stopTurnEvent) {
        //System.out.println("ricevuto STOP");
        Platform.runLater(()->{
            guiGameScreenController.disableButtons();
            guiGameScreenController.setCanPlaceDie(false);
            guiGameScreenController.setCanUseTool(false);
            guiGameScreenController.setCanUseRoundDice(false);
            guiGameScreenController.setCanSelectCell(false);
            guiGameScreenController.showAlert(stopTurnEvent.getMessage());
        });
    }

    @Override
    public void handleMVEvent(ChangeDieEvent changeDieEvent) throws InvalidConnectionException, ParseException, InvalidViewException, IOException {
        //System.out.println("ricevuto CHANGE DIE");

        Platform.runLater(()-> {
            guiGameScreenController.setChangeDie(true);
            guiGameScreenController.setCanPlaceDie(true);
            guiGameScreenController.showAlert("Select a die from the DraftPool");
            //guiGameScreenController.setCanPlaceDie(true);
        });
    }

    @Override
    public void handleMVEvent(ModifiedPlaceEvent modifiedPlaceEvent) {
        //System.out.println("ricevuto MODIFIED");

        Platform.runLater(()->{
            guiGameScreenController.setCanSelectCell(true);
            guiGameScreenController.setModifiedPlace(true);
            guiGameScreenController.showAlert("Select the cell where you want to put the modified die");
        });
    }

    @Override
    public void handleMVEvent(IsNotYourTurn isNotYourTurn) {
        //System.out.println("ricevuto IS NOT");

        Platform.runLater(()->guiGameScreenController.showTurnDialog(isNotYourTurn.getUsername()));
    }

    @Override
    public void handleMVEvent(ChangedDieEvent changedDieEvent) {
        //System.out.println("ricevuto CHANGHED ");

        Platform.runLater(()->guiGameScreenController.showAlert("This is the modified die"));
    }

    @Override
    public void handleMVEvent(MoveDieEvent moveDieEffect) throws InvalidDieException, InvalidConnectionException, ParseException, InvalidViewException, IOException {
        //System.out.println("ricevuto MOVE");

        Platform.runLater(()->{
            guiGameScreenController.setCanSelectCell(true);
            guiGameScreenController.setMoveDie(true);
            guiGameScreenController.showAlert("Select the die you want to move, then the cell where you want to put it");
        });

    }

    @Override
    public void handleMVEvent(WrongPlaceEvent wrongPlaceEvent) throws InvalidDieException, InvalidConnectionException, InvalidViewException, ParseException, IOException {
        //System.out.println("ricevuto WRONG PLACE");

        Platform.runLater(()->{
            guiGameScreenController.showAlert(INVALID_MOVE_RETRY);
            guiGameScreenController.enableButtons();
        });
    }

    @Override
    public void handleMVEvent(IncDecEvent incDecEvent) throws InvalidDieException, InvalidConnectionException, ParseException, InvalidViewException, IOException {
        //System.out.println("ricevuto INCDEC");

        Platform.runLater(()->guiGameScreenController.showChoiceDialog(ONE_INCREASE_TWO_DECREASE));
    }

    @Override
    public void handleMVEvent(InvalidToolEvent invalidToolEvent) {
        //System.out.println("ricevuto INVALIDTOOL");

        Platform.runLater(()->{
            guiGameScreenController.showAlert(NOT_ENOUGH_TOKENS);
            guiGameScreenController.enableButtons();
        });

    }

    @Override
    public void handleMVEvent(PerformActionEvent performActionEvent) {
        //System.out.println("ricevuto PERFORM");

        Platform.runLater(()->guiGameScreenController.miniActions());
    }

    @Override
    public void handleMVEvent(RetryToolEvent retryToolEvent) throws InvalidDieException, InvalidConnectionException, InvalidViewException, ParseException, IOException {
        //System.out.println("ricevuto RETRY");

        Platform.runLater(()->{
            guiGameScreenController.showAlert("This ia an \"After Drafting\" tool card ! Please try with a different one!");
            guiGameScreenController.miniChoice();
        });
    }

    @Override
    public void handleMVEvent(RollingDiceEvent rollDiceEvent) throws InvalidDieException, InvalidConnectionException, InvalidViewException, ParseException, IOException {
        //System.out.println("ricevuto ROLLING");

        vcEvent = new RollDiceEvent(rollDiceEvent.getUsername());
        notifyObservers();
    }

    @Override
    public void handleMVEvent(SetDieEvent setDieEvent) throws InvalidDieException, InvalidConnectionException, ParseException, InvalidViewException, IOException {
        //System.out.println("ricevuto SETDIE");

        setDieEventPos = setDieEvent.getPos();
        Platform.runLater(()->guiGameScreenController.showValueDialog("Choose the value you want to give to the die!"));
    }

    @Override
    public void handleMVEvent(SwapDieEvent swapDieEvent) throws InvalidDieException, InvalidConnectionException, ParseException, InvalidViewException, IOException {
        //System.out.println("ricevuto SWAP");

        Platform.runLater(()->guiGameScreenController.showAlert("Select dice you want to swap: one from the RoundTrack and one from the DraftPool."));
    }

    @Override
    public void handleMVEvent(MiniMenuEvent miniMenuEvent) {
        //System.out.println("ricevuto MINIMENU");

        Platform.runLater(()->guiGameScreenController.miniChoice());
    }

    @Override
    public void handleMVEvent(DoublePlaceEvent doublePlaceEvent) {
        //System.out.println("ricevuto DOUBLEPLACE");

        Platform.runLater(()->guiGameScreenController.setCanPlaceDie(true));
    }

    @Override
    public void handleMVEvent(EndGameEvent endGameEvent) {
        Platform.runLater(()->guiGameScreenController.changeScene(endGameEvent));
    }

    @Override
    public void handleMVEvent(RequestCoordEvent requestCoordEvent) throws InvalidDieException, InvalidConnectionException, ParseException, InvalidViewException, IOException {
        //System.out.println("ricevuto REQUESTCOORD");

        Platform.runLater(()->{
            guiGameScreenController.setCanSelectCell(true);
        });

    }

    @Override
    public void handleMVEvent(RequestColorAndNumberEvent requestColorAndNumberEvent) throws InvalidDieException, InvalidConnectionException, ParseException, InvalidViewException, IOException {

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

    public void createSkipTurnEvent() throws InvalidConnectionException, IOException, InvalidViewException, ParseException, InvalidDieException {
        vcEvent = new SkipTurnEvent(user);
        //System.out.println("creo evento SKIP");
        notifyObservers();
    }

    public void setGuiEndScreenController(GUIEndScreenController guiEndScreenController) {
        this.guiEndScreenController = guiEndScreenController;
    }

    public GUIEndScreenController getGuiEndScreenController() {
        return guiEndScreenController;
    }

    public void createUseToolEvent(int pos) throws InvalidConnectionException, IOException, InvalidViewException, ParseException, InvalidDieException {
        vcEvent = new UseToolEvent(user, pos);
        //System.out.println("creo evento USETOOL " + pos);
        notifyObservers();
    }

    public void createPlaceDieEvent(int pos, int coordX, int coordY) throws InvalidConnectionException, IOException, InvalidViewException, ParseException, InvalidDieException {
        vcEvent = new PlaceDieEvent(user, pos, coordX, coordY);
        //System.out.println("creo evento PLACE " + pos + " " + coordX + " " + coordY);
        notifyObservers();
    }

    public void createSelectDieEvent(int pos) throws InvalidDieException, InvalidConnectionException, InvalidViewException, ParseException, IOException {
        vcEvent = new SelectDieEvent(user, pos);
        //System.out.println("creo evento SELECT DIE " + pos);
        notifyObservers();
    }


    public void createMovingDieEvent(Integer columnIndex, Integer rowIndex, Integer columnIndex1, Integer rowIndex1) throws InvalidDieException, InvalidConnectionException, InvalidViewException, ParseException, IOException {
        vcEvent = new MovingDieEvent(user, columnIndex, rowIndex, columnIndex1, rowIndex1);
        //System.out.println("creo evento MOVING DIE " + columnIndex + " " + rowIndex + " " + columnIndex1 + " " + rowIndex1);
        notifyObservers();
    }

    public void createIncDecEvent(int choice) throws InvalidDieException, InvalidConnectionException, InvalidViewException, ParseException, IOException {
        vcEvent = new IncrementDecrementDieEvent(user, choice);
        //System.out.println("creo evento INCDEC " + choice);
        notifyObservers();
    }

    public void createUpdateDieEvent(int val) throws InvalidDieException, InvalidConnectionException, InvalidViewException, ParseException, IOException {
        vcEvent = new UpdateDieEvent(user, val, setDieEventPos);
        //System.out.println("creo evento UPDATE DIE" + setDieEventPos + val);
        notifyObservers();
    }

    public void createPlaceDieWithRestrictionEvent(int x, int y) throws InvalidDieException, InvalidConnectionException, InvalidViewException, ParseException, IOException {
        vcEvent = new PlaceDieWithRestriction(user, x, y, true,true,false);
        notifyObservers();
    }
}
