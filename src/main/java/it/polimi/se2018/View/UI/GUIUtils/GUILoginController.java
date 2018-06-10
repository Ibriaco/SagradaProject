package it.polimi.se2018.View.UI.GUIUtils;

import com.jfoenix.controls.*;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.input.MouseEvent;

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
    public void handleLogin(){
        int c;
        if((rmiBtn.isSelected() || socketBtn.isSelected()) && !userField.getText().equals("")) {
            spin.setVisible(true);
            if (rmiBtn.isSelected())
                c = 1;
            else
                c = 2;
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
