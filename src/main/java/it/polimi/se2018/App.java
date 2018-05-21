package it.polimi.se2018;


import it.polimi.se2018.Server.Model.*;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) {
        System.out.println("Main");
        Game g = null;
        try {
            g = new Game(3);
        } catch (InvalidGameCreationException e) {
            e.printStackTrace();
        }

        try {
            g.addPlayer(new Player("test1","RMI","CLI"));
            g.addPlayer(new Player("test2","RMI","CLI"));
        } catch (InvalidConnectionException | InvalidViewException e) {
            e.printStackTrace();
        }

        try {
            g.dealWindowCards();
        } catch (WindowCardAssociationException e) {
            e.printStackTrace();
        }

        for (Player p: g.getPlayers()) {
            System.out.println(p.getWindowCardAssociations()[0].getFront().getWindowName());
            System.out.println(p.getWindowCardAssociations()[0].getBack().getWindowName());
            System.out.println(p.getWindowCardAssociations()[1].getFront().getWindowName());
            System.out.println(p.getWindowCardAssociations()[1].getBack().getWindowName());
        }


    /*
        for (int i = 0; i < 3; i++) {

            System.out.println("P O " + i + ": ");
            System.out.println(g.getPublicCards().get(i).getNumber());
            System.out.println(g.getPublicCards().get(i).getTitle());
            System.out.println(g.getPublicCards().get(i).getDescription());
            System.out.println(g.getPublicCards().get(i).getScore());
        }

        g.setRolledDice(new Die(BLUE, 0));
        for (int i = 0; i < g.getRolledDice().size(); i++) {

            System.out.println("Dado " + i + ": ");
            System.out.println(g.getRolledDice().get(i).getColor());
            System.out.println(g.getRolledDice().get(i).getValue());
        }

        Player p1 = new Player("ingsw");
        g.addPlayer(p1);
        g.dealPrivateCards();
        System.out.println("Go:");
        System.out.println(p1.getPrivateObjective().getColor());
        System.out.println(p1.getPrivateObjective().getTitle());


        g.dealWindowCards();
        System.out.println("Go:");
        p1.printAss();
        p1.chooseWindowCard(p1.getWindowCardAssociations()[0], 0);
        System.out.println(p1.getWindowCard().getWindowName());
        g.getPublicCards().get(0).calculateBonus(p1);
        System.out.println(p1.getPlayerScore());
*/
    }
}
