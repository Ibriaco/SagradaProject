package it.polimi.se2018.model;

import it.polimi.se2018.MyObserver;
import it.polimi.se2018.ServerParser;
import it.polimi.se2018.org.json.simple.parser.ParseException;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.fail;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * @author Gregorio Galletti
 * Unit Test Class for Class Game
 */
public class TestGame {
    ArrayList<MyObserver> observerCollection;
    MyObserver observer;
    private ServerParser sp = new ServerParser();

    @Before
    public void init(){
        sp.reader();
        observerCollection = new ArrayList<>();
    }

    @Test
    public void testGameCreation(){
        Game game = new Game(3);
    }


    @Test
    public void testPublic() throws InvalidConnectionException, InvalidViewException, ParseException, IOException {
        Game game = null;
        game = new Game(3);
        game.dealPublicCards();
        List<PublicObjective> list = game.getPublicCards();


        assertEquals(3, list.size());
        for(int i = 0; i < 3; i++)
            assert (list.get(i) != null);

        assertNotEquals(list.get(0),list.get(1));
        assertNotEquals(list.get(1),list.get(2));
        assertNotEquals(list.get(0),list.get(2));
    }

    @Test
    public void testRollDice(){
        Game game = null;
        game = new Game(3);

        game.setRolledDice();

        List<Die> list = game.getRolledDice();

        assertEquals(7, list.size());
        for(int i = 0; i < 7; i++)
            assert (list.get(i) != null);

    }

    @Test
    public void testRollAllDice(){
        Game game = null;
        game = new Game(4);
        for(int i = 0; i < 10; i++)
            game.setRolledDice();


        List<Die> list = game.getRolledDice();

        assertEquals(90, list.size());
        assertEquals(0, game.getColorList().size());
    }

    @Test
    public void testAddPlayer(){
        Game game = null;
        game = new Game(3);

        try {
            game.addPlayer(new Player("test", "CLI"));
        } catch (InvalidViewException e) {
            fail();
        }

        assertEquals(1,game.getPlayers().size());
        assert (game.getPlayers().get(0) != null);
    }

    @Test
    public void testAddPlayerFail(){
        Game game = null;
        game = new Game(2);

        try {
            game.addPlayer(new Player("test1", "CLI"));
            game.addPlayer(new Player("test2", "CLI"));
            game.addPlayer(new Player("test3","CLI"));
        } catch (InvalidViewException e) {
            fail();
        }

        assertEquals(2,game.getPlayers().size());
    }

    @Test
    public void testPrivateCards() throws IOException, InvalidConnectionException, ParseException, InvalidViewException {

        Game game = new Game(3);

        try {
            game.addPlayer(new Player("test1", "CLI"));
            game.addPlayer(new Player("test2", "CLI"));
            game.addPlayer(new Player("test3","CLI"));
        } catch (InvalidViewException e) {
        }
        game.dealPrivateCards();
        assertNotEquals(null, game.getPlayers().get(0).getPrivateObjective());
        assertNotEquals(null, game.getPlayers().get(1).getPrivateObjective());
        assertNotEquals(null, game.getPlayers().get(2).getPrivateObjective());
    }

    @Test
    public void testToolCards() throws InvalidConnectionException, InvalidViewException, ParseException, IOException {
        Game game = new Game(2);

        try {
            game.addPlayer(new Player("test1", "CLI"));
            game.addPlayer(new Player("test2", "CLI"));
        } catch (InvalidViewException e) {
        }
        game.dealToolCards();
        assertNotEquals(null, game.getToolCards().get(0));
        assertNotEquals(null, game.getToolCards().get(1));
    }

    @Test
    public void testWindowCards() throws ParseException {
        Game game = new Game(2);

        try {
            game.addPlayer(new Player("test1", "CLI"));
            game.addPlayer(new Player("test2", "CLI"));
        } catch (InvalidViewException e) {
        }
        game.dealWindowCards();
        assertNotEquals(null, game.getPlayers().get(0).getWindowCard());
        assertNotEquals(null, game.getPlayers().get(1).getWindowCard());
    }

    @Test
    public void registerObserver(){
        observerCollection.add(observer);
        assertEquals(1, observerCollection.size());
    }

    @Test
    public void unregisterObserver(){
        observerCollection.remove(observer);
        assertEquals(0, observerCollection.size());
    }
}
