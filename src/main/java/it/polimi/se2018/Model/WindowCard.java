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
    }

    // da testare
    public boolean checkLegalPlacement(Die d, int row, int col) {

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
