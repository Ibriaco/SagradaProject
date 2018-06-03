package it.polimi.se2018.Model;

import org.junit.Before;
import org.junit.Test;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.fail;

/**
 * @author Gregorio Galletti
 * Unit Test Class for the Die class.
 */

public class TestDie {

    Game g;
    @Before
    public void init(){
        try {
            g = new Game(2);
        } catch (InvalidGameCreationException e) {
        }
    }

    @Test
    public void testDieCreation(){
        Die d = null;
        try {
            d = new Die(g.getColorList());
            d.setColor(Color.BLUE);
            d.setValue(3);
        } catch (InvalidDieException e) {
            e.printStackTrace();
        }

        assertEquals (Color.BLUE,d.getColor());
        assertEquals(3, d.getValue());
    }

    @Test
    public void testDieFailCreation(){
        Die d = null;
        try {
            d = new Die(g.getColorList());
            d.setColor(Color.BLUE);
            d.setValue(9);
            fail();
        } catch (InvalidDieException e) {

        }
    }

    @Test
    public void testDieReverse(){
        Die d = null;
        try {
            d = new Die(g.getColorList());
            d.setColor(Color.BLUE);
            d.setValue(3);
        } catch (InvalidDieException e) {
            e.printStackTrace();
        }

        try {
            d.reverse(d);
        } catch (InvalidDieException e) {
            e.printStackTrace();
        }
        assertEquals (Color.BLUE, d.getColor());
        assertEquals(4, d.getValue());
    }

    @Test
    public void testRollDie(){
        Die d = null;
        d = new Die(g.getColorList());

        assert(d.getColor() != null);
        assert(d.getValue() > 0 && d.getValue() < 7);
    }

    //creare test per verificare se vengono tolti realmente i colori disponibili
}
