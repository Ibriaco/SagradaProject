package it.polimi.se2018.Model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestWindowCard {

    WindowCard w;
    Game g;

    @Before
    public void init(){

        g = new Game(1, "single");

        //creo una windowcard con questi specifici valori
        w = new WindowCard();
        w.setCell(new Cell(Color.BLUE,3),0,0);
        w.setCell(new Cell(Color.BLUE,3),0 ,1);
        w.setCell(new Cell(Color.BLUE,3),0,2);
        w.setCell(new Cell(Color.RED,3),0,3);
        w.setCell(new Cell(Color.GREEN,3),0,4);
        w.setCell(new Cell(Color.RED,3),1,0);
        w.setCell(new Cell(Color.YELLOW,3),1,1);
        w.setCell(new Cell(Color.YELLOW,3),1,2);
        w.setCell(new Cell(Color.YELLOW,3),1,3);
        w.setCell(new Cell(Color.GREEN,3),1,4);
        w.setCell(new Cell(Color.YELLOW,3),2,0);
        w.setCell(new Cell(Color.RED,5),2,1);
        w.setCell(new Cell(Color.PURPLE,3),2,2);
        w.setCell(new Cell(Color.BLUE,3),2,3);
        w.setCell(new Cell(Color.GREEN,3),2,4);
        w.setCell(new Cell(Color.BLUE,3),3,0);
        w.setCell(new Cell(Color.BLUE,3),3,1);
        w.setCell(new Cell(Color.BLUE,3),3,2);
        w.setCell(new Cell(Color.GREEN,3),3,3);
        w.setCell(new Cell(Color.GREEN,3),3,4);
    }

    @Test
    public void testFirstPlacement(){

        Die d1 = new Die(g.getColorList());
        try {
            d1.setValue(1);
        } catch (InvalidDieException e) {
        }
        d1.setColor(Color.BLUE);

        //se il primo dado lo voglio mettere sul bordo va bene
        assertEquals(true, w.checkLegalPlacement(d1,0,0));

        //se lo voglio mettere in mezzo non va bene
        assertEquals(false, w.checkLegalPlacement(d1,1,1));
    }

    @Test
    public void testWrongCoordPlacement(){

        Die d1 = new Die(g.getColorList());
        try {
            d1.setValue(1);
        } catch (InvalidDieException e) {
        }
        d1.setColor(Color.BLUE);

        //se il dado lo voglio mettere in coordinate sbagliate allora non va bene
        assertEquals(false, w.checkLegalPlacement(d1,7,2));
    }

    @Test
    public void testFullCardPlacement(){

        Die d1 = new Die(g.getColorList());
        try {
            d1.setValue(1);
        } catch (InvalidDieException e) {
        }
        d1.setColor(Color.BLUE);
    }

    @Test
    public void testAlreadyPlaced(){

        Die d1 = new Die(g.getColorList());
        try {
            d1.setValue(1);
        } catch (InvalidDieException e) {
        }
        d1.setColor(Color.BLUE);

    }

    @Test
    public void testCheckOrtogonal(){

        Die d1 = new Die(g.getColorList());
        try {
            d1.setValue(1);
        } catch (InvalidDieException e) {
        }
        d1.setColor(Color.BLUE);

    }


}
