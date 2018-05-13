package it.polimi.se2018.Model;

import java.util.ArrayList;

import static it.polimi.se2018.Model.Color.BLUE;

public class WindowCard {
    final int ROWS = 4;
    final int COLS = 5;
    private Cell[][] grid;
    private String windowName;
    private int difficulty;
    private ArrayList<Die> placedDice;


    public WindowCard() {
        for (int i = 0; i <ROWS; i++)  {
            for (int j = 0; i < COLS; j++){
                grid[i][j] = new Cell(null, 4);
            }
        }
    }

    // da testare millemila volte
    public boolean checkLegalPlacement(Die d, int x, int y) {

        return false;
    }

    // da testare
    public void placeDie(Die d, int x, int y){

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

}
