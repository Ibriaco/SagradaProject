package it.polimi.se2018.model;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.Before;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * @author Gregorio Galletti
 * @author Marco Gasperini
 * Unit Test Class for the Player class.
 */
public class TestPlayer {
    @Before
    public void init() throws IOException, ParseException {

        JSONParser parser = new JSONParser();
        JSONArray cards = (JSONArray) parser.parse(new FileReader("./src/main/resources/GameResources/windows.json"));
    }

    @Test
    public void testNewPlayer(){
        try {
            Player p = new Player("ingsw", "CLI");
        } catch (InvalidViewException e) {
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
        } catch (InvalidViewException e) { }
    }

    @Test
    public void testSetTokens(){
        Player p = null;
        try {
            p = new Player("ingsw", "CLI");
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
            p = new Player("ingsw", "CLI");
        } catch (InvalidViewException e) {
            fail();
        }
        p.setTokens(4);
        p.reduceTokens(5);

        assertEquals(4,p.getTokens());
        assertNotEquals(-1,p.getTokens());
    }

    /*@Test
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
            p = new Player("ingsw", "CLI");
        } catch (InvalidViewException e) {
            fail();
        }

        p.chooseWindowCard(a,4);
        assertNotEquals(w1,p.getWindowCard());
        assertNotEquals(w2,p.getWindowCard());


    }*/

    @Test
    public void testSetPlayerScore(){
        Player p= null;
        try {
            p = new Player("player", "CLI");
        } catch (InvalidViewException e) {
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
        } catch (InvalidViewException e) {
            fail();
        }
        p.setTokens(10);
        p.reduceTokens(3);
        assertEquals(7,p.getTokens());
        p.reduceTokens(8);
        assertEquals(7,p.getTokens());
    }

    @Test
    public void testDrawCard() throws IOException, ParseException {
        Player p= null;
        try {
            p = new Player("player", "CLI");
        } catch (InvalidViewException e) {
            fail();
        }
        p.drawCard(0);
        assertEquals(0,p.getPrivateObjective().getScore());
        assertEquals(Color.RED,p.getPrivateObjective().getColor());
        p.drawCard(1);
        assertEquals(0,p.getPrivateObjective().getScore());
        assertEquals(Color.YELLOW,p.getPrivateObjective().getColor());
        p.drawCard(2);
        assertEquals(0,p.getPrivateObjective().getScore());
        assertEquals(Color.GREEN,p.getPrivateObjective().getColor());
        p.drawCard(3);
        assertEquals(0,p.getPrivateObjective().getScore());
        assertEquals(Color.BLUE,p.getPrivateObjective().getColor());
        p.drawCard(4);
        assertEquals(0,p.getPrivateObjective().getScore());
        assertEquals(Color.PURPLE,p.getPrivateObjective().getColor());
    }

    @Test
    public void testDrawWindowCard() throws InvalidViewException, IOException, ParseException {
        Player p = new Player("Player", "GUI");

        JSONParser parser = new JSONParser();
        JSONArray cards = (JSONArray) parser.parse(new FileReader("./src/main/resources/GameResources/windows.json"));
        p.drawWindowCards(cards, 0, 2);
        assertEquals("Kaleidoscopic Dream",p.getWindowCardList().get(0).getWindowName());
        assertEquals("Firmitas",p.getWindowCardList().get(1).getWindowName());
        assertEquals("Fractal Drops",p.getWindowCardList().get(2).getWindowName());
        assertEquals("Ripples of Light",p.getWindowCardList().get(3).getWindowName());

    }
}
