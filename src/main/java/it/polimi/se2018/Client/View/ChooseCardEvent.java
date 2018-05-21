package it.polimi.se2018.Client.View;

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
