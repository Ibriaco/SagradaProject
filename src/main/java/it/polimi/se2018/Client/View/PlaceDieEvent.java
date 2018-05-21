package it.polimi.se2018.Client.View;


import it.polimi.se2018.Server.Model.Color;

public class PlaceDieEvent extends VCEvent {

    private Color color;
    private int value;
    private int coordX;
    private int coordY;

    public Color getColor(){

        return color;
    }

    public int getValue(){

        return value;
    }

    public int getCoordX() {

        return coordX;
    }

    public int getCoordY() {

        return coordY;
    }
}
