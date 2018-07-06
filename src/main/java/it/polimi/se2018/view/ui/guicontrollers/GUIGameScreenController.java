package it.polimi.se2018.view.ui.guicontrollers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import it.polimi.se2018.model.*;
import it.polimi.se2018.model.event.*;
import it.polimi.se2018.org.json.simple.parser.ParseException;
import it.polimi.se2018.view.ui.GUIView;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static it.polimi.se2018.view.ui.guicontrollers.GUIControllerUtils.*;

/**Controller class of the GUI scene where the game is shown.
 * @author Gregorio Galletti
 */

public class GUIGameScreenController {

    private static final String MY_TURN = "It's up to you. You have 60 seconds to do your move.";
    private static final String NOT_MY_TURN = "'s turn. Waiting for him to play.";
    private static final String DISCONNECTION_MESSAGE = "disconnected from the game!.";
    private static final double R_HEIGHT = 50;
    private static final double R_WIDTH = 50;
    private static final double L_HEIGHT = 30;
    private static final double L_WIDTH = 30;
    private static final double T_HEIGHT = 45;
    private static final double T_WIDTH = 45;

    private boolean canUseTool;
    private boolean canPlaceDie;
    private boolean canUseRoundDice;

    private ColorAdjust colorAdjust;
    private ColorAdjust normalColor;
    private DropShadow shadowEffect;
    private double oldH;
    private double oldW;
    private ImageView toZoom;
    private ImageView toLight;
    private GUIView guiView;
    private StackPane stack = new StackPane();
    private boolean whichSize;
    private List<ImageView> diceListToDelete = new ArrayList<>();
    private List<RoundCell> roundTrackCells;
    private StackPane rStack = new StackPane();
    private GridPane diceOnTrack = new GridPane();

    @FXML
    private AnchorPane anchor;
    @FXML
    private ImageView privateCard;
    @FXML
    private GridPane myWindow;
    @FXML
    private GridPane publics;
    @FXML
    private ImageView pub0;
    @FXML
    private ImageView pub1;
    @FXML
    private ImageView pub2;
    @FXML
    private GridPane toolCards;
    @FXML
    private ImageView tool0;
    @FXML
    private ImageView tool1;
    @FXML
    private ImageView tool2;
    @FXML
    private GridPane window1;
    @FXML
    private GridPane window2;
    @FXML
    private GridPane window3;
    @FXML
    private Circle c0;
    @FXML
    private Circle c1;
    @FXML
    private Circle c2;
    @FXML
    private Circle c3;
    @FXML
    private Circle c4;
    @FXML
    private Circle c5;
    @FXML
    private GridPane draftPool;
    @FXML
    private Label player1;
    @FXML
    private Label player2;
    @FXML
    private Label player3;
    @FXML
    private Label token1;
    @FXML
    private Label token2;
    @FXML
    private Label token3;
    @FXML
    private GridPane roundTrack;
    @FXML
    private JFXButton skipButton;
    @FXML
    private JFXButton toolButton;
    @FXML
    private JFXButton placeButton;



    @FXML
    public void initialize(){
        stack.setLayoutX(100);
        stack.setLayoutY(100);
        anchor.getChildren().add(stack);
        shadowEffect = new DropShadow(20, javafx.scene.paint.Color.BLACK);
        colorAdjust = new ColorAdjust();
        colorAdjust.setBrightness(-0.5);
        normalColor = new ColorAdjust();

        rStack.setLayoutX(100);
        rStack.setLayoutY(100);
        diceOnTrack.setPrefWidth(500);
        diceOnTrack.setPrefHeight(50);
        anchor.getChildren().add(rStack);

        privateCard.setEffect(shadowEffect);
        tool0.setEffect(shadowEffect);
        tool1.setEffect(shadowEffect);
        tool2.setEffect(shadowEffect);
        pub0.setEffect(shadowEffect);
        pub1.setEffect(shadowEffect);
        pub2.setEffect(shadowEffect);
        roundTrack.setEffect(shadowEffect);
        myWindow.setEffect(shadowEffect);
    }

    public void showTurnDialog(String user) {
        if (user.equals(guiView.getUser()))
            makeDialog(MY_TURN, stack, INFO_TYPE, "");
        else
            makeDialog(NOT_MY_TURN, stack, INFO_TYPE, user);
    }

    public void showCards(PrivateCardEvent privateCardEvent, PublicCardEvent publicCardEvent, ToolCardEvent toolCardEvent) {
        showPrivate(privateCardEvent.getPrivateName());
        showPublic(publicCardEvent.getPublicName());
        showTool(toolCardEvent.getToolCards());
    }

