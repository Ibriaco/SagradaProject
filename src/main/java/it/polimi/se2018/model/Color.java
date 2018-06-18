package it.polimi.se2018.model;
/**Color class of the Game. This class is an Enumeration class used by cells and dice. Contains all the colors available in the game.
 * @author Ibrahim El Shemy
 */
public enum Color {
    BLUE,
    GREEN,
    PURPLE,
    RED,
    YELLOW,
    WHITE,
    NOT_A_COLOR;

    public static boolean contains(String s) {
        for (Color c: Color.values()) {
            if(c.name().equals(s))
                return true;
        }
        return false;
    }

    public static Color returnMatch(String s) {
        for (Color c: Color.values()) {
            if(c.name().equals(s))
                return c;
        }
        return NOT_A_COLOR;
    }
}
