package it.polimi.se2018.view.ui.guicontrollers;

import it.polimi.se2018.model.WindowCard;
import it.polimi.se2018.model.Color;
import it.polimi.se2018.model.event.WindowCardEvent;
import it.polimi.se2018.view.ui.GUIView;
import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;


public class GUIChoiceController implements GUIControllerIF {

    private static final double R_HEIGHT = 50;
    private static final double R_WIDTH = 50;

    private GUIView guiView;
    private WindowCardEvent event;

    @FXML
    private GridPane gridNW;
    @FXML
    private GridPane gridNE;
    @FXML
    private GridPane gridSW;
    @FXML
    private GridPane gridSE;

    private int index = 0;

    public void setEvent(WindowCardEvent event){
        this.event = event;
    }

    @FXML
    public void initialize(){
        /*
        for (WindowCard w : event.getWindowCards()){
            showCard(w, index);
            index++;
        }
        */
    }

    private void showCard(WindowCard windowCard, int index) {
        if(index == 0) {
            for (int i = 0; i < windowCard.getRows(); i++) {
                for (int j = 0; j < windowCard.getCols(); j++) {
                    gridNW.add(new Rectangle(R_WIDTH, R_HEIGHT, getMatch(windowCard.getGridCell(i, j).getColor())), i, j);
                }
            }
        }
    }

    private Paint getMatch(Color c){

        return javafx.scene.paint.Color.valueOf(c.toString());
    }


    @Override
    public void setView(GUIView vi) {
        guiView = vi;
    }

    @Override
    public void changeScene() {

    }

    @Override
    public void changeScene(WindowCardEvent event) {

    }

    @Override
    public void reLogin(String state) {

    }
}
