package it.polimi.se2018.Model;

public class ColumnRowShades extends PublicObjective{
    public ColumnRowShades(int number, String title, String description, int score) {
        super(number, title, description, score);
    }

    public int[] calculateFrequency(WindowCard w, int[] freq, int j, int i, boolean type){

        int currentV;
        if(type)
            currentV = w.getGridCell(j, i).getPlacedDie().getValue();
        else
            currentV = w.getGridCell(i, j).getPlacedDie().getValue();

        switch (currentV){
            case 1: freq[0]++; break;
            case 2: freq[1]++; break;
            case 3: freq[2]++; break;
            case 4: freq[3]++; break;
            case 5: freq[4]++; break;
            case 6: freq[5]++; break;
            default:
        }

        return freq;
    }
}

