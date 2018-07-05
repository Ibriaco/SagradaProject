package it.polimi.se2018.model;

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
        g = new Game(2);
    }

    @Test
    public void testDieCreation() throws InvalidDieException {
        Die d = null;
        d = new Die(g.getColorList());
        d.setColor(Color.BLUE);
        d.setValue(3);


        assertEquals (Color.BLUE,d.getColor());
        assertEquals(3, d.getValue());
    }

    @Test
    public void testDieFailCreation() throws InvalidDieException {
        Die d = null;
        d = new Die(g.getColorList());
        d.setColor(Color.BLUE);
        d.setValue(9);
        fail();
    }

    @Test
    public void testDieReverse() throws InvalidDieException {
        Die d = null;
        d = new Die(g.getColorList());
        d.setColor(Color.BLUE);
        d.setValue(3);

        d.reverse(d);

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
