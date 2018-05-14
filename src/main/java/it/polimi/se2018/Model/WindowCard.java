package it.polimi.se2018.Model;

import java.util.ArrayList;
import java.util.List;

import static it.polimi.se2018.Model.Color.BLUE;

public class WindowCard {
    final int ROWS = 4;
    final int COLS = 5;
    private Cell[][] grid;
    private String windowName;
    private int difficulty;

    public WindowCard() {
        grid = new Cell[ROWS][COLS];
        /*for (int i = 0; i <ROWS; i++)  {
            for (int j = 0; j < COLS; j++){
                grid[i][j] = new Cell(BLUE, 4);
            }
        }*/
    }

    // da testare
    public boolean checkLegalPlacement(Die d, int x, int y) {

        return false;
    }

    public void setCell(Cell c, int x, int y) {
        this.grid[x][y] = c;
    }

    // da testare
    public void placeDie(Die d, int x, int y){

        this.getGridCell(x,y).placeDie(d);

    }

    // da testare
    public void removeDie(int x, int y){

    }

    // da testare
    public Cell getGridCell(int x, int y){

       return grid[x][y];
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

        return false;
    }

    // da testare
    public boolean isEmpty(){

        return true;
    }

    public void setWindowName(String windowName) {
        this.windowName = windowName;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }
}
