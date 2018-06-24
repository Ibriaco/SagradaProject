package it.polimi.se2018.view.ui.guicontrollers;

import it.polimi.se2018.model.event.PrivateCardEvent;
import it.polimi.se2018.model.event.PublicCardEvent;
import it.polimi.se2018.model.event.ToolCardEvent;
import it.polimi.se2018.model.event.WindowCardEvent;
import it.polimi.se2018.view.ui.GUIView;

public interface GUIControllerIF {

    void setView(GUIView vi);
    void changeScene();

    void changeScene(PrivateCardEvent event);

    void reLogin(String state);

    void setEvent(WindowCardEvent event);
    void setEvent(PrivateCardEvent event);

    void setEvent(PublicCardEvent publicCardEvent);

    void setEvent(ToolCardEvent toolCardEvent);
}
