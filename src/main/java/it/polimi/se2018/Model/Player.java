package it.polimi.se2018.Model;

import java.io.BufferedReader;
import java.io.FileReader;

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

    public WindowCard getWindowCard() {

        return windowCard;
    }


    public void setPlayerScore(int playerScore) {
        this.playerScore = playerScore;
    }

    public PrivateObjective getPrivateObjective() {

        return privateObjective;
    }

    // da testare
    public void drawCard(int cardNumber){

        String line;
        String x;
        String t;
        String d;
        Color c=null;
        System.out.println("num " + cardNumber);
        switch (cardNumber){
            case 1:
                c = Color.RED; break;
            case 2:
                c = Color.YELLOW; break;
            case 3:
                c = Color.GREEN; break;
            case 4:
                c = Color.BLUE; break;
            case 5:
                c = Color.PURPLE; break;
        }

        System.out.println("COLORE " + c.toString());

        try (BufferedReader b = new BufferedReader(new FileReader("./src/PrivateObjective.txt"))) {
            line = b.readLine();
            while(line != null) {
                x = line;

                if(x.equals(String.valueOf(cardNumber))) {
                    t = b.readLine();
                    d = b.readLine();
                    privateObjective = new PrivateObjective(Integer.parseInt(x), t, d, "PublicObjective",1, c);
                    line=null;
                }
                else
                    line = b.readLine();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    // da testare
    public WindowCard chooseWindowCard(WindowCardAssociation w){

        return windowCard;
    }

    // da testare
    public int getWindowFrameNumber(){

        return windowFrameNumber;
    }

    // da testare
    public void drawWindowCardAssociation(int windowCardNumber1, int windowCardNumber){
        //apro file
        //cerco i numeri passati come parametro
        //appena ne trovo uno leggo: Nome, Difficoltà e Griglia
        //devo interpretare la griglia e creare una windowcard
        //leggo ancora nome, difficolta e griglia e creo la seconda windowcard
        //setto il front e il back della prima WindowCardAssociation
        //ripeto la stessa cosa con il secondo numero passato
        //inserisco le windowcardassociation create nel vettore
        //fine

    }



}