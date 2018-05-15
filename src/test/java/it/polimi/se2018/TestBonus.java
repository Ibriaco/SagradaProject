package it.polimi.se2018;

import it.polimi.se2018.Model.*;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestBonus {
    WindowCard w;
    Player p;
    Game g;

    @Before
    public void init() {

        g = new Game(1, "single");

        //creo una windowcard con questi specifici valori
        w = new WindowCard();
        w.setCell(new Cell(Color.BLUE,3),0,0);
        w.setCell(new Cell(Color.BLUE,3),0 ,1);
        w.setCell(new Cell(Color.BLUE,3),0,2);
        w.setCell(new Cell(Color.RED,3),0,3);
        w.setCell(new Cell(Color.RED,3),0,4);
        w.setCell(new Cell(Color.RED,3),1,0);
        w.setCell(new Cell(Color.YELLOW,3),1,1);
        w.setCell(new Cell(Color.YELLOW,3),1,2);
        w.setCell(new Cell(Color.YELLOW,3),1,3);
        w.setCell(new Cell(Color.YELLOW,3),1,4);
        w.setCell(new Cell(Color.YELLOW,3),2,0);
        w.setCell(new Cell(Color.RED,3),2,1);
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

            d1.setValue(1);
            d1.setColor(Color.BLUE);
            w.placeDie(d1, 0, 0);

            d2.setValue(1);
            d2.setColor(Color.BLUE);
            w.placeDie(d2, 0, 1);

            d3.setValue(3);
            d3.setColor(Color.BLUE);
            w.placeDie(d3, 2, 3);

            d4.setValue(1);
            d4.setColor(Color.RED);
            w.placeDie(d4, 0, 3);

            d5.setValue(2);
            d5.setColor(Color.RED);
            w.placeDie(d5, 2, 1);

            d6.setValue(1);
            d6.setColor(Color.YELLOW);
            w.placeDie(d6, 1, 3);

            d7.setValue(2);
            d7.setColor(Color.YELLOW);
            w.placeDie(d7, 2, 0);

            d8.setValue(1);
            d8.setColor(Color.PURPLE);
            w.placeDie(d8, 2, 2);

            d9.setValue(1);
            d9.setColor(Color.GREEN);
            w.placeDie(d9, 3, 3);

            d10.setValue(6);
            d10.setColor(Color.GREEN);
            w.placeDie(d10, 2, 4);
        } catch (InvalidDieException e) {
            e.printStackTrace();
        }
        //creo un player e gli assegno la windowcard creata
        try {
            p = new Player("test", "Socket", "CLI");
        } catch (InvalidConnectionException e) {
            e.printStackTrace();
        } catch (InvalidViewException e) {
            e.printStackTrace();
        }
        p.setWindowCard(w);

        //creo un game e aggiungo il player appena creato
        g.addPlayer(p);
    }

    @Test
    public void testLightShades(){
        //metto nel game una sola public objective, cioe lightshades, e la testo
        //con i dadi inseriti prima, valore atteso è:

        g.getPublicCards().set(0, new LightShade(1, "LightShades", "a","PublicObjective", 2 ));
        g.getPublicCards().get(0).calculateBonus(p);

        assertEquals (4,p.getPlayerScore());
    }

    @Test
    public void testMediumShades(){
        //metto nel game una sola public objective, cioe mediumshades, e la testo
        //con i dadi inseriti prima, valore atteso è:

        g.getPublicCards().set(0, new MediumShade(2, "MediumShades", "a","PublicObjective", 2 ));
        g.getPublicCards().get(0).calculateBonus(p);

        assertEquals (0,p.getPlayerScore());
    }

    @Test
    public void testDeepShades(){
        //metto nel game una sola public objective, cioe deepshade, e la testo
        //con i dadi inseriti prima, valore atteso è:

        g.getPublicCards().set(0, new DeepShade(3, "DeepShade", "Sets of one of each color anywhere","PublicObjective", 2 ));
        g.getPublicCards().get(0).calculateBonus(p);

        assertEquals (0,p.getPlayerScore());
    }


    @Test
    public void testColorVariey(){
        //metto nel game una sola public objective, cioe colorvariey, e la testo
        //con i dadi inseriti prima, valore atteso è:

        g.getPublicCards().set(0, new ColorBonus(4, "ColorVariey", "Sets of one of each color anywhere","PublicObjective", 4 ));
        g.getPublicCards().get(0).calculateBonus(p);

        assertEquals (4,p.getPlayerScore());
    }

    @Test
    public void testShadeVariety(){
        //metto nel game una sola public objective, cioe shadevariety, e la testo
        //con i dadi inseriti prima, valore atteso è:

        g.getPublicCards().set(0, new Shade(5, "ShadeVariety", "Sets of one of each color anywhere","PublicObjective", 5 ));
        g.getPublicCards().get(0).calculateBonus(p);

        assertEquals (0,p.getPlayerScore());
    }

    @Test
    public void testColumnShadeVariety(){
        //metto nel game una sola public objective, cioe columnshade, e la testo
        //con i dadi inseriti prima, valore atteso è:

        g.getPublicCards().set(0, new ColumnShade(6, "ColumnShadeVariety", "Sets of one of each color anywhere","PublicObjective", 4 ));
        g.getPublicCards().get(0).calculateBonus(p);

        assertEquals (0,p.getPlayerScore());
    }

    @Test
    public void testRowShadeVariety(){
        //metto nel game una sola public objective, cioe rowshade, e la testo
        //con i dadi inseriti prima, valore atteso è:

        g.getPublicCards().set(0, new RowShade(7, "RowShadeVariety", "Sets of one of each color anywhere","PublicObjective", 5 ));
        g.getPublicCards().get(0).calculateBonus(p);

        assertEquals (0,p.getPlayerScore());
    }

    @Test
    public void testRowColorVariety(){
        //metto nel game una sola public objective, cioe rowcolor, e la testo
        //con i dadi inseriti prima, valore atteso è:

        g.getPublicCards().set(0, new RowColor(8, "RowColorVariety", "Sets of one of each color anywhere","PublicObjective", 6 ));
        g.getPublicCards().get(0).calculateBonus(p);

        assertEquals (6, p.getPlayerScore());
    }

    @Test
    public void testColumnColorVariety(){
        //metto nel game una sola public objective, cioe columncolor, e la testo
        //con i dadi inseriti prima, valore atteso è:

        g.getPublicCards().set(0, new ColumnColor(9, "ColumnColorVariety", "Sets of one of each color anywhere","PublicObjective", 5 ));
        g.getPublicCards().get(0).calculateBonus(p);

        assertEquals (5,p.getPlayerScore());
    }

    @Test
    public void testColorDiagonals(){
        //metto nel game una sola public objective, cioe colordiagonals, e la testo
        //con i dadi inseriti prima, valore atteso è:

        g.getPublicCards().set(0, new ColorDiagonals(10, "ColorDiagonals", "Sets of one of each color anywhere","PublicObjective", 0 ));
        g.getPublicCards().get(0).calculateBonus(p);

        System.out.println(p.getPlayerScore());
        assertEquals (2,p.getPlayerScore());
    }

}
