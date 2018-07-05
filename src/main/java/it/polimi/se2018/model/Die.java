package it.polimi.se2018.model;

import java.io.Serializable;
import java.util.List;
import java.util.Random;

import static it.polimi.se2018.ServerConfig.ONE_VALUE;
import static it.polimi.se2018.ServerConfig.SIX_VALUE;

/**Die class of the game. Contains methods to handle colors and values of dice.
 * @author Ibrahim El Shemy
 */
public class Die implements Serializable{
    private Color color;
    private int value;

    public Die(List<Color> colorList){

        Random random = new Random();
        int index;
        index = random.nextInt(colorList.size());
        this.color = colorList.get(index);
        this.value = random.nextInt(SIX_VALUE) + ONE_VALUE;

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

    /**
     *This setter method assigns values between 1 and 6 to a certain die. If the value is out of the range, an exception is thrown.
     * @param value Refers to the value of the die.
     * @throws InvalidDieException Makes sure that no invalid die should be created.
     */
    public void setValue(int value) throws InvalidDieException{

        if (value < 1 || value > 6)
            throw new InvalidDieException();
        else
            this.value = value;
    }


    /**
     *This method takes a die as a parameter and sets its reversed value.
     * @param d Refers to the die I want to reverse.
     * @throws InvalidDieException Makes sure that no invalid die should be given to the method.
     */
    public void reverse(Die d) throws InvalidDieException {

        d.setValue(7 - d.getValue());
    }
}
