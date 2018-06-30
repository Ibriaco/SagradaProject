package it.polimi.se2018.view.ui.guicontrollers;

import it.polimi.se2018.model.PublicObjective;
import it.polimi.se2018.model.event.PrivateCardEvent;
import it.polimi.se2018.model.event.PublicCardEvent;
import it.polimi.se2018.model.event.ToolCardEvent;
import it.polimi.se2018.model.event.UpdateGameEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

import static it.polimi.se2018.view.ui.guicontrollers.GUIControllerUtils.*;

public class GUIGameScreenController {

    private static final String NOTIFY_TURN = "It's up to you. You have 60 seconds to do your move.";
    private StackPane stack = new StackPane();

    @FXML
    private AnchorPane anchor;

    @FXML
    public void initialize(){
        stack.setLayoutX(100);
        stack.setLayoutY(100);
        anchor.getChildren().add(stack);
    }

    public void showTurnDialog() {
        makeDialog(NOTIFY_TURN, stack, INFO_TYPE);
    }

    public void showCards(PrivateCardEvent privateCardEvent, PublicCardEvent publicCardEvent, ToolCardEvent toolCardEvent) {
        privateCardEvent.printPrivateName();
        publicCardEvent.printPublicName();
        toolCardEvent.printToolCards();
    }

    public void updateScreen(UpdateGameEvent updateGameEvent){

    }
}
