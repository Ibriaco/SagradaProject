package it.polimi.se2018.view.ui.guicontrollers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import it.polimi.se2018.model.Color;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;

public final class GUIControllerUtils {

    private GUIControllerUtils(){}

    public static void makeAlertError(String content, StackPane pane){

        JFXDialogLayout layout = new JFXDialogLayout();
        layout.setHeading(new Text("ERROR"));
        layout.setBody(new Text(content));
        JFXDialog dialog = new JFXDialog(pane, layout, JFXDialog.DialogTransition.CENTER);
        JFXButton button = new JFXButton("CLOSE");
        button.setBackground(new Background(new BackgroundFill(javafx.scene.paint.Color.rgb(44,62,80), null, null)));
        button.setTextFill(javafx.scene.paint.Color.WHITE);
        button.setOnAction(event -> dialog.close());
        layout.setActions(button);
        dialog.show();

        /*
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(content);
        alert.showAndWait()
                .filter(response -> response == ButtonType.OK);
        */
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
