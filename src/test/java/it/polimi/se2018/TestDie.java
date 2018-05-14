package it.polimi.se2018;

import it.polimi.se2018.Model.Color;
import it.polimi.se2018.Model.Die;
import it.polimi.se2018.Model.InvalidDieException;
import org.junit.Test;

import static junit.framework.TestCase.fail;

public class TestDie {

    @Test
    public void testDieCreation(){
        Die d = null;
        try {
            d = new Die(Color.BLUE, 3);
        } catch (InvalidDieException e) {
            e.printStackTrace();
        }

        assert (d.getColor() == Color.BLUE && d.getValue() == 3);
    }

    @Test
    public void testDieFailCreation(){
        Die d = null;
        try {
            d = new Die(Color.BLUE, 9);
            fail();
        } catch (InvalidDieException e) {

        }
    }

    @Test
    public void testDieReverse(){
        Die d = null;
        try {
            d = new Die(Color.BLUE, 3);
        } catch (InvalidDieException e) {
            e.printStackTrace();
        }

        d.reverse(d);
        assert (d.getColor() == Color.BLUE && d.getValue() == 4);
    }

    @Test
    public void testRollDie(){
        Die d = null;
        try {
            d = new Die(Color.BLUE, 3);
        } catch (InvalidDieException e) {
            e.printStackTrace();
        }

        d = d.rollDie();

        assert(d.getColor() != null);
        assert(d.getValue() > 0 && d.getValue() < 7);
    }

    //creare test per verificare se vengono tolti realmente i colori disponibili
}
