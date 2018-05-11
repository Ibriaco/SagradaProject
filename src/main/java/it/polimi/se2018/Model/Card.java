package it.polimi.se2018.Model;

public abstract class Card {


    private int number;
    private String title;
    private String description;
    private String cardType;

    public Card(int number, String title, String description, String cardType) {
        this.number = number;
        this.title = title;
        this.description = description;
        this.cardType = cardType;
    }


    public String getTitle(){
        return title;
    }

    public String getDescription(){

        return description;
    }

    public String getCardType(){

            return cardType;
    }

    public int getNumber() {

        return number;
    }
}
