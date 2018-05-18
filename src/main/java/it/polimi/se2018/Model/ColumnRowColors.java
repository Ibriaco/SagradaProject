package it.polimi.se2018.Model;

public class ColumnRowColors extends PublicObjective {
    public ColumnRowColors(int number, String title, String description, int score) {
        super(number, title, description, score);
    }

    public int[] calculateFrequency(WindowCard w, int[] freq, int j, int i, boolean type){

        Color currentC;
        if(type)
            currentC = w.getGridCell(j, i).getPlacedDie().getColor();
        else
            currentC = w.getGridCell(i, j).getPlacedDie().getColor();

        switch (currentC){
            case BLUE: freq[0]++; break;
            case RED: freq[1]++; break;
            case YELLOW: freq[2]++; break;
            case PURPLE: freq[3]++; break;
            case GREEN: freq[4]++; break;
            default:
        }

        return freq;
    }
}
