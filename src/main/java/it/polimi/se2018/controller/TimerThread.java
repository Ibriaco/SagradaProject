package it.polimi.se2018.controller;


import it.polimi.se2018.model.InvalidConnectionException;
import it.polimi.se2018.model.InvalidViewException;
import org.json.simple.parser.ParseException;

import java.io.IOException;

public class TimerThread extends Thread{
    private int cont = 0;
    private int playerIndex;
    private EventsController eventsController;

    public TimerThread (EventsController eventsController, int playerIndex){
        this.playerIndex = playerIndex;
        this.eventsController = eventsController;
    }

    @Override
    public void run (){
        while (cont < 60) {
            try {
                Thread.sleep(1000);
                //System.out.println("player index: " + playerIndex + " timer: " + cont);
                cont++;

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        try {
            eventsController.timerExpired();
        } catch (InvalidConnectionException | ParseException | InvalidViewException | IOException e) {
            e.printStackTrace();
        }
        this.interrupt();
    }



    public int getCont() {
        return cont;
    }

}
