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

        int previousR = row - 1;
        int previousC = col - 1;
        int nextR = row + 1;
        int nextC = col + 1;

        //se è vuota la griglia allora controllo se è sul bordo
        if (isEmpty())
            return ((row >= 0 && row <= 3) && (col == 0 || col == 4) || (col >= 0 && col <= 4) && (row == 0 || row == 3));

        //se è piena la griglia allora non posso piazzare
        if(isFull())
            return false;

        //se le coordinate sono sbagliate allora non posso piazzare
        if(!checkCoords(row, col))
            return false;

        //se c'è un dado nella stessa posizione allora non posso piazzare
        if (getGridCell(row, col).isPlaced())
            return false;

        //controllo se ho dadi adiacenti (ortogonalmente) dello stesso colore o valore: se sì, non posso piazzare; se il dado è adiacente (ortogonalmente o diagonalmente) a un altro dado gia piazzato
        return checkOrtogonal(d, row, col, previousR, previousC, nextC, nextR) && checkAround(d, row, col, previousR, previousC, nextC, nextR);
    }

    private boolean checkOrtogonal(Die d, int row, int col, int previousR, int previousC, int nextC, int nextR) {

        System.out.println("Ortogonal");
        boolean ok1 = true;
        boolean ok2 = true;
        boolean ok3 = true;
        boolean ok4 = true;

        if (previousR >= 0)
            ok1 = checkUp(d, previousR, col);
        if (nextR < ROWS)
            ok2 = checkDown(d, nextR, col);
        if (previousC >= 0)
            ok3 = checkLeft(d, row, previousC);
        if (nextC < COLS)
            ok4 = checkRight(d, row, nextC);

        System.out.println("ok1: " + ok1);
        System.out.println("ok2: " + ok2);
        System.out.println("ok3: " + ok3);
        System.out.println("ok4: " + ok4);

        return(ok1 && ok2 && ok3 && ok4);
        //ritorna true se posso piazzare, cioe se nessun dado ortogonalmente ha stesso colore/valore
    }

    private boolean checkAround(Die d, int row, int col, int previousR, int previousC, int nextC, int nextR) {

        System.out.println("Around");
        //sinistra
        if(previousC >= 0) {
            if (getGridCell(row, previousC).isPlaced())
                return true;
        }
        //destra
        if(nextC < COLS) {
            if (getGridCell(row, nextC).isPlaced())
                return true;
        }

        if (previousR >= 0) {
            //sinistra-su
            if (previousC >= 0) {
                if (getGridCell(previousR, previousC).isPlaced())
                    return true;
            }
            //destra-su
            if (nextC < COLS){
                if(getGridCell(previousR, nextC).isPlaced())
                    return true;
            }

            //su
            if(getGridCell(previousR, col).isPlaced())
                return true;
        }

        if (nextR < ROWS) {
            if (previousC >= 0){
                if(getGridCell(nextR, previousC).isPlaced())
                return true;
            }
            if (nextC < COLS) {
                if (getGridCell(nextR, nextC).isPlaced())
                    return true;
            }

            if(getGridCell(nextR, col).isPlaced())
                return true;
        }
        return false;
        //ritorna true se posso piazzare, cioe se almeno un dado ortogonalmente/diagonalmente è piazzato
    }

    private boolean checkCoords(int r, int c){

        return (r <= ROWS && r >= 0) && (c <= COLS && c >= 0);

    }

    private boolean checkUp(Die d, int r, int c){

        Die upDie;
        if(getGridCell(r,c).isPlaced())
            upDie = getGridCell(r,c).getPlacedDie();
        else
            return true;

        return !(upDie.getColor() == d.getColor() || upDie.getValue() == d.getValue());

    }

    private boolean checkDown(Die d, int r, int c){

        Die downDie;
        if(getGridCell(r,c).isPlaced())
            downDie = getGridCell(r,c).getPlacedDie();
        else
            return true;

        return !(downDie.getColor() == d.getColor() || downDie.getValue() == d.getValue());

    }

    private boolean checkLeft(Die d, int r, int c){

        Die leftDie;
        if(getGridCell(r,c).isPlaced())
            leftDie = getGridCell(r,c).getPlacedDie();
        else
            return true;

        System.out.println("Colore left: " + leftDie.getColor());
        System.out.println("Colore dado: " + d.getColor());
        System.out.println("Valore left: " + leftDie.getValue());
        System.out.println("Valore dado: " + d.getValue());

        return !(leftDie.getColor() == d.getColor() || leftDie.getValue() == d.getValue());

    }

    private boolean checkRight(Die d, int r, int c){

        Die rightDie;
        if(getGridCell(r,c).isPlaced())
            rightDie = getGridCell(r,c).getPlacedDie();
        else
            return true;

        return !(rightDie.getColor() == d.getColor() || rightDie.getValue() == d.getValue());

    }

    public void setCell(Cell c, int row, int col) {

        grid[row][col] = c;
    }


    // da testare
    public void placeDie(Die d, int row, int col){

        if(checkLegalPlacement(d,row,col))
            getGridCell(row,col).placeDie(d);

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
        for(int i = 0; i < ROWS; i++){
            for(int j = 0; j < COLS; j++){
                if(!grid[i][j].isPlaced())
                    return false;
            }
        }

        return true;
    }

    // da testare
    public boolean isEmpty(){
        for(int i = 0; i < ROWS; i++){
            for(int j = 0; j < COLS; j++){
                if(grid[i][j].isPlaced())
                    return false;
            }
        }

        return true;
    }

    public void setWindowName(String windowName) {

        this.windowName = windowName;
    }

    public void setDifficulty(int difficulty) {

        this.difficulty = difficulty;
    }
}
