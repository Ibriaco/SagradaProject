package it.polimi.se2018;

import it.polimi.se2018.Model.Cell;
import it.polimi.se2018.Model.Color;
import it.polimi.se2018.Model.Die;
import it.polimi.se2018.Model.InvalidDieException;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class TestCell {
    @Test
    public void testPlacedCell(){
        Cell c = new Cell(Color.BLUE,3);
        c.placeDie(new Die());

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
        d = new Die();
        c.placeDie(d);

        assert (c.isPlaced());
        assertEquals (d,c.getPlacedDie());
    }

    @Test
    public void testPlaceWrongDie(){
        Cell c = new Cell(Color.BLUE,3);
        Die d = null;
        d = new Die();
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
