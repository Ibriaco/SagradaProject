package it.polimi.se2018.Model;
/**Cell class of the game. This class simply contains getters and setters of the attributes of the object, and also methods to check if a placement is possible.
 * @author Gregorio Galletti
 * */
public class Cell {

    private Color color;
    private int shade;
    private boolean placed = false;
    private Die placedDie;
    private boolean visited;

    /**
     * Creates a new Cell that will be placed in the Window card's grid. A cell can only have either a color or a shade.
     * @param color the color of the cell.
     * @param shade the value of the cell.
     */
    public Cell(Color color, int shade) {
        this.color = color;
        this.shade = shade;
    }

    public Color getColor() {

        return color;
    }

    public int getShade() {

        return shade;
    }

    public void setColor(Color color) {

        this.color = color;
    }

    public void setShade(int shade) {

        this.shade = shade;
    }

    // da testare

    /**
     * This boolean method checks if a die is placed or not in the cell.
     * @return true if a die is placed, false if no die is placed.
     */
    public boolean isPlaced() {

        placed = (placedDie != null);
        return placed;
    }

    public void setPlaced(boolean placed) {
        this.placed = placed;
    }

    // da testare
    public Die getPlacedDie() {

        return placedDie;
    }

    public boolean isVisited() {

        return visited;
    }

    public void setVisited(boolean visited) {

        this.visited = visited;
    }

    public void placeDie(Die d){
        if(checkPlacement(this, d)) {
            placedDie = d;
            placed = true;
        }
    }

    private boolean checkPlacement(Cell c, Die d){
        return (c.getColor() == d.getColor() || c.getShade() == d.getValue());
    }

    //used only for test purpose
    public void setPlacedDie(Die d){
        placed = true;
        placedDie = d;
    }
}