    private void showPublic(List<String> publicNames) {
        pub0.setImage(new Image(new File("./src/main/resources/GUIUtils/publics/" + publicNames.get(0) + ".png").toURI().toString()));
        pub1.setImage(new Image(new File("./src/main/resources/GUIUtils/publics/" + publicNames.get(1) + ".png").toURI().toString()));
        pub2.setImage(new Image(new File("./src/main/resources/GUIUtils/publics/" + publicNames.get(2) + ".png").toURI().toString()));
    }

    private void showTool(List<String> toolNames) {
        tool0.setImage(new Image(new File("./src/main/resources/GUIUtils/tools/" + toolNames.get(0) + ".png").toURI().toString()));
        tool1.setImage(new Image(new File("./src/main/resources/GUIUtils/tools/" + toolNames.get(1) + ".png").toURI().toString()));
        tool2.setImage(new Image(new File("./src/main/resources/GUIUtils/tools/" + toolNames.get(2) + ".png").toURI().toString()));
        tool0.setOnMouseClicked(this::handleToolClick);
        tool0.setOnMouseClicked(this::handleToolClick);
        tool0.setOnMouseClicked(this::handleToolClick);

    }

    private void showPrivate(String privateName) {
        privateCard.setImage(new Image(new File("./src/main/resources/GUIUtils/privates/" + privateName + ".png").toURI().toString()));
    }

    public void updateScreen(UpdateGameEvent updateGameEvent){

        flushAll();

        int userN = 0;
        int counter = 0;
        String currentUser;

        for (WindowCard w: updateGameEvent.getWindowCardList()) {
            currentUser = updateGameEvent.getUser().get(userN);
            if (currentUser.equals(guiView.getUser())) {
                whichSize = true;
                showWindow(w, myWindow);
                showTokens(w);
            }
            else {
                whichSize = false;
                if (counter == 0)
                    showTheirWindow(w, window1, player1, token1, currentUser);
                else if (counter == 1)
                    showTheirWindow(w, window2, player2, token2, currentUser);
                else
                    showTheirWindow(w, window3, player3, token3, currentUser);
                counter++;
            }
            userN++;
        }

        whichSize = true;
        showDice(updateGameEvent.getDice());
        showRoundTrack(updateGameEvent.getRoundTrack());

    }

    private void flushAll() {

        for(ImageView i : diceListToDelete) {
                draftPool.getChildren().remove(i);
        }
    }

    private void showDice(List<Die> dice) {
        int i = 0;

        for (Die d : dice) {
            createAndPutDie(draftPool, d, i, 0, true);
            i++;
        }
    }

    private void showRoundTrack(List<RoundCell> track) {
        for (int i = 0; i < 10; i++) {
            ImageView imageView = new ImageView(new Image(new File("./src/main/resources/GUIUtils/round/round" +(i+1) +".png").toURI().toString()));
            imageView.setFitHeight(T_HEIGHT);
            imageView.setFitWidth(T_WIDTH);
            imageView.setOnMouseEntered(this::lightUp);
            imageView.setOnMouseExited(this::lightDown);
            imageView.setOnMouseClicked(this::showRoundDice);
            roundTrack.add(imageView, i, 0);
            roundTrackCells = track;
        }
    }

    private void showRoundDice(MouseEvent mouseEvent) {
        ImageView source = (ImageView) mouseEvent.getSource();
        int column = GridPane.getColumnIndex(source);
        try{
            makeRoundDialog("Round " + (column + 1), roundTrackCells.get(column));
        }
        catch (IndexOutOfBoundsException e){
            /*If this exception is caught means that no dice has to be shown*/
        }
    }

    private void lightDown(MouseEvent mouseEvent) {
        toLight.setEffect(normalColor);
    }

    private void lightUp(MouseEvent event) {
        toLight = (ImageView) event.getSource();
        toLight.setEffect(colorAdjust);
    }


    private void showTheirWindow(WindowCard w, GridPane pane, Label player, Label token, String p) {
        player.setText(p);
        token.setText(String.valueOf(w.getDifficulty()));
        pane.setVisible(true);
        player.setVisible(true);
        token.setVisible(true);
        pane.setEffect(shadowEffect);
        showWindow(w, pane);

    }

    private void showWindow(WindowCard w, GridPane location) {
        for (int i = 0; i < w.getRows(); i++) {
            for (int j = 0; j < w.getCols(); j++)
                putInGrid(location, createCell(w, i, j), j, i);
        }
        addDice(w, location);

    }

