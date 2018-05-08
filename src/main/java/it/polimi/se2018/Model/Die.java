package it.polimi.se2018.Model;

public class Die {
    private Color color;
    private int value;
    private int redAmount;
    private int greenAmount;
    private int yellowAmount;
    private int purpleAmount;
    private int blueAmount;


    public Die(Color color, int value) {
        this.color = color;
        this.value = value;
    }

    public Color getColor() {
        return color;
    }

    public int getValue() {
        return value;
    }

    public Die rollDie(){

    }

    public Die reverse(Die d){

    }
}
