package it.polimi.se2018.Client.View;

import it.polimi.se2018.Server.Model.Color;
//evento per selezionare un dado
public class SelectDieEvent extends VCEvent{
    private Color color;
    private int value;
    private int coordX;
    private int coordY;

    public void SelectDieEvent(Color color,int value,int x,int y){
        this.color = color;
        this.value = value;
        this.coordX = x;
        this.coordY = y;
    }

    public Color getColor() {
        return color;
    }

    public int getValue() {
        return value;
    }

    public int getCoordX() {
        return coordX;
    }

    public int getCoordY() {
        return coordY;
    }
}
