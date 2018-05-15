package it.polimi.se2018.Model;

public abstract class Card {

    private int number;
    private String title;
    private String description;

    public Card(int number, String title, String description) {
        this.number = number;
        this.title = title;
        this.description = description;
    }


    public String getTitle(){

        return title;
    }

    public String getDescription(){

        return description;
    }

        public int getNumber() {

        return number;
    }
}
