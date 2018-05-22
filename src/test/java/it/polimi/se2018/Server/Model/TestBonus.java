package it.polimi.se2018.Server.Model;


import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author Gregorio Galletti
 * @author Marco Gasperini
 * Unit Test Class for all the Public Cards of the game.
 */

public class TestBonus {
    WindowCard w;
    Player p;
    Game g;

    @Before
    public void init() {

        try {
            g = new Game(2);
        } catch (InvalidGameCreationException e) {
        }

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

        try {
            //piazzo alcuni dadi per poter calcolare i vari bonus
            Die d1 = new Die(g.getColorList());
            Die d2 = new Die(g.getColorList());
            Die d3 = new Die(g.getColorList());
            Die d4 = new Die(g.getColorList());
            Die d5 = new Die(g.getColorList());
            Die d6 = new Die(g.getColorList());
            Die d7 = new Die(g.getColorList());
            Die d8 = new Die(g.getColorList());
            Die d9 = new Die(g.getColorList());
            Die d10 = new Die(g.getColorList());
            Die d11 = new Die(g.getColorList());
            Die d12 = new Die(g.getColorList());
            Die d13 = new Die(g.getColorList());

            d1.setValue(1);
            d1.setColor(Color.BLUE);
            w.getGridCell(0,0).setPlacedDie(d1);

            d2.setValue(1);
            d2.setColor(Color.BLUE);
            w.getGridCell(0,1).setPlacedDie(d2);

            d3.setValue(3);
            d3.setColor(Color.BLUE);
            w.getGridCell(2,3).setPlacedDie(d3);

            d4.setValue(1);
            d4.setColor(Color.RED);
            w.getGridCell(0,3).setPlacedDie(d4);

            d5.setValue(6);
            d5.setColor(Color.RED);
            w.getGridCell(2,1).setPlacedDie(d5);

            d6.setValue(1);
            d6.setColor(Color.YELLOW);
            w.getGridCell(1,3).setPlacedDie(d6);

            d7.setValue(2);
            d7.setColor(Color.YELLOW);
            w.getGridCell(2,0).setPlacedDie(d7);

            d8.setValue(1);
            d8.setColor(Color.PURPLE);
            w.getGridCell(2,2).setPlacedDie(d8);

            d9.setValue(1);
            d9.setColor(Color.GREEN);
            w.getGridCell(3,3).setPlacedDie(d9);

            d10.setValue(6);
            d10.setColor(Color.GREEN);
            w.getGridCell(3,4).setPlacedDie(d10);

            d11.setValue(3);
            d11.setColor(Color.GREEN);
            w.getGridCell(0,4).setPlacedDie(d11);

            d12.setValue(5);
            d12.setColor(Color.GREEN);
            w.getGridCell(2,4).setPlacedDie(d12);

            d13.setValue(4);
            d13.setColor(Color.GREEN);
            w.getGridCell(1,4).setPlacedDie(d13);

        } catch (InvalidDieException e) {
            e.printStackTrace();
        }
        //creo un player e gli assegno la windowcard creata
        try {
            p = new Player("test", "Socket", "CLI");
        }
        catch (InvalidConnectionException | InvalidViewException e) {}
        p.setWindowCard(w);

        //creo un game e aggiungo il player appena creato
        g.addPlayer(p);
    }

    @Test
    public void testLightShades(){
        //metto nel game una sola public objective, cioe lightshades, e la testo
        //con i dadi inseriti prima, valore atteso è:

        g.getPublicCards().set(0, new LightShade(1, "LightShades", "a", 2 ));
        g.getPublicCards().get(0).calculateBonus(p);

        assertEquals (2,p.getPlayerScore());
    }

    @Test
    public void testMediumShades(){
        //metto nel game una sola public objective, cioe mediumshades, e la testo
        //con i dadi inseriti prima, valore atteso è:

        g.getPublicCards().set(0, new MediumShade(2, "MediumShades", "a", 2 ));
        g.getPublicCards().get(0).calculateBonus(p);

        assertEquals (2,p.getPlayerScore());
    }

    @Test
    public void testDeepShades(){
        //metto nel game una sola public objective, cioe deepshade, e la testo
        //con i dadi inseriti prima, valore atteso è:

        g.getPublicCards().set(0, new DeepShade(3, "DeepShade", "Sets of one of each color anywhere", 2 ));
        g.getPublicCards().get(0).calculateBonus(p);

        assertEquals (2,p.getPlayerScore());
    }


    @Test
    public void testColorVariety(){
        //metto nel game una sola public objective, cioe colorvariey, e la testo
        //con i dadi inseriti prima, valore atteso è:

        g.getPublicCards().set(0, new ColorBonus(4, "ColorVariey", "Sets of one of each color anywhere", 4 ));
        g.getPublicCards().get(0).calculateBonus(p);

        assertEquals (4,p.getPlayerScore());
    }

    @Test
    public void testShadeVariety(){
        //metto nel game una sola public objective, cioe shadevariety, e la testo
        //con i dadi inseriti prima, valore atteso è:

        g.getPublicCards().set(0, new Shade(5, "ShadeVariety", "Sets of one of each color anywhere", 5 ));
        g.getPublicCards().get(0).calculateBonus(p);

        assertEquals (5,p.getPlayerScore());
    }

    @Test
    public void testColumnShadeVariety(){
        //metto nel game una sola public objective, cioe columnshade, e la testo
        //con i dadi inseriti prima, valore atteso è:

        g.getPublicCards().set(0, new ColumnShade(6, "ColumnShadeVariety", "Sets of one of each color anywhere", 4 ));
        g.getPublicCards().get(0).calculateBonus(p);

        assertEquals (4,p.getPlayerScore());
    }

    @Test
    public void testRowShadeVariety(){
        //metto nel game una sola public objective, cioe rowshade, e la testo
        //con i dadi inseriti prima, valore atteso è:

        g.getPublicCards().set(0, new RowShade(7, "RowShadeVariety", "Sets of one of each color anywhere", 5 ));
        g.getPublicCards().get(0).calculateBonus(p);

        assertEquals (5,p.getPlayerScore());
    }

    @Test
    public void testRowColorVariety(){
        //metto nel game una sola public objective, cioe rowcolor, e la testo
        //con i dadi inseriti prima, valore atteso è:

        g.getPublicCards().set(0, new RowColor(8, "RowColorVariety", "Sets of one of each color anywhere", 6 ));
        g.getPublicCards().get(0).calculateBonus(p);

        assertEquals (6, p.getPlayerScore());
    }

    @Test
    public void testColumnColorVariety(){
        //metto nel game una sola public objective, cioe columncolor, e la testo
        //con i dadi inseriti prima, valore atteso è:

        g.getPublicCards().set(0, new ColumnColor(9, "ColumnColorVariety", "Sets of one of each color anywhere", 5 ));
        g.getPublicCards().get(0).calculateBonus(p);

        assertEquals (5,p.getPlayerScore());
    }

    @Test
    public void testColorDiagonals(){
        //metto nel game una sola public objective, cioe colordiagonals, e la testo
        //con i dadi inseriti prima, valore atteso è:

        g.getPublicCards().set(0, new ColorDiagonals(10, "ColorDiagonals", "Sets of one of each color anywhere", 0 ));
        g.getPublicCards().get(0).calculateBonus(p);

        assertEquals (2,p.getPlayerScore());
    }

}
