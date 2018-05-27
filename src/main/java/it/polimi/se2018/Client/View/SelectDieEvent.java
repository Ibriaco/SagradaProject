package it.polimi.se2018.Client.View;

import it.polimi.se2018.Server.Model.*;

/**
 * Event that notifies that a die is being selected.
 * @author Ibrahim El Shemy
 * @author Marco Gasperini
 */
public class SelectDieEvent extends VCEvent{

    private Color color;
    private int value;
    private int coordX;
    private int coordY;
    private int position;

    /**
     *
     * @param w refers to the Window Card I'm selecting the die from.
     * @param coordX refers to the x position on the Window Card.
     * @param coordY refers to the y position on the Window Card.
     * @param player refers to the current player.
     */

    // caso in cui seleziono un dado presente sulla Window Card
    public SelectDieEvent(WindowCard w, int coordX, int coordY, Player player){

        super(player);
        this.coordX = coordX;
        this.coordY = coordY;
        value = w.getGridCell(coordX, coordY).getPlacedDie().getValue();
        color = w.getGridCell(coordX, coordY).getPlacedDie().getColor();
    }

    /**
     *
     * @param c refers to the RoundCell I'm selecting the die from.
     * @param player refers to the current player.
     * @param position refers to the position of the RoundTrack where there is the RoundCell I'm selecting the die from.
     */

    // caso in cui seleziono un dado sul RoundTrack
    public SelectDieEvent(RoundCell c, Player player, int position){

        super(player);
        color = c.getDiceList().get(position).getColor();
        value = c.getDiceList().get(position).getValue();
        this.position = position;
    }

    /**
     *
     * @param game refers to the actual game.
     * @param player refers to the current player.
     * @param position refers to the position of the die I'm selecting from the DraftPool
     */

    // caso in cui seleziono un dado dalla DraftPool
    public SelectDieEvent(Game game, Player player, int position){

        super(player);
        color = game.getRolledDice().get(position).getColor();
        value = game.getRolledDice().get(position).getValue();
        this.position = position;
    }

    public Color getColor() {

        return color;
    }

    public int getValue() {

        return value;
    }

    public int getCoordX() {

        return coordX;
    }

    public int getCoordY() {

        return coordY;
    }

    public int getPosition(){

        return position;
    }
}
