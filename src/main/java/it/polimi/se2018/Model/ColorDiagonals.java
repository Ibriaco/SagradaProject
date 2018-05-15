package it.polimi.se2018.Model;
/**Class of the Color Diagonals Public Objective of the game.
 * @author Gregorio Galletti
 */
public class ColorDiagonals extends PublicObjective {
    public ColorDiagonals(int number, String title, String description, int score) {
        super(number, title, description, score);
    }

    @Override
    public void calculateBonus(Player p) {

        WindowCard temp = p.getWindowCard();
        Color currentColor = null;
        Color diagonalColor = null;
        int counter = 1;
        boolean bonus = false;
        for(int i = 0; i < temp.ROWS - 1; i++){
            for(int j = 0; j < temp.COLS; j++){
                if(temp.getGridCell(i,j).isPlaced() && !temp.getGridCell(i,j).isVisited()) {
                    currentColor = temp.getGridCell(i,j).getPlacedDie().getColor();
                    if (i + 1 < temp.ROWS && j + 1 < temp.COLS && temp.getGridCell(i + 1, j + 1).isPlaced()){
                        diagonalColor = temp.getGridCell(i + 1, j + 1).getPlacedDie().getColor();
                        if(currentColor == diagonalColor && !temp.getGridCell(i + 1, j + 1).isVisited()) {
                        counter++;
                        temp.getGridCell(i + 1, j + 1).setVisited(true);
                        bonus = true;
                        }
                    }
                    if (i + 1 < temp.ROWS && j - 1 >= 0 && temp.getGridCell(i + 1, j - 1).isPlaced()){
                        diagonalColor = temp.getGridCell(i + 1, j - 1).getPlacedDie().getColor();
                        if(currentColor == diagonalColor && !temp.getGridCell(i + 1, j - 1).isVisited()) {
                        counter++;
                        temp.getGridCell(i + 1, j - 1).setVisited(true);
                        bonus = true;
                        }
                    }
                    if (i - 1 >= 0 && j + 1 < temp.COLS && temp.getGridCell(i - 1, j + 1).isPlaced()){
                        diagonalColor = temp.getGridCell(i - 1, j + 1).getPlacedDie().getColor();
                        if(currentColor == diagonalColor && !temp.getGridCell(i - 1, j + 1).isVisited()) {
                        counter++;
                        temp.getGridCell(i - 1, j + 1).setVisited(true);
                            bonus = true;
                        }
                    }
                    if (i - 1 >= 0 && j - 1 >= 0 && temp.getGridCell(i - 1, j - 1).isPlaced()) {
                        diagonalColor = temp.getGridCell(i - 1, j - 1).getPlacedDie().getColor();
                        if(currentColor == diagonalColor && !temp.getGridCell(i - 1, j - 1).isVisited()) {
                        counter++;
                        temp.getGridCell(i - 1, j - 1).setVisited(true);
                            bonus = true;
                        }
                    }
                temp.getGridCell(i,j).setVisited(true);
                }
                System.out.println(counter);
            }
        }

        if(bonus == false)
            counter = 0;

        p.setPlayerScore(counter);
    }


}
