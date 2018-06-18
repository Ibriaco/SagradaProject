package it.polimi.se2018.model.event;

import it.polimi.se2018.view.ui.ViewInterface;

public class GameUpdateEvent implements MVEvent {

    private String username;
    //quando lo creo gli passo come username ALL. così l'evento verrà notificato a tutti i client
    public GameUpdateEvent(String username) {

        this.username = username;
    }

    @Override
    public void accept(ViewInterface vi) {

    }

    @Override
    public String getUsername() {
        return null;
    }


}
