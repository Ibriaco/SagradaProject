package it.polimi.se2018.Model;

public class PlaceDieEvent extends Event {
    private Die die;
    private int coordX;
    private int coordY;

    public PlaceDieEvent(Die die, int coordX, int coordY) {
        super();
        this.die = die;
        this.coordX = coordX;
        this.coordY = coordY;
    }

    public Die getDie() {
        return die;
    }

    public int getCoordX() {
        return coordX;
    }

    public int getCoordY() {
        return coordY;
    }
}
