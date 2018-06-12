package it.polimi.se2018.Model.Event;

public class GameUpdateEvent extends MVEvent {

    //quando lo creo gli passo come username ALL. così l'evento verrà notificato a tutti i client
    public GameUpdateEvent(String username) {
        super(username);
    }
}
