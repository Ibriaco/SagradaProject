package it.polimi.se2018.Model;

public class Player {
    private String username;
    private String connectionType;
    private String viewType;
    private int tokens;
    private int playerScore;
    private PrivateObjective privateObjective;
    private WindowCard windowCard;
    private WindowCardAssociation[] windowCardAssociations;
    private int windowFrameNumber;


    public Player(String username) {
        windowCardAssociations = new WindowCardAssociation[2];
        this.username = username;
    }


    public String getUsername() {
        return username;
    }

    public String getConnectionType() {
        return connectionType;
    }

    public String getViewType() {
        return viewType;
    }

    public int getTokens() {
        return tokens;
    }

    public void setTokens(int tokens) {
        this.tokens = tokens;
    }

    public int getPlayerScore() {
        return playerScore;
    }

    public PrivateObjective getPrivateObjective() {
        return privateObjective;
    }

    // da testare
    public PrivateObjective drawCard(){
        return privateObjective;
    }

    // da testare
    public WindowCard chooseWindowCard(WindowCardAssociation w){ return windowCard; }

    // da testare
    public int getWindowFrameNumber(){
        return windowFrameNumber;
    }

    // da testare
    public WindowCardAssociation[] drawWindowCardAssociation(){
        return windowCardAssociations;
    }


}
