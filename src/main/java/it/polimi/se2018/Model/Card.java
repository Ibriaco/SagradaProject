package it.polimi.se2018.Model;

/**
 * Abstract class for the Card object of the game.
 * @author Ibrahim El Shemy
 */

public abstract class Card {

    private String title;
    private String description;

    /**
     * Constructor the Class.
     * @param title the title of the related card of the game.
     * @param description the description of the related card of the game.
     */
    public Card( String title, String description) {
        this.title = title;
        this.description = description;
    }


    public String getTitle(){

        return title;
    }

    public String getDescription(){

        return description;
    }


    @Override
    public String toString() {
        return "[PUBLIC OBJECTIVE]: TITLE: " + title + "      DESCRIPTION: " + description;
    }
}
