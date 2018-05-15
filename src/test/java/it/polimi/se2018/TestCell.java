package it.polimi.se2018;

import it.polimi.se2018.Model.Cell;
import it.polimi.se2018.Model.Color;
import it.polimi.se2018.Model.Die;
import it.polimi.se2018.Model.Game;
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
        c.placeDie(d);

        assert (c.isPlaced());
    }

    @Test
    public void testNotPlacedCell(){
        Cell c = new Cell(Color.BLUE,3);

        assert (!c.isPlaced());
    }

    @Test
    public void testPlaceDie(){
        Cell c = new Cell(Color.BLUE,3);
        Die d = null;
        d = new Die(g.getColorList());
        d.setColor(Color.BLUE);
        c.placeDie(d);

        assert (c.isPlaced());
        assertEquals (d,c.getPlacedDie());
    }

    @Test
    public void testPlaceWrongDie(){
        Cell c = new Cell(Color.BLUE,3);
        Die d = null;
        d = new Die(g.getColorList());
        d.setColor(Color.RED);
        c.placeDie(d);

        assert (!c.isPlaced());
        assertNotEquals (d,(c.getPlacedDie()));
    }

    @Test
    public void testVisitedCell(){
        Cell c = new Cell(Color.BLUE,3);
        c.setVisited(true);
        assert (c.isVisited());
    }

    @Test
    public void testNotVisitedCell(){
        Cell c = new Cell(Color.BLUE,3);
        assert (!c.isVisited());
    }

}
