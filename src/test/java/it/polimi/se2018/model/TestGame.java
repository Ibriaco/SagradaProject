package it.polimi.se2018.model;

import org.json.simple.parser.ParseException;
import org.junit.Test;

import java.io.IOException;
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

}
