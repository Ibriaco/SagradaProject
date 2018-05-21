package it.polimi.se2018.Model.Event;

public class ChosenCardEvent extends MVEvent {

    private int windowNumber;
    private int side;
    private String username;

    public int getWindowNumber() {
        return windowNumber;
    }

    public int getSide() {
        return side;
    }

    public String getUsername() {
        return username;
    }
}
