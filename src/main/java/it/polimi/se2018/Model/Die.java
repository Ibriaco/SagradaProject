package it.polimi.se2018.Model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static it.polimi.se2018.Model.Color.*;
/**Die class of the game. Contains methods to handle colors and values of dice.
 * @author Ibrahim El Shemy
 */
public class Die {
    private Color color;
    private int value;


    public Die(List<Color> colorList){

        Random random = new Random();
        int index;
        index = random.nextInt(colorList.size());
        this.color = colorList.get(index);
        this.value = random.nextInt(6) + 1;

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

    public void setValue(int value) throws InvalidDieException{

        if (value < 1 || value > 6)
            throw new InvalidDieException();
        else
            this.value = value;
    }


    // da testare
    public void reverse(Die d) throws InvalidDieException {

        d.setValue(7 - d.getValue());
    }
}
