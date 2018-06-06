package it.polimi.se2018.View;

import java.io.Serializable;

/**
 * Class of the events that occur between ViewInterface and Controller.
 * @author Ibrahim El Shemy
 * @author Marco Gasperini
 */
public abstract class VCEvent implements Serializable {

    static final long serialVersionUID = 42L;

    private String username;

    /**
     * @param username username of the current player
     */
    public VCEvent(String username) {

        this.username = username;
    }

    public String getUsername() {

        return username;
    }
}
