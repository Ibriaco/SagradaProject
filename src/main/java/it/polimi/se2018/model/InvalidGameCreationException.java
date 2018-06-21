package it.polimi.se2018.model;

import java.util.logging.Level;
import java.util.logging.Logger;

public class InvalidGameCreationException extends Exception{
    private static final Logger LOGGER = Logger.getLogger(InvalidGameCreationException.class.getName());
    public InvalidGameCreationException() {
        LOGGER.log(Level.SEVERE, "Cannot create game!");
    }
}
