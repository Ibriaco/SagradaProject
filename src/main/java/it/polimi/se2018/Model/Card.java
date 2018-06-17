package it.polimi.se2018.Model;

/**
 * Abstract class for the Card object of the game.
 * @author Ibrahim El Shemy
 */

public abstract class Card {

    private int number;
    private String title;
    private String description;

    /**
     * Constructor the Class.
     * @param number an integer value that represents an index, used to upload cards from file.
     * @param title the title of the related card of the game.
     * @param description the description of the related card of the game.
     */
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

    @Override
    public String toString() {
        return "[PUBLIC OBJECTIVE]: TITLE: " + title + "      DESCRIPTION: " + description;
    }
}
