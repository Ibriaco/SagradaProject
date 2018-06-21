package it.polimi.se2018.view.ui.guicontrollers;

import com.jfoenix.controls.*;
import it.polimi.se2018.MyObserver;
import it.polimi.se2018.model.InvalidConnectionException;
import it.polimi.se2018.model.InvalidViewException;
import it.polimi.se2018.model.event.WindowCardEvent;
import it.polimi.se2018.view.ui.GUIView;
import it.polimi.se2018.view.viewevents.VCEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import static javafx.fxml.FXMLLoader.load;

public class GUILoginController implements GUIControllerIF{

    private ArrayList<MyObserver> observerCollection = new ArrayList<>();
    private VCEvent myEvent;
    private GUIView guiView;
    private Node node;
    private static final Logger LOGGER = Logger.getLogger( GUILoginController.class.getName() );
    private static final String alertMessage = "Insert a valid connection type and username please.";

    @FXML
    JFXButton loginBtn;
    @FXML
    JFXToggleButton rmiBtn;
    @FXML
    JFXToggleButton socketBtn;
    @FXML
    JFXTextField userField;

    @FXML
    public void handleLogin(MouseEvent event) throws InvalidConnectionException, IOException, InvalidViewException, ParseException {
        int c;
        if((rmiBtn.isSelected() || socketBtn.isSelected()) && !userField.getText().equals("")) {

            if (rmiBtn.isSelected())
                c = 1;
            else
                c = 2;

            guiView.createNH(c);

            guiView.setUsername(userField.getText());
            guiView.createLoginEvent();

            //node = (Node) event.getSource();
        }
        else
            makeAlert(alertMessage);
    }

    public void handleToggle(MouseEvent mouseEvent) {
        if(mouseEvent.getSource().equals(rmiBtn)) {
            rmiBtn.setSelected(true);
            socketBtn.setSelected(false);
        }
        else{
            rmiBtn.setSelected(false);
            socketBtn.setSelected(true);
        }
    }

    @Override
    public void changeScene(){
        Stage stage = (Stage) rmiBtn.getScene().getWindow();

        Scene scene = stage.getScene();

        URL url = null;
        try {
            url = new File("src/main/resources/GUIUtils/waitingLobby.fxml").toURI().toURL();
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
        stage.setHeight(540);
        stage.setWidth(960);
        //stage.centerOnScreen();
        scene.setRoot(root);
    }

    @Override
    public void changeScene(WindowCardEvent event) {

    }

    @FXML
    public void reLogin(String message){
        resetScreen();
        makeAlert(message);
    }

    @Override
    public void setEvent(WindowCardEvent event) {

    }

    @Override
    public void setView(GUIView vi) {
        guiView = vi;
    }

    private void resetScreen(){
        loginBtn.setDisableVisualFocus(true);
        rmiBtn.setSelected(false);
        socketBtn.setSelected(false);
        userField.setText("");
    }

    private void makeAlert(String content){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(content);
        alert.showAndWait()
                .filter(response -> response == ButtonType.OK);
    }
}
