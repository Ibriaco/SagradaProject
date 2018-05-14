package it.polimi.se2018.Model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static it.polimi.se2018.Model.Color.*;

public class Die {
    private Color color;
    private int value;
    private int redAmount = 18;
    private int greenAmount = 18;
    private int yellowAmount = 18;
    private int purpleAmount = 18;
    private int blueAmount = 18;
    private boolean init = true;
    List<Color> colorList;

    public Die(Color color, int value) throws InvalidDieException {
        this.color = color;
        colorList = new ArrayList<Color>();

        if(value < 7 && value > 0)
            this.value = value;
        else {
            throw new InvalidDieException();
        }
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

    private void getAvailableColor() {

        if(init) {
            colorList.add(GREEN);
            colorList.add(PURPLE);
            colorList.add(YELLOW);
            colorList.add(RED);
            colorList.add(BLUE);

            init = false;
        }
        else
            checkRemainingColors();
    }

    private void checkRemainingColors() {
        if (redAmount == 1)
            colorList.remove(RED);

        if (blueAmount == 1)
            colorList.remove(BLUE);

        if (greenAmount == 1)
            colorList.remove(GREEN);

        if (yellowAmount == 1)
            colorList.remove(YELLOW);

        if (purpleAmount == 1)
            colorList.remove(PURPLE);

    }

    // da testare
    public Die rollDie(){

        getAvailableColor();

        Random random = new Random();
        int index;

        index = random.nextInt(colorList.size());
        Die d = null;
        try {
            d = new Die(colorList.get(index),random.nextInt(6) + 1);
        } catch (InvalidDieException e) {
            e.printStackTrace();
        }

        switch(d.getColor()){
            case BLUE:
                blueAmount--;
                break;

            case GREEN:
                greenAmount--;
                break;

            case PURPLE:
                purpleAmount--;
                break;

            case RED:
                redAmount--;
                break;

            case YELLOW:
                yellowAmount--;
                break;
        }
        return d;
    }

    public List<Color> getColorList(){

        return colorList;
    }

    // da testare
    public void reverse(Die d){

        d.setValue(7 - d.getValue());
    }
}
