package it.polimi.se2018.Model;

public abstract class Card {
    private String title;
    private String description;
    private String cardType;

    public Card() {
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
}