    private void showTokens(WindowCard w) {

        int tokensN = w.getDifficulty();
        List<Circle> tokenCircles = new ArrayList<>();
        //molto brutto
        tokenCircles.add(c0);
        tokenCircles.add(c1);
        tokenCircles.add(c2);
        tokenCircles.add(c3);
        tokenCircles.add(c4);
        tokenCircles.add(c5);

        int i = 0;
        for (Circle c : tokenCircles) {
            if (i < tokensN) {
                c.setVisible(true);
                c.setEffect(shadowEffect);
            }
            i++;
        }

    }

    private void putInGrid(GridPane grid, Node node, int j, int i) {
       grid.add(node, j, i);
    }

    private Node createCell(WindowCard windowCard, int i, int j) {

        int value = windowCard.getGridCell(i,j).getShade();
        if(value != 0)
        {
            ImageView imageView = new ImageView(new Image(new File("./src/main/resources/GUIUtils/dice/" + value + ".png").toURI().toString()));
            if(whichSize) {
                imageView.setFitHeight(R_HEIGHT);
                imageView.setFitWidth(R_WIDTH);
            }
            else {
                imageView.setFitHeight(L_HEIGHT);
                imageView.setFitWidth(L_WIDTH);
            }
            return imageView;
        }
        else {
            Rectangle rect;
            if(whichSize)
                rect = new Rectangle(R_WIDTH, R_HEIGHT, GUIControllerUtils.getMatch(windowCard.getGridCell(i, j).getColor()));
            else
                rect = new Rectangle(L_WIDTH, L_HEIGHT, GUIControllerUtils.getMatch(windowCard.getGridCell(i, j).getColor()));
            rect.setStrokeType(StrokeType.INSIDE);
            rect.setStroke(javafx.scene.paint.Color.BLACK);
            rect.setStrokeWidth(2);

            return rect;
        }
    }

    private void addDice(WindowCard w, GridPane location) {

        for (int i = 0; i < w.getRows(); i++) {
            for (int j = 0; j < w.getCols(); j++)
                if (w.getGridCell(i,j).isPlaced())
                    createAndPutDie(location, w.getGridCell(i,j).getPlacedDie(), i, j, true);
                else
                    createAndPutDie(location, w.getGridCell(i,j).getPlacedDie(), i, j, false);
        }

    }

    private void createAndPutDie(GridPane grid, Die placedDie, int i, int j, boolean present) {

        ImageView imageView;

        if (present) {
            char pathColor = placedDie.getColor().toString().charAt(0);
            char pathValue = String.valueOf(placedDie.getValue()).charAt(0);
            imageView = new ImageView(new Image(new File("./src/main/resources/GUIUtils/dice/d" + pathColor + pathValue + ".png").toURI().toString()));
        }
        else
            imageView = new ImageView(new Image(new File("./src/main/resources/GUIUtils/dice/intentionallyWrong.png").toURI().toString()));

        if (whichSize) {
            imageView.setFitHeight(R_HEIGHT);
            imageView.setFitWidth(R_WIDTH);
        }
        else {
            imageView.setFitHeight(L_HEIGHT);
            imageView.setFitWidth(L_WIDTH);
        }

        if(grid.equals(draftPool)) {
            imageView.setEffect(shadowEffect);
            imageView.setOnMouseEntered(this::lightUp);
            imageView.setOnMouseExited(this::lightDown);
            diceListToDelete.add(imageView);
        }

        if (grid.equals(draftPool))
            imageView.setOnMouseClicked(this::handleDraftPoolClick);
        else if (grid.equals(myWindow))
            imageView.setOnMouseClicked(this::handleMyWindowClick);

        grid.add(imageView, j, i);
    }

    public void showDisconnectDialog(String user) {
        makeDialog(DISCONNECTION_MESSAGE, stack, INFO_TYPE, user);
    }

    public void setView(GUIView guiView){
        this.guiView = guiView;
    }

    @FXML
    private void zoomCard(MouseEvent mouseEvent){

        toZoom = (ImageView) mouseEvent.getSource();
        if (toZoom.equals(pub0) || toZoom.equals(pub1) || toZoom.equals(pub2))
            publics.toFront();
        toZoom.toFront();

        oldH = toZoom.getFitHeight();
        oldW = toZoom.getFitWidth();

        toZoom.setFitWidth(oldW * 2);
        toZoom.setFitHeight(oldH * 2);

    }

    @FXML
    private void zoomOut(){

        if (toZoom.equals(pub0) || toZoom.equals(pub1) || toZoom.equals(pub2))
            publics.toBack();

        toZoom.setFitHeight(oldH);
        toZoom.setFitWidth(oldW);
        toZoom.toBack();
    }

