package it.polimi.se2018.view.ui.guicontrollers;


import it.polimi.se2018.model.event.EndGameEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.util.ArrayList;
import java.util.List;

public class GUIEndScreenController {

    private List<Label> playerLabels;

    @FXML
    private Label p1;
    @FXML
    private Label p2;
    @FXML
    private Label p3;
    @FXML
    private Label p4;
    @FXML
    private Label winner;

    @FXML
    public void initialize(){
        playerLabels = new ArrayList<>();
        playerLabels.add(p1);
        playerLabels.add(p2);
        playerLabels.add(p3);
        playerLabels.add(p4);
    }

    public void showScores(EndGameEvent endGameEvent){
        final int[] i = {0};
        endGameEvent.getResults().forEach((s, s2) -> {
            playerLabels.get(i[0]).setText(s + ": " + s2);
            i[0]++;
        });
    }

    public void showWinner(){

    }

}
