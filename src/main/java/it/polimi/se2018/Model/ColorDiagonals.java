package it.polimi.se2018.Model;

public class ColorDiagonals extends PublicObjective {
    public ColorDiagonals(int number, String title, String description, String cardType, int score) {
        super(number, title, description, "PublicObjective", score);
    }

    @Override
    public void calculateBonus(Player p) {

        WindowCard temp = p.getWindowCard();

        int counter = 0;
        for(int i = 0; i < temp.ROWS - 1; i++){
            for(int j = 0; j < temp.COLS; j++){
                if(temp.getGridCell(i,j).isPlaced() && !temp.getGridCell(i,j).isVisited()) {
                    if (i + 1 < temp.ROWS && j + 1 < temp.COLS && temp.getGridCell(i, j).getPlacedDie().getColor() == temp.getGridCell(i + 1, j + 1).getPlacedDie().getColor() && !temp.getGridCell(i + 1, j + 1).isVisited()) {
                        counter++;
                        temp.getGridCell(i + 1, j + 1).setVisited(true);
                    }
                    if (i + 1 < temp.ROWS && j - 1 >= 0 && temp.getGridCell(i, j).getPlacedDie().getColor() == temp.getGridCell(i + 1, j - 1).getPlacedDie().getColor() && !temp.getGridCell(i + 1, j - 1).isVisited()) {
                        counter++;
                        temp.getGridCell(i + 1, j - 1).setVisited(true);
                    }
                    if (i - 1 >= 0 && j + 1 < temp.COLS && temp.getGridCell(i, j).getPlacedDie().getColor() == temp.getGridCell(i - 1, j + 1).getPlacedDie().getColor() && !temp.getGridCell(i - 1, j + 1).isVisited()) {
                        counter++;
                        temp.getGridCell(i - 1, j + 1).setVisited(true);
                    }
                    if (i - 1 >= 0 && j - 1 >= 0 && temp.getGridCell(i, j).getPlacedDie().getColor() == temp.getGridCell(i - 1, j - 1).getPlacedDie().getColor() && !temp.getGridCell(i - 1, j - 1).isVisited()) {
                        counter++;
                        temp.getGridCell(i - 1, j - 1).setVisited(true);
                    }
                temp.getGridCell(i,j).setVisited(true);
                }
            }
        }

        p.setPlayerScore(p.getPlayerScore() + counter);
    }
}