    @FXML
    private void placeAction(){
        makeDialog("Select a die from the Draft Pool and place it on your window pattern.", stack, INFO_TYPE, "");
        canPlaceDie = true;
    }

    @FXML
    private void toolAction(){
        makeDialog("Select a Tool Card to use it.", stack, INFO_TYPE, "");
        canUseTool = true;
    }

    @FXML
    private void skipAction() throws InvalidDieException, InvalidConnectionException, ParseException, InvalidViewException, IOException {
        guiView.createSkipTurnEvent();
        disableButtons();
    }

    @FXML
    public void enableButtons(){
        skipButton.setDisable(false);
        toolButton.setDisable(false);
        placeButton.setDisable(false);
    }

    @FXML
    public void disableButtons(){
        skipButton.setDisable(true);
        toolButton.setDisable(true);
        placeButton.setDisable(true);
    }

    public void changeScene(EndGameEvent endGameEvent) {
        Stage stage = (Stage) anchor.getScene().getWindow();

        Scene scene = stage.getScene();

        URL url = null;
        try {
            url = new File("src/main/resources/GUIUtils/endGame.fxml").toURI().toURL();
        } catch (MalformedURLException e) {
        }
        Parent root = null;
        FXMLLoader loader = null;
        try{
            loader = new FXMLLoader(url);
            root = loader.load();
        } catch (IOException e) {
        }

        guiView.setGuiEndScreenController(loader.getController());
        guiView.getGuiEndScreenController().showScores(endGameEvent);

        stage.setResizable(false);
        stage.setOnCloseRequest(event -> System.exit(0));
        stage.setMinWidth(800);
        stage.setMinHeight(600);
        stage.setHeight(600);
        stage.setWidth(800);
        stage.centerOnScreen();
        scene.setRoot(root);
    }

    public void showAlert(String message) {
        makeDialog(message, stack, INFO_TYPE, "");
    }


    private void makeRoundDialog(String content, RoundCell cell){

        JFXDialogLayout layout = new JFXDialogLayout();
        int i = 0;
        for (Die d: cell.getDiceList()) {
            char pathColor = d.getColor().toString().charAt(0);
            char pathValue = String.valueOf(d.getValue()).charAt(0);
            ImageView imageView = new ImageView(new Image(new File("./src/main/resources/GUIUtils/dice/d" + pathColor + pathValue + ".png").toURI().toString()));
            imageView.setFitHeight(50);
            imageView.setFitWidth(50);
            imageView.setOnMouseClicked(this::handleRoundDiceClicked);
            diceOnTrack.add(imageView, i, 0);
            i++;
        }

        JFXDialog dialog = new JFXDialog(rStack, layout, JFXDialog.DialogTransition.CENTER);
        JFXButton button = new JFXButton("CLOSE");
        button.setBackground(new Background(new BackgroundFill(javafx.scene.paint.Color.rgb(44,62,80), null, null)));
        button.setTextFill(javafx.scene.paint.Color.WHITE);
        button.setOnAction(event -> dialog.close());
        layout.setActions(button);
        layout.setBody(diceOnTrack);
        layout.setHeading(new Text(content));

        dialog.show();
    }

    private void handleRoundDiceClicked(MouseEvent mouseEvent) {
        if (canUseRoundDice)
            System.out.println("ok");
        else
            makeDialog("You can't do it now!", stack, ERROR_TYPE, "");

    }

    private void handleDraftPoolClick(MouseEvent mouseEvent) {
        if (canPlaceDie)
            System.out.println("ok");
        else
            makeDialog("You can't do it now!", stack, ERROR_TYPE, "");
    }

    private void handleMyWindowClick(MouseEvent mouseEvent) {
        if (canPlaceDie)
            System.out.println("ok");
        else
            makeDialog("You can't do it now!", stack, ERROR_TYPE, "");
    }

    private void handleToolClick(MouseEvent mouseEvent) {
        if (canUseTool)
            System.out.println("ok");
        else
            makeDialog("You can't do it now!", stack, ERROR_TYPE, "");
    }

    public void setCanUseTool(boolean canUseTool) {
        this.canUseTool = canUseTool;
    }

    public void setCanPlaceDie(boolean canPlaceDie) {
        this.canPlaceDie = canPlaceDie;
    }

    public void setCanUseRoundDice(boolean canUseRoundDice) {
        this.canUseRoundDice = canUseRoundDice;
    }

    public void miniActions() {
        toolButton.setDisable(true);
        placeButton.setDisable(false);
        skipButton.setDisable(false);
    }

    public void miniChoice() {
        toolButton.setDisable(false);
        placeButton.setDisable(true);
        skipButton.setDisable(false);
    }
}
