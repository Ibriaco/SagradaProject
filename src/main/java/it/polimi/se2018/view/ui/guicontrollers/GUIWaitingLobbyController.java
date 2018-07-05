package it.polimi.se2018.view.ui.guicontrollers;

import it.polimi.se2018.model.event.PrivateCardEvent;
import it.polimi.se2018.model.event.UpdateGameEvent;
import it.polimi.se2018.view.ui.GUIView;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

/**Controller class of the GUI scene where the client waits for the game to begin. Only contains an animation.
 * @author Gregorio Galletti
 */

public class GUIWaitingLobbyController implements GUIControllerIF{

    private GUIView guiView;
    private static final Logger LOGGER = Logger.getLogger(GUIWaitingLobbyController.class.getName());

    @FXML
    private Pane pane;


    @Override
    public void setView(GUIView vi) {
        guiView = vi;
    }

    @Override
    public void changeScene() {
        /*Not used in this class*/
    }


    public void changeScene(PrivateCardEvent privateCardEvent) {
        Stage stage = (Stage) pane.getScene().getWindow();

        Scene scene = stage.getScene();

        URL url = null;
        try {
            url = new File("src/main/resources/GUIUtils/windowChoice2.fxml").toURI().toURL();
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
        guiView.setGuiChoiceController(loader.getController());
        guiView.getGuiChoiceController().setEvent(privateCardEvent);

        stage.setOnCloseRequest(event -> System.exit(0));
        stage.setHeight(600);
        stage.setWidth(1000);
        stage.centerOnScreen();
        scene.setRoot(root);

    }

}
