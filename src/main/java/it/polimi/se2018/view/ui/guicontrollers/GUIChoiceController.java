package it.polimi.se2018.view.ui.guicontrollers;

import it.polimi.se2018.model.InvalidConnectionException;
import it.polimi.se2018.model.InvalidViewException;
import it.polimi.se2018.model.WindowCard;
import it.polimi.se2018.model.event.PrivateCardEvent;
import it.polimi.se2018.model.event.WindowCardEvent;
import it.polimi.se2018.view.ui.GUIView;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;

import java.io.File;
import java.io.IOException;


public class GUIChoiceController implements GUIControllerIF {

    private static final double R_HEIGHT = 37.5;
    private static final double R_WIDTH = 40;
    private static final String SELECTION_MESSAGE = "You selected the Window Card. Waiting for the other players. \nGood Luck!";

    private GUIView guiView;
    private WindowCardEvent windowCardEvent;
    private PrivateCardEvent privateCardEvent;

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

    public void setEvent(WindowCardEvent event){
        this.windowCardEvent = event;
        for (WindowCard w : event.getWindowCards()){
            showCard(w, index);
            index++;
        }
    }

    public void setEvent(PrivateCardEvent event){
        this.privateCardEvent = event;
        placeCard(privateCardEvent.getPrivateName());
    }

    private void placeCard(String privateName) {
        privateImage.setImage(new Image(new File("./src/main/resources/GUIUtils/privates/" + privateName + ".png").toURI().toString()));
    }

    private void showCard(WindowCard windowCard, int index) {
        for (int i = 0; i < windowCard.getRows(); i++) {
            for (int j = 0; j < windowCard.getCols(); j++) {
                putInGrid(index, createRect(windowCard, i, j), j, i);
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

    private void putInGrid(int index, Rectangle rect, int j, int i) {
        if(index == 0)
            gridNW.add(rect, j, i);
        else if(index == 1)
            gridNE.add(rect, j, i);
        else if(index == 2)
            gridSW.add(rect, j, i);
        else if(index == 3)
            gridSE.add(rect, j, i);

    }

    private Rectangle createRect(WindowCard windowCard, int i, int j) {
        Rectangle rect = new Rectangle(R_WIDTH, R_HEIGHT, GUIControllerUtils.getMatch(windowCard.getGridCell(i, j).getColor()));
        rect.setStrokeType(StrokeType.INSIDE);
        rect.setStroke(javafx.scene.paint.Color.BLACK);
        rect.setStrokeWidth(2);

        return rect;
    }

    @Override
    public void setView(GUIView vi) {
        guiView = vi;
    }

    @Override
    public void changeScene() {
        //load the game screen
    }

    @Override
    public void changeScene(PrivateCardEvent event) {
        /*Not used in this class*/
    }

    @Override
    public void reLogin(String state) {
        /*Not used in this class*/
    }

    @FXML
    public void handleSelection(MouseEvent mouseEvent) throws InvalidConnectionException, org.json.simple.parser.ParseException, InvalidViewException, IOException {
        GridPane selectedGrid = (GridPane) mouseEvent.getSource();
        if(selectedGrid.equals(gridNW))
            guiView.createChooseCardEvent(guiView.findInCards(titleNW.getText()));
        else if (selectedGrid.equals(gridNE))
            guiView.createChooseCardEvent(guiView.findInCards(titleNE.getText()));
        else if (selectedGrid.equals(gridSW))
            guiView.createChooseCardEvent(guiView.findInCards(titleSW.getText()));
        else if (selectedGrid.equals(gridSE))
            guiView.createChooseCardEvent(guiView.findInCards(titleSE.getText()));

        makeUnClickable();
        GUIControllerUtils.makeAlertInfo(SELECTION_MESSAGE);
        //changeScene();

    }

    private void makeUnClickable() {
        gridNW.setDisable(true);
        gridNE.setDisable(true);
        gridSW.setDisable(true);
        gridSE.setDisable(true);
    }

}
