package it.polimi.se2018;

import it.polimi.se2018.Model.Cell;
import it.polimi.se2018.Model.Color;
import it.polimi.se2018.Model.Die;
import it.polimi.se2018.Model.InvalidDieException;
import org.junit.Test;

public class TestCell {
    @Test
    public void testPlacedCell(){
        Cell c = new Cell(Color.BLUE,3);
        try {
            c.placeDie(new Die(Color.BLUE, 3));
        } catch (InvalidDieException e) {

        }
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
        try {
            d = new Die(Color.BLUE, 2);
        } catch (InvalidDieException e) {
            e.printStackTrace();
        }

        c.placeDie(d);

        assert (c.isPlaced());
        assert (c.getPlacedDie() == d);
    }

    @Test
    public void testPlaceWrongDie(){
        Cell c = new Cell(Color.BLUE,3);
        Die d = null;
        try {
            d = new Die(Color.GREEN, 2);
        } catch (InvalidDieException e) {
            e.printStackTrace();
        }

        c.placeDie(d);

        assert (!c.isPlaced());
        assert (!(c.getPlacedDie() == d));
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
