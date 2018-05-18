package it.polimi.se2018.Model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.fail;
/**
 * @author Gregorio Galletti
 * Unit Test Class for the Player class.
 */
public class TestPlayer {
    @Test
    public void testNewPlayer(){
        try {
            Player p = new Player("ingsw", "Socket", "CLI");
        } catch (InvalidConnectionException e) {
            fail();
        } catch (InvalidViewException e) {
            fail();
        }
    }

    @Test
    public void testNewWrongConnectionPlayer(){
        try {
            Player p = new Player("ingsw", "Soket", "CLI");
            fail();
        } catch (InvalidConnectionException e) {

        } catch (InvalidViewException e) { }
    }
    @Test
    public void testNewWrongViewPlayer(){
        try {
            Player p = new Player("ingsw", "Socket", "GLI");
            fail();
        } catch (InvalidConnectionException e) {

        } catch (InvalidViewException e) { }
    }

    @Test
    public void testSetTokens(){
        Player p = null;
        try {
            p = new Player("ingsw", "Socket", "CLI");
        } catch (InvalidConnectionException e) {
            fail();
        } catch (InvalidViewException e) {
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
            p = new Player("ingsw", "Socket", "CLI");
        } catch (InvalidConnectionException e) {
            fail();
        } catch (InvalidViewException e) {
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
            p = new Player("ingsw", "Socket", "CLI");
        } catch (InvalidConnectionException e) {
            fail();
        } catch (InvalidViewException e) {
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
            p = new Player("ingsw", "Socket", "CLI");
        } catch (InvalidConnectionException e) {
            fail();
        }catch (InvalidViewException e) {
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
            p = new Player("player", "RMI","CLI");
        } catch (InvalidConnectionException e) {
            fail();
        } catch (InvalidViewException e) {
            fail();
        }
        p.setPlayerScore(5);
        assertEquals(5,p.getPlayerScore());
        p.setPlayerScore(5);
        assertEquals(10,p.getPlayerScore());
    }

}
