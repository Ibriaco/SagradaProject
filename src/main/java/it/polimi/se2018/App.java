package it.polimi.se2018;


import static it.polimi.se2018.view.ui.CLIUtils.ANSI_BLUE;
import static it.polimi.se2018.view.ui.CLIUtils.ANSI_RESET;

/**
 * Hello world!
 *
 */
public class App 
{

    /*public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_WHITE = "\u001B[37m";*/

    public static void main( String[] args ) {
       /* server s = new server();
        SocketClient sc = new SocketClient("localhost",10000);
        SocketClient sc2 = new SocketClient("localhost",10000);
        SocketClient sc3 = new SocketClient("localhost",10000);

        RMIClient rc = new RMIClient("localhost",10001);*/


       /* System.out.println("Main");
        String toPrint = "\u25FC";
        System.out.print(ANSI_WHITE + toPrint + ANSI_RESET);
        Game g = null;

        g = new Game(3);


        try {
            g.addPlayer(new Player("test1","CLI"));
            g.addPlayer(new Player("test2","CLI"));
        } catch ( InvalidViewException e) {
            e.printStackTrace();
        }
g.dealPrivateCards();
        g.dealWindowCards();


        Color c;
        int v;
        for (Player p: g.getPlayers()) {

            System.out.println(p.getUsername() + "WindowCard");
            System.out.println(p.getWindowCardAssociations()[0].getFront().getWindowName());
            for (int i = 0; i < 4; i++){

                for (int j = 0; j < 5; j++){
                    c = p.getWindowCardAssociations()[0].getFront().getGridCell(i,j).getColor();
                    v = p.getWindowCardAssociations()[0].getFront().getGridCell(i,j).getShade();
                    printCell(c,v);
                }
                System.out.println("");
            }
            System.out.println();
            System.out.println(p.getWindowCardAssociations()[0].getBack().getWindowName());
            System.out.println(p.getWindowCardAssociations()[1].getFront().getWindowName());
            System.out.println(p.getWindowCardAssociations()[1].getBack().getWindowName());
        }

*/
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

    /*private static void printCell(Color c, int v) {
        String toPrint;
        String ok;
        if (v == 0) {
            toPrint = "\u25FC";
            if(c == null) {
                System.out.print(toPrint);
            }
            else{
            switch (c) {
                case BLUE:
                    System.out.print(ANSI_BLUE + toPrint + ANSI_RESET);
                    break;
                case RED:
                    System.out.print(ANSI_RED + toPrint + ANSI_RESET);
                    break;
                case GREEN:
                    System.out.print(ANSI_GREEN + toPrint + ANSI_RESET);
                    break;
                case YELLOW:
                    System.out.print(ANSI_YELLOW + toPrint + ANSI_RESET);
                    break;
                case PURPLE:
                    System.out.print(ANSI_PURPLE + toPrint + ANSI_RESET);
                    break;
                default:
                    break;
            }
            }
        } else {

            switch(v){
                case 1:
                    System.out.print("\u2680");
                    break;
                case 2:
                    System.out.print("\u2681");
                    break;
                case 3:
                    System.out.print("\u2682");
                    break;
                case 4:
                    System.out.print("\u2683");
                    break;
                case 5:
                    System.out.print("\u2684");
                    break;
                case 6:
                    System.out.print("\u2685");
                    break;
            }
            }
        }*/

}
