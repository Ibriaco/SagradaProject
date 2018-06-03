package it.polimi.se2018.Model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
/**
 * @author Marco Gasperini
 * Unit Test Class for all the Public Cards of the game.
 */
public class TestWindowCardAssociation {

    @Test
    public void TestExtractAssociation() {
        WindowCard w1,w2;
        WindowCardAssociation ww = null;
        w1 = new WindowCard();
        w2 = new WindowCard();
        try {
            ww = new WindowCardAssociation(w1, w2);
        } catch (WindowCardAssociationException e){
            fail();
        }

        assertEquals(w1,ww.extractAssociation());
    }
    @Test
    public void TestNewWindowCardAssociation() {
        WindowCard w1 = new WindowCard();
        WindowCard w2 = new  WindowCard();
        WindowCardAssociation ww = null;
        try {
           ww = new WindowCardAssociation(w1,w2);
        } catch (WindowCardAssociationException e){
            fail();
        }

        assertEquals(w1, ww.getFront());
        assertEquals(w2, ww.getBack());
    }
    @Test
    public void TestFailWindowCardAssociation1(){
        WindowCard w1 =  null;
        WindowCard w2 = new  WindowCard();
        WindowCardAssociation ww = null;
        try {
            ww = new WindowCardAssociation(w1,w2);
            fail();
        } catch (WindowCardAssociationException e) {}

    }

    @Test
    public void TestFailWindowCardAssociation2(){
        WindowCard w1 =  new WindowCard();
        WindowCard w2 = null;
        WindowCardAssociation ww = null;
        try {
            ww = new WindowCardAssociation(w1,w2);
            fail();
        } catch (WindowCardAssociationException e) {}

    }

}