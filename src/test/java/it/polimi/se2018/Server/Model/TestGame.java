package it.polimi.se2018.Server.Model;

import org.junit.Test;

import java.util.List;

import static junit.framework.TestCase.fail;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * @author Gregorio Galletti
 * Unit Test Class for Class Game
 */
public class TestGame {

    @Test
    public void testGameCreation(){
        try {
            Game game = new Game(3);
        } catch (InvalidGameCreationException e) {
            fail();
        }
    }

    @Test
    public void testGameWrongCreation(){
        try {
            Game game = new Game(6);
            fail();
        } catch (InvalidGameCreationException e) {
        }
    }

    @Test
    public void testPublic(){
        Game game = null;
        try {
             game = new Game(4);
        } catch (InvalidGameCreationException e) {
            fail();
        }

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
        try {
            game = new Game(3);
        } catch (InvalidGameCreationException e) {
            fail();
        }

        game.setRolledDice();

        List<Die> list = game.getRolledDice();

        assertEquals(7, list.size());
        for(int i = 0; i < 7; i++)
            assert (list.get(i) != null);

    }

    @Test
    public void testRollAllDice(){
        Game game = null;
        try {
            game = new Game(4);
        } catch (InvalidGameCreationException e) {
            fail();
        }
        for(int i = 0; i < 10; i++)
            game.setRolledDice();


        List<Die> list = game.getRolledDice();

        assertEquals(90, list.size());
        assertEquals(0, game.getColorList().size());
    }

    @Test
    public void testAddPlayer(){
        Game game = null;
        try {
            game = new Game(3);
        } catch (InvalidGameCreationException e) {
            fail();
        }

        try {
            game.addPlayer(new Player("test", "RMI","CLI"));
        } catch (InvalidConnectionException | InvalidViewException e) {
            fail();
        }

        assertEquals(1,game.getPlayers().size());
        assert (game.getPlayers().get(0) != null);
    }

    @Test
    public void testAddPlayerFail(){
        Game game = null;
        try {
            game = new Game(2);
        } catch (InvalidGameCreationException e) {
            fail();
        }

        try {
            game.addPlayer(new Player("test1", "RMI","CLI"));
            game.addPlayer(new Player("test2", "RMI","CLI"));
            game.addPlayer(new Player("test3", "RMI","CLI"));
        } catch (InvalidConnectionException | InvalidViewException e) {
            fail();
        }

        assertEquals(2,game.getPlayers().size());
    }

}
