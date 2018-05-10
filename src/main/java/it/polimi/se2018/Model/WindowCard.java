package it.polimi.se2018.Model;

import static it.polimi.se2018.Model.Color.BLUE;

public class WindowCard {
    final int ROWS = 5;
    final int COLS = 4;
    private Cell[][] grid;
    private String windowName;
    private int difficulty;


    public WindowCard() {
    }

    // da testare millemila volte
    public boolean checkLegalPlacement(Die d, int x, int y){
        return false;
    }

    // da testare
    public void placeDie(Die d, int x, int y){

    }

    // da testare
    public void removeDie(int x, int y){

    }

    // da testare
    public Cell getGridCell(){

       Cell c = new Cell(BLUE, 4);
       return c;
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
