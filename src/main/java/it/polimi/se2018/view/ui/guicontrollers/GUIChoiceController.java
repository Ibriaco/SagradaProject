package it.polimi.se2018.view.ui.guicontrollers;

import it.polimi.se2018.model.InvalidConnectionException;
import it.polimi.se2018.model.InvalidDieException;
import it.polimi.se2018.model.InvalidViewException;
import it.polimi.se2018.model.WindowCard;
import it.polimi.se2018.model.event.PrivateCardEvent;
import it.polimi.se2018.model.event.PublicCardEvent;
import it.polimi.se2018.model.event.ToolCardEvent;
import it.polimi.se2018.model.event.WindowCardEvent;
import it.polimi.se2018.view.ui.GUIView;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import javafx.stage.Screen;
import javafx.stage.Stage;


import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import static it.polimi.se2018.view.ui.guicontrollers.GUIControllerUtils.*;

public class GUIChoiceController implements GUIControllerIF {

    private static final double R_HEIGHT = 37.5;
    private static final double R_WIDTH = 40;
    private static final String SELECTION_MESSAGE = "You selected the Window Card. Waiting for the other players. \nGood Luck!";
    private static final Logger LOGGER = Logger.getLogger(GUIWaitingLobbyController.class.getName());
    private StackPane stack = new StackPane();

    private GUIView guiView;
    private PublicCardEvent publicCardEvent;
    private ToolCardEvent toolCardEvent;
    private PrivateCardEvent privateCardEvent;

    @FXML
    private Pane pane;
    @FXML
    private Label titleNW;
    @FXML
    private Label titleNE;
    @FXML
    private Label titleSW;
    @FXML
    private Label titleSE;
    @FXML
    private Label diffNW;
    @FXML
    private Label diffNE;
    @FXML
    private Label diffSW;
    @FXML
    private Label diffSE;
    @FXML
    private GridPane gridNW;
    @FXML
    private GridPane gridNE;
    @FXML
    private GridPane gridSW;
    @FXML
    private GridPane gridSE;
    @FXML
    private ImageView privateImage;

    private int index = 0;

    @FXML
    public void initialize(){
        stack.setLayoutX(100);
        stack.setLayoutY(100);
        pane.getChildren().add(stack);
    }

    public void setEvent(WindowCardEvent event){
        for (WindowCard w : event.getWindowCards()){
            showCard(w, index);
            index++;
        }
    }

    public void setEvent(PrivateCardEvent event){
        this.privateCardEvent = event;
        placeCard(event.getPrivateName());
    }

    public void setEvent(PublicCardEvent publicCardEvent) {
        this.publicCardEvent = publicCardEvent;
    }

    public void setEvent(ToolCardEvent toolCardEvent) {
        this.toolCardEvent = toolCardEvent;
        changeScene();
    }

    private void placeCard(String privateName) {
        privateImage.setImage(new Image(new File("./src/main/resources/GUIUtils/privates/" + privateName + ".png").toURI().toString()));
    }

    private void showCard(WindowCard windowCard, int index) {
        for (int i = 0; i < windowCard.getRows(); i++) {
            for (int j = 0; j < windowCard.getCols(); j++) {
                putInGrid(index, createCell(windowCard, i, j), j, i);
                putNameAndDiff(windowCard);
            }
        }
    }

    private void putNameAndDiff(WindowCard windowCard) {
        if(index == 0) {
            titleNW.setText(windowCard.getWindowName());
            diffNW.setText(String.valueOf(windowCard.getDifficulty()));
        }
        else if(index == 1) {
            titleNE.setText(windowCard.getWindowName());
            diffNE.setText(String.valueOf(windowCard.getDifficulty()));
        }
        else if(index == 2) {
            titleSW.setText(windowCard.getWindowName());
            diffSW.setText(String.valueOf(windowCard.getDifficulty()));
        }
        else if(index == 3){
            titleSE.setText(windowCard.getWindowName());
            diffSE.setText(String.valueOf(windowCard.getDifficulty()));
        }
    }

    private void putInGrid(int index, Node node, int j, int i) {
        if(index == 0)
            gridNW.add(node, j, i);
        else if(index == 1)
            gridNE.add(node, j, i);
        else if(index == 2)
            gridSW.add(node, j, i);
        else if(index == 3)
            gridSE.add(node, j, i);
    }

    private Node createCell(WindowCard windowCard, int i, int j) {

        int value = windowCard.getGridCell(i,j).getShade();
        if(value != 0)
        {
            ImageView imageView = new ImageView(new Image(new File("./src/main/resources/GUIUtils/dice/" + value + ".png").toURI().toString()));
            imageView.setFitHeight(R_HEIGHT);
            imageView.setFitWidth(R_WIDTH);
            return imageView;
        }
        else {
            Rectangle rect = new Rectangle(R_WIDTH, R_HEIGHT, GUIControllerUtils.getMatch(windowCard.getGridCell(i, j).getColor()));
            rect.setStrokeType(StrokeType.INSIDE);
            rect.setStroke(javafx.scene.paint.Color.BLACK);
            rect.setStrokeWidth(2);

            return rect;
        }
    }

    @Override
    public void setView(GUIView vi) {
        guiView = vi;
    }

    @Override
    public void changeScene() {
        Stage stage = (Stage) titleNW.getScene().getWindow();

        Scene scene = stage.getScene();

        URL url = null;
        try {
            url = new File("src/main/resources/GUIUtils/gameScreen.fxml").toURI().toURL();
        } catch (MalformedURLException e) {
            LOGGER.log(Level.SEVERE, e.toString(), e);
        }
        Parent root = null;
        FXMLLoader loader = null;
        try{
            loader = new FXMLLoader(url);
            root = loader.load();
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, e.toString(), e);
        }
        GUIGameScreenController gameCtrl = loader.getController();
        gameCtrl.showCards(privateCardEvent, publicCardEvent, toolCardEvent);
        guiView.setGuiGameScreenController(gameCtrl);

        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        stage.setHeight(screenBounds.getHeight()*0.7);
        stage.setWidth(screenBounds.getWidth()*0.7);
        //stage.setResizable(true);
        stage.setMinHeight(600);
        stage.setMinWidth(800);
        stage.setOnCloseRequest(event -> System.exit(0));


        stage.centerOnScreen();
        scene.setRoot(root);
    }

    @FXML
    public void handleSelection(MouseEvent mouseEvent) throws InvalidConnectionException, org.json.simple.parser.ParseException, InvalidViewException, IOException, InvalidDieException {
        GridPane selectedGrid = (GridPane) mouseEvent.getSource();
        String windowName = "";
        if(selectedGrid.equals(gridNW))
            windowName = titleNW.getText();
        else if (selectedGrid.equals(gridNE))
            windowName = titleNE.getText();
        else if (selectedGrid.equals(gridSW))
            windowName = titleSW.getText();
        else if (selectedGrid.equals(gridSE))
            windowName = titleSE.getText();

        guiView.createChooseCardEvent(guiView.findInCards(windowName));
        makeUnClickable();
        makeDialog(SELECTION_MESSAGE, stack, windowName, "");
    }

    private void makeUnClickable() {
        gridNW.setDisable(true);
        gridNE.setDisable(true);
        gridSW.setDisable(true);
        gridSE.setDisable(true);
    }
}
