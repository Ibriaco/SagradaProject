package it.polimi.se2018.model;

import it.polimi.se2018.ServerParser;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
/**
 * @author Gregorio Galletti
 * Unit Test Class for the Cell class.
 */
public class TestCell {

    private Game g;
    ServerParser sp = new ServerParser();
    @Before
    public void init(){
        sp.reader();
        g = new Game(2);
    }

    @Test
    public void testPlacedCell(){
        Cell c = new Cell(Color.WHITE,3);
        Die d = new Die(g.getColorList());
        d.setColor(Color.BLUE);
        try {
            d.setValue(3);
        }
        catch(InvalidDieException e){
            e.printStackTrace();
        }
        c.placeDie(d);
        boolean placed = c.isPlaced();

        assert (placed);
    }

    @Test
    public void testNotPlacedCell(){
        Cell c = new Cell(Color.BLUE,3);
        boolean placed = c.isPlaced();

        assert (!placed);
    }

    @Test
    public void testPlaceDie(){
        Cell c = new Cell(Color.BLUE,0);
        Die d = new Die(g.getColorList());
        d.setColor(Color.BLUE);

        c.placeDie(d);
        boolean placed = c.isPlaced();
        assert (placed);

        assertEquals (d,c.getPlacedDie());
    }

    @Test
    public void testPlaceWrongDie(){
        Cell c = new Cell(Color.BLUE,0);
        Die d =  new Die(g.getColorList());
        d.setColor(Color.RED);

        c.placeDie(d);
        boolean placed = c.isPlaced();
        //assert(!placed);
        assertEquals(true, placed);
        assertEquals (d,(c.getPlacedDie()));
    }

    @Test
    public void testVisitedCell(){
        Cell c = new Cell(Color.BLUE,0);
        c.setVisited(true);
        assert (c.isVisited());
    }

    @Test
    public void testNotVisitedCell(){
        Cell c = new Cell(Color.BLUE,0);
        assert (!c.isVisited());
    }

}
