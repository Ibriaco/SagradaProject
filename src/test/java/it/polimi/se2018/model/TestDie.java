package it.polimi.se2018.model;

import it.polimi.se2018.ServerParser;
import org.junit.Before;
import org.junit.Test;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotSame;
import static junit.framework.TestCase.fail;

/**
 * @author Gregorio Galletti
 * Unit Test Class for the Die class.
 */

public class TestDie {

    Game g;
    private ServerParser sp = new ServerParser();
    @Before
    public void init(){
        g = new Game(2);
    }


    @Test
    public void testDieCreation() throws InvalidDieException {
        sp.reader(sp.createObj());
        Die d = null;
        d = new Die(g.getColorList());
        d.setColor(Color.BLUE);
        d.setValue(3);


        assertEquals (Color.BLUE,d.getColor());
        assertEquals(3, d.getValue());
    }

    @Test
    public void testDieFailCreation(){
        Die d = null;
        d = new Die(g.getColorList());
        d.setColor(Color.BLUE);
        try {
            d.setValue(9);
        }
        catch(InvalidDieException e){
            assertNotSame(9,d.getValue());
        }
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
