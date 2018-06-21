package it.polimi.se2018.model;

import java.util.logging.Level;
import java.util.logging.Logger;

/**Exception thrown when an unknown value of the die (1, 2, 3, 4, 5, 6) is entered.
 * @author Gregorio Galletti
 */
public class InvalidDieException extends Exception {
    private static final Logger LOGGER = Logger.getLogger(InvalidDieException.class.getName());
    public InvalidDieException(){
        LOGGER.log(Level.SEVERE, "Invalid die!");
    }
}
