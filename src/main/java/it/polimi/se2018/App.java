package it.polimi.se2018;

import it.polimi.se2018.Model.Color;
import it.polimi.se2018.Model.Die;
import it.polimi.se2018.Model.Game;
import it.polimi.se2018.Model.Player;

import static it.polimi.se2018.Model.Color.BLUE;
import static it.polimi.se2018.Model.Color.RED;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) {
        System.out.println("Main");
        Game g = new Game(3, "Singleplayer");
        /*for (int i = 0; i < 3; i++) {

            System.out.println("P O " + i + ": ");
            System.out.println(g.getPublicCards().get(i).getNumber());
            System.out.println(g.getPublicCards().get(i).getTitle());
            System.out.println(g.getPublicCards().get(i).getDescription());
            System.out.println(g.getPublicCards().get(i).getScore());
        }
*/
        /*g.setRolledDice(new Die(BLUE, 0));
        for (int i = 0; i < g.getRolledDice().size(); i++) {

            System.out.println("Dado " + i + ": ");
            System.out.println(g.getRolledDice().get(i).getColor());
            System.out.println(g.getRolledDice().get(i).getValue());
        }*/

        Player p1 = new Player("griggoswaggo");
        g.addPlayer(p1);
        g.dealPrivateCards();
        System.out.println("OOOOO");
        System.out.println(p1.getPrivateObjective().getColor());
        System.out.println(p1.getPrivateObjective().getTitle());

    }
}
