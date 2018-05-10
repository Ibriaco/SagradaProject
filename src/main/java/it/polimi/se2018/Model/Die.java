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


    public Die rollDie(){
        return this;
    }

    public Die reverse(Die d){

        if (d.getValue()==1)
            d.setValue(6);

        else if (d.getValue() == 2)
            d.setValue(5);

        else if (d.getValue() == 3)
            d.setValue(4);

        else if (d.getValue() == 4)
            d.setValue(3);

        else if (d.getValue() == 5)
            d.setValue(2);

        else
            d.setValue(1);

        return d;
    }
}
