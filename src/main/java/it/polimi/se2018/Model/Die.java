package it.polimi.se2018.Model;

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
    public Die rollDie(){
        return this;
    }

    // da testare
    public Die reverse(Die d){

        d.setValue(7-d.getValue());

        return d;
    }
}
