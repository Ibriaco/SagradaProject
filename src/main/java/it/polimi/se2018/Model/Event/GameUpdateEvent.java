package it.polimi.se2018.Model.Event;

import it.polimi.se2018.View.UI.ViewInterface;

public class GameUpdateEvent implements MVEvent {

    private String username;
    //quando lo creo gli passo come username ALL. così l'evento verrà notificato a tutti i Client
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
