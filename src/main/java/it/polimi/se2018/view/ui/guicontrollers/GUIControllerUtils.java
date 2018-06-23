package it.polimi.se2018.view.ui.guicontrollers;

import it.polimi.se2018.model.Color;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.paint.Paint;

public final class GUIControllerUtils {

    private GUIControllerUtils(){}

    public static void makeAlertError(String content){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(content);
        alert.showAndWait()
                .filter(response -> response == ButtonType.OK);
    }

    public static void makeAlertInfo(String content){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(content);
        alert.showAndWait()
                .filter(response -> response == ButtonType.OK);
    }

    public static Paint getMatch(Color c){

        return javafx.scene.paint.Color.valueOf(c.toString());
    }

}
