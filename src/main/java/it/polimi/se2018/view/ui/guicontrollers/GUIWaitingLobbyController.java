package it.polimi.se2018.view.ui.guicontrollers;

import it.polimi.se2018.model.WindowCard;
import it.polimi.se2018.model.event.WindowCardEvent;
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

    }

    @Override
    public void changeScene(WindowCardEvent event) {
        Stage stage = (Stage) pane.getScene().getWindow();

        Scene scene = stage.getScene();

        URL url = null;
        try {
            url = new File("src/main/resources/GUIUtils/windowChoice.fxml").toURI().toURL();
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
        guiView.addGUIController(loader.getController());
        guiView.getControllerList().get(2).setEvent(event);

        stage.setHeight(624);
        stage.setWidth(900);
        stage.centerOnScreen();
        scene.setRoot(root);

    }

    @Override
    public void reLogin(String state) {
        /*Intentionally left void, not used.*/
    }

    @Override
    public void setEvent(WindowCardEvent event) {

    }
}
