package it.polimi.se2018.controller;


import it.polimi.se2018.model.InvalidConnectionException;
import it.polimi.se2018.model.InvalidViewException;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import static it.polimi.se2018.ServerConfig.*;

public class TimerThread extends Thread{
    private int cont = 0;
    private int playerIndex;
    private EventsController eventsController;
    private static final Logger LOGGER = Logger.getGlobal();

    public TimerThread (EventsController eventsController, int playerIndex){
        this.playerIndex = playerIndex;
        this.eventsController = eventsController;
    }

    @Override
    public void run (){
        while (cont < TURN_TIMER) {
            try {
                Thread.sleep(SLEEP_TIME);
                //System.out.println("player index: " + playerIndex + " timer: " + cont);
                cont++;

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        try {
            eventsController.timerExpired();
        } catch (InvalidConnectionException | ParseException | InvalidViewException | IOException e) {
            LOGGER.log(Level.SEVERE, e.toString(), e);
        }
        this.interrupt();
    }



    public int getCont() {
        return cont;
    }

}
