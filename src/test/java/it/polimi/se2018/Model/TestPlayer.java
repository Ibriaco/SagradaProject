package it.polimi.se2018.Model;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Gregorio Galletti
 * @author Marco Gasperini
 * Unit Test Class for the Player class.
 */
public class TestPlayer {
    @Test
    public void testNewPlayer(){
        try {
            Player p = new Player("ingsw", "CLI");
        } catch (InvalidConnectionException | InvalidViewException e) {
            fail();
        }
    }

    /*@Test
    public void testNewWrongConnectionPlayer(){
        try {
            Player p = new Player("ingsw", "CLI");
            fail();
        } catch (InvalidConnectionException | InvalidViewException e) { }
    }*/
    @Test
    public void testNewWrongViewPlayer(){
        try {
            Player p = new Player("ingsw", "GLI");
            fail();
        } catch (InvalidConnectionException | InvalidViewException e) { }
    }

    @Test
    public void testSetTokens(){
        Player p = null;
        try {
            p = new Player("ingsw", "CLI");
        } catch (InvalidConnectionException | InvalidViewException e) {
            fail();
        }
        p.setTokens(5);
        p.reduceTokens(3);

        assertEquals(2,p.getTokens());
    }

    @Test
    public void testSetTokensFail(){
        Player p = null;
        try {
            p = new Player("ingsw", "CLI");
        } catch (InvalidConnectionException | InvalidViewException e) {
            fail();
        }
        p.setTokens(4);
        p.reduceTokens(5);

        assertEquals(4,p.getTokens());
        assertNotEquals(-1,p.getTokens());
    }

    @Test
    public void testChoice(){
        WindowCard w1 = new WindowCard();
        WindowCard w2 = new WindowCard();
        WindowCardAssociation a = null;
        try {
            a = new WindowCardAssociation(w1, w2);
        } catch (WindowCardAssociationException e) {
            e.printStackTrace();
        }
        Player p = null;
        try {
            p = new Player("ingsw", "CLI");
        } catch (InvalidConnectionException | InvalidViewException e) {
            fail();
        }

        p.chooseWindowCard(a,0);
        assertEquals(w1,p.getWindowCard());
    }

    @Test
    public void testWrongChoice(){
        WindowCard w1 = new WindowCard();
        WindowCard w2 = new WindowCard();
        WindowCardAssociation a = null;
        try {
            a = new WindowCardAssociation(w1, w2);
        } catch (WindowCardAssociationException e) {
           fail();
        }
        Player p = null;
        try {
            p = new Player("ingsw", "CLI");
        } catch (InvalidConnectionException | InvalidViewException e) {
            fail();
        }

        p.chooseWindowCard(a,4);
        assertNotEquals(w1,p.getWindowCard());
        assertNotEquals(w2,p.getWindowCard());


    }

    @Test
    public void testSetPlayerScore(){
        Player p= null;
        try {
            p = new Player("player", "CLI");
        } catch (InvalidConnectionException | InvalidViewException e) {
            fail();
        }
        p.setPlayerScore(5);
        assertEquals(5,p.getPlayerScore());
        p.setPlayerScore(5);
        assertEquals(10,p.getPlayerScore());
    }

    @Test
    public void testReduceTokens(){
        Player p= null;
        try {
            p = new Player("player", "CLI");
        } catch (InvalidConnectionException | InvalidViewException e) {
            fail();
        }
        p.setTokens(10);
        p.reduceTokens(3);
        assertEquals(7,p.getTokens());
        p.reduceTokens(8);
        assertEquals(7,p.getTokens());
    }

    @Test
    public void testDrawCard(){
        Player p= null;
        try {
            p = new Player("player", "CLI");
        } catch (InvalidConnectionException | InvalidViewException e) {
            fail();
        }
        p.drawCard(1);
        assertEquals(1,p.getPrivateObjective().getScore());
        assertEquals(Color.RED,p.getPrivateObjective().getColor());
        p.drawCard(2);
        assertEquals(1,p.getPrivateObjective().getScore());
        assertEquals(Color.YELLOW,p.getPrivateObjective().getColor());
        p.drawCard(3);
        assertEquals(1,p.getPrivateObjective().getScore());
        assertEquals(Color.GREEN,p.getPrivateObjective().getColor());
        p.drawCard(4);
        assertEquals(1,p.getPrivateObjective().getScore());
        assertEquals(Color.BLUE,p.getPrivateObjective().getColor());
        p.drawCard(5);
        assertEquals(1,p.getPrivateObjective().getScore());
        assertEquals(Color.PURPLE,p.getPrivateObjective().getColor());
    }

    @Test
    public void testWrongDrawCard(){
        Player p = null;
        try {
            p = new Player("player", "CLI");
        } catch (InvalidConnectionException | InvalidViewException e) {
            fail();
        }
        p.drawCard(6);
        assertEquals(null,p.getPrivateObjective());
    }

    @Test
    public void testDrawWindowCardAssociation(){
        Player p = null;
        Cell c;
        try {
            p = new Player("player", "CLI");
        } catch (InvalidConnectionException | InvalidViewException e) {
            fail();
        }
        p.drawWindowCardAssociation(1,2);
        c = new Cell(Color.YELLOW,0);

        assertEquals("Kaleidoscopic Dream",p.getWindowCardAssociations()[0].getFront().getWindowName());
        assertEquals(4,p.getWindowCardAssociations()[0].getFront().getDifficulty());
        assertEquals("Firmitas",p.getWindowCardAssociations()[0].getBack().getWindowName());
        assertEquals(5,p.getWindowCardAssociations()[0].getBack().getDifficulty());
        assertEquals("Fractal Drops",p.getWindowCardAssociations()[1].getFront().getWindowName());
        assertEquals(3,p.getWindowCardAssociations()[1].getFront().getDifficulty());
        assertEquals("Ripples of Light",p.getWindowCardAssociations()[1].getBack().getWindowName());
        assertEquals(5,p.getWindowCardAssociations()[1].getBack().getDifficulty());

        assertEquals(Color.YELLOW,p.getWindowCardAssociations()[0].getFront().getGridCell(0,0).getColor());
        assertEquals(2,p.getWindowCardAssociations()[0].getFront().getGridCell(3,0).getShade());
        assertEquals(Color.PURPLE,p.getWindowCardAssociations()[0].getBack().getGridCell(0,0).getColor());
        assertEquals(4,p.getWindowCardAssociations()[0].getBack().getGridCell(3,4).getShade());
        assertNull(p.getWindowCardAssociations()[1].getFront().getGridCell(0,0));
        assertEquals(Color.RED,p.getWindowCardAssociations()[1].getBack().getGridCell(0,3).getColor());
        assertEquals(6,p.getWindowCardAssociations()[1].getBack().getGridCell(2,4).getShade());
    }

}
