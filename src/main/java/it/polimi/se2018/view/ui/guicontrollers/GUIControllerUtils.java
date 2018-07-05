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

/**Final class that contains some helpful methods used in the GUI controllers.
 * @author Gregorio Galletti
 */

public final class GUIControllerUtils {

    private GUIControllerUtils(){}

    public static final String ERROR_TYPE = "ERROR";
    public static final String INFO_TYPE = "INFO";
    public static final String EXIT_MESSAGE = "Are you sure you want to close the game?";

    public static void makeDialog(String content, StackPane pane, String type, String data){

        JFXDialogLayout layout = new JFXDialogLayout();
        if(data.equals(""))
            layout.setBody(new Text(content));
        else
            layout.setBody(new Text(data + " " + content));
        JFXDialog dialog = new JFXDialog(pane, layout, JFXDialog.DialogTransition.CENTER);
        JFXButton button = new JFXButton("CLOSE");
        button.setBackground(new Background(new BackgroundFill(javafx.scene.paint.Color.rgb(44,62,80), null, null)));
        button.setTextFill(javafx.scene.paint.Color.WHITE);
        button.setOnAction(event -> dialog.close());
        layout.setActions(button);

        layout.setHeading(new Text(type));

        dialog.show();
    }

    public static Paint getMatch(Color c){

        return javafx.scene.paint.Color.valueOf(c.toString());
    }

}
