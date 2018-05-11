package it.polimi.se2018;

import it.polimi.se2018.Model.Color;
import it.polimi.se2018.Model.Die;
import it.polimi.se2018.Model.Game;

import static it.polimi.se2018.Model.Color.RED;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) {
        System.out.println("Main");
        Game g = new Game(2, "Singleplayer");
        for (int i = 0; i < 3; i++) {

            System.out.println("P O " + i + ": ");
            System.out.println(g.getPublicCards().get(i).getNumber());
            System.out.println(g.getPublicCards().get(i).getTitle());
            System.out.println(g.getPublicCards().get(i).getDescription());
            System.out.println(g.getPublicCards().get(i).getScore());
        }

    }
}
