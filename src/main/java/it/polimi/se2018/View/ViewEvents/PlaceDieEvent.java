package it.polimi.se2018.View.ViewEvents;



/**
 * Events that lets the player place a die
 * @author Ibrahim El Shemy
 * @author Marco Gasperini
 */
public class PlaceDieEvent extends VCEvent {


    private int pos;
    private int coordX;
    private int coordY;

    /**
     *
     * @param username username of the current player
     * @param pos is the position of die in the DraftPool
     * @param x x coordinate where the player wants to place the die
     * @param y y coordinate where the player wants to place the die
     */
    public PlaceDieEvent(String username, int pos, int x, int y) {

        super(username);

        this.pos = pos;
        this.coordX = x;
        this.coordY = y;
    }


    public int getPos(){

        return pos;
    }

    public int getCoordX() {

        return coordX;
    }

    public int getCoordY() {

        return coordY;
    }
}
