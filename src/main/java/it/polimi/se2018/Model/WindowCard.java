package it.polimi.se2018.Model;

public class WindowCard {
    final int ROWS = 5;
    final int COLS = 4;
    private Cell[][] grid;
    private String windowName;
    private int difficulty;


    public WindowCard() {
    }

    public boolean checkLegalPlacement(Die d, int x, int y){
        return false;
    }

    public void placeDie(Die d, int x, int y){

    }

    public void removeDie(int x, int y){

    }

    public Cell getGridCell(){

        Cell c = new Cell();
        return c;

    }

    public String getWindowName(){
        return windowName;
    }

    public int getDifficulty(){
        return difficulty;
    }

    public boolean isFull(){
        return false;
    }

    public boolean isEmpty(){
        return true;
    }

}
