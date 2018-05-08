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

    public int getPlayerScore() {
        return playerScore;
    }

    public PrivateObjective getPrivateObjective() {
        return privateObjective;
    }

    public PrivateObjective drawCard(){
        return privateObjective;
    }

    public WindowCard chooseWindowCard(WindowCardAssociation w){ return windowCard; }

    public int getWindowFrameNumber(){
        return windowFrameNumber;
    }

    public void setTokens(int tokens) {
        this.tokens = tokens;
    }

    public WindowCardAssociation[] drawWindowCardAssociation(){
        return windowCardAssociations;
    }


}
