package it.polimi.se2018.view.ui.guicontrollers;

import it.polimi.se2018.view.ui.GUIView;

/**GUI Controller Interface, contains common methods for every controller.
 * @author Gregorio Galletti
 */

public interface GUIControllerIF {

    void setView(GUIView vi);
    void changeScene();
}
