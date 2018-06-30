package it.polimi.se2018.view.ui.guicontrollers;

import it.polimi.se2018.model.event.PrivateCardEvent;
import it.polimi.se2018.model.event.PublicCardEvent;
import it.polimi.se2018.model.event.ToolCardEvent;
import it.polimi.se2018.model.event.UpdateGameEvent;
import it.polimi.se2018.view.ui.GUIView;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

import static it.polimi.se2018.view.ui.guicontrollers.GUIControllerUtils.*;

public class GUIGameScreenController {

    private static final String MY_TURN = "It's up to you. You have 60 seconds to do your move.";
    private static final String NOT_MY_TURN = "'s turn. Waiting for him to play.";
    private static final String DISCONNECTION_MESSAGE = "disconnected from the game!.";
    private GUIView guiView;
    private StackPane stack = new StackPane();

    @FXML
    private AnchorPane anchor;

    @FXML
    public void initialize(){
        stack.setLayoutX(100);
        stack.setLayoutY(100);
        anchor.getChildren().add(stack);
    }

    public void showTurnDialog(String user) {
        if (user.equals(guiView.getUser()))
            makeDialog(MY_TURN, stack, INFO_TYPE, "");
        else
            makeDialog(NOT_MY_TURN, stack, INFO_TYPE, user);
    }

    public void showCards(PrivateCardEvent privateCardEvent, PublicCardEvent publicCardEvent, ToolCardEvent toolCardEvent) {
        privateCardEvent.printPrivateName();
        publicCardEvent.printPublicName();
        toolCardEvent.printToolCards();



    }

    public void updateScreen(UpdateGameEvent updateGameEvent){

    }

    public void showDisconnectDialog(String user) {
        makeDialog(DISCONNECTION_MESSAGE, stack, INFO_TYPE, user);
    }

}
