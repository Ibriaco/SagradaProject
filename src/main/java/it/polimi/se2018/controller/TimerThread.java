package it.polimi.se2018.controller;


import it.polimi.se2018.model.InvalidConnectionException;
import it.polimi.se2018.model.InvalidViewException;
import it.polimi.se2018.org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import static it.polimi.se2018.ServerConfig.SLEEP_TIME;
import static it.polimi.se2018.ServerConfig.TURN_TIMER;

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
        } catch (InvalidConnectionException | InvalidViewException | IOException e) {
            LOGGER.log(Level.SEVERE, e.toString(), e);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        this.interrupt();
    }



    public int getCont() {
        return cont;
    }

}
