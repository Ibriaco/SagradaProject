package it.polimi.se2018.Model;

/**
 * @author Ibrahim El Shemy
 */
public class WindowCard {
    final int ROWS = 4;
    final int COLS = 5;
    private Cell[][] grid;
    private String windowName;
    private int difficulty;

    public WindowCard() {
        grid = new Cell[ROWS][COLS];
    }

    // da testare
    // Questo metodo va rivisto con piu' attenzione
    // PROBLEMA: se voglio inserire un dado sul bordo di sinistra per esempio, e devo fare il controllo su valori e colori adiacenti,
    //           se vado a controllare la cella a sinitra, ovviamente non c'è, e il compilatore mi lancia un eccezione sicuramente
    public boolean checkLegalPlacement(Die d, int row, int col){

        if (this.isEmpty()) {
            return ((row >= 0 && row <= 3) && (col == 0 || col == 4) || (col >= 0 && col <= 4) && (row == 0 || row == 3));
        }

        if (!this.getGridCell(row, col).isPlaced()) {
            if(this.getGridCell(row-1,col).isPlaced()||
                this.getGridCell(row+1,col).isPlaced()||
                this.getGridCell(row,col-1).isPlaced()||
                this.getGridCell(row,col+1).isPlaced()) {

                    if (d.getValue() == this.getGridCell(row - 1, col).getPlacedDie().getValue() ||
                            d.getValue() == this.getGridCell(row + 1, col).getPlacedDie().getValue() ||
                            d.getValue() == this.getGridCell(row, col - 1).getPlacedDie().getValue() ||
                            d.getValue() == this.getGridCell(row, col + 1).getPlacedDie().getValue() ||
                            d.getColor() == this.getGridCell(row - 1, col).getPlacedDie().getColor() ||
                            d.getColor() == this.getGridCell(row + 1, col).getPlacedDie().getColor() ||
                            d.getColor() == this.getGridCell(row, col - 1).getPlacedDie().getColor() ||
                            d.getColor() == this.getGridCell(row, col + 1).getPlacedDie().getColor())
                        return false;
                    else
                        return true;
            }
            else
                return false;
        }
        else
            return false;

    }

    public void setCell(Cell c, int row, int col) {
        this.grid[row][col] = c;
    }


    // da testare
    public void placeDie(Die d, int row, int col){

        this.getGridCell(row,col).placeDie(d);

    }

    // da testare
    public void removeDie(int row, int col){
        if(grid[row][col].isPlaced()) {
            grid[row][col].placeDie(null);
            grid[row][col].setPlaced(false);
        }

    }

    // da testare
    public Cell getGridCell(int row, int col){

       return grid[row][col];
    }

    // da testare
    public String getWindowName(){

        return windowName;
    }

    // da testare
    public int getDifficulty(){

        return difficulty;
    }

    // da testare
    public boolean isFull(){
        if (grid != null)
            return true;
        else
            return false;
    }

    // da testare
    public boolean isEmpty(){
        if (grid == null)
            return true;
        else
            return false;
    }

    public void setWindowName(String windowName) {

        this.windowName = windowName;
    }

    public void setDifficulty(int difficulty) {

        this.difficulty = difficulty;
    }
}
