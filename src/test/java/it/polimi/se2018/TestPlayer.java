package it.polimi.se2018;

import it.polimi.se2018.Model.InvalidConnectionException;
import it.polimi.se2018.Model.Player;
import it.polimi.se2018.Model.WindowCard;
import it.polimi.se2018.Model.WindowCardAssociation;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.fail;

public class TestPlayer {
    @Test
    public void testNewPlayer(){
        try {
            Player p = new Player("ingsw", "Socket", "CLI");
        } catch (InvalidConnectionException e) {
            fail();
        }
    }

    @Test
    public void testNewWrongPlayer(){
        try {
            Player p = new Player("ingsw", "Soket", "CLI");
            fail();
        } catch (InvalidConnectionException e) {

        }
    }

    @Test
    public void testSetTokens(){
        Player p = null;
        try {
            p = new Player("ingsw", "Socket", "CLI");
        } catch (InvalidConnectionException e) {

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
        WindowCardAssociation a = new WindowCardAssociation(w1, w2);
        Player p = null;
        try {
            p = new Player("ingsw", "Socket", "CLI");
        } catch (InvalidConnectionException e) {
            e.printStackTrace();
        }

        p.chooseWindowCard(a,0);
        assertEquals(w1,p.getWindowCard());
    }

    /*@Test
    public void testWrongChoice(){
        WindowCard w1 = new WindowCard();
        WindowCard w2 = new WindowCard();
        WindowCardAssociation a = new WindowCardAssociation(w1, w2);
        Player p = null;
        try {
            p = new Player("ingsw", "Socket", "CLI");
        } catch (InvalidConnectionException e) {
            e.printStackTrace();
        }

        p.chooseWindowCard(a,4);

    }*/
}
