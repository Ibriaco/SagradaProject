package it.polimi.se2018.Model;


import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
/**
 * @author Gregorio Galletti
 * Unit Test Class for the Cell class.
 */
public class TestCell {

    Game g;
    @Before
    public void init(){
        g = new Game(1, "single");
    }

    @Test
    public void testPlacedCell(){
        Cell c = new Cell(Color.BLUE,3);
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
        Die d;
        d = new Die(g.getColorList());
        d.setColor(Color.RED);
        c.placeDie(d);
        boolean placed = c.isPlaced();

        assert (!placed);
        assertNotEquals (d,(c.getPlacedDie()));
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
