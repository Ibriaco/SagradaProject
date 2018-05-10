package it.polimi.se2018.Model;

import java.util.Random;

import static it.polimi.se2018.Model.Color.BLUE;

public class Die {
    private Color color;
    private int value;
    private int redAmount = 18;
    private int greenAmount = 18;
    private int yellowAmount = 18;
    private int purpleAmount = 18;
    private int blueAmount = 18;


    public Die(Color color, int value) {
        this.color = color;
        this.value = value;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {

        this.color = color;
    }


    public int getValue() {

        return value;
    }

    public void setValue(int value) {

        this.value = value;
    }

    // da testare
    // bisogna capire come decrementare quei contatori relativi ai colori...
    public Die rollDie(){
        Random rnd = new Random();
        return new Die(Color.values()[rnd.nextInt(Color.values().length)],(int)Math.random()*6 + 1);
    }

    // da testare
    public void reverse(Die d){

        d.setValue(7-d.getValue());
    }
}
