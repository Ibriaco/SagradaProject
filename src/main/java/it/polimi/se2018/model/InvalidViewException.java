package it.polimi.se2018.model;

import java.util.logging.Level;
import java.util.logging.Logger;

/**Exception thrown when an unknown value of the view type (CLI, GUI) is entered.
 * @author Marco Gasperini
 */
public class InvalidViewException extends Exception {
    private static final Logger LOGGER = Logger.getLogger(InvalidViewException.class.getName());
    public InvalidViewException() {
        LOGGER.log(Level.SEVERE, "Invalid view!");
    }
}
