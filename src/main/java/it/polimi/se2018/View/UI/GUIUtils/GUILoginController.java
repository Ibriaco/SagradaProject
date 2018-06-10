package it.polimi.se2018.View.UI.GUIUtils;

import com.jfoenix.controls.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class GUILoginController {

    @FXML
    JFXButton loginBtn;
    @FXML
    JFXSpinner spin;
    @FXML
    JFXToggleButton rmiBtn;
    @FXML
    JFXToggleButton socketBtn;
    @FXML
    JFXTextField userField;

    @FXML
    public void handleLogin(MouseEvent event){
        int c;
        if((rmiBtn.isSelected() || socketBtn.isSelected()) && !userField.getText().equals("")) {
            spin.setVisible(true);
            if (rmiBtn.isSelected())
                c = 1;
            else
                c = 2;

            Node node = (Node) event.getSource();
            Stage stage = (Stage) node.getScene().getWindow();
            Scene scene = stage.getScene();
            URL url = null;
            try {
                url = new File("src/main/java/it/polimi/se2018/View/UI/GUIUtils/waitingLobby.fxml").toURI().toURL();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            Parent root = null;
            try{root = FXMLLoader.load(url);
            } catch (IOException e) {
                e.printStackTrace();
            }
            stage.setHeight(540);
            stage.setWidth(960);
            scene.setRoot(root);
        }
        else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Insert a valid connection type and username please.");
            alert.showAndWait()
                    .filter(response -> response == ButtonType.OK);
        }
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
}
