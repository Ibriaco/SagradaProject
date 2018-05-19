package it.polimi.se2018.View;

public class ChooseCardEvent extends VCEvent {
    private int windowNumber;
    private int side;

    public int getWindowNumber() {
        return windowNumber;
    }

    public int getSide() {
        return side;
    }
}
