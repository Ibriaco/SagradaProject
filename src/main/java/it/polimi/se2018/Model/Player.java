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

    public void printAss(){
        System.out.println("Stampo le carte da scegliere: ");

        for(int i=0;i<2;i++){
            System.out.println("Name: " + windowCardAssociations[i].getFront().getWindowName());
            System.out.println("Difficulty: " + windowCardAssociations[i].getFront().getDifficulty());
        }
    }

    // da testare
    public void drawWindowCardAssociation(int windowCardNumber1, int windowCardNumber2) {
        //apro file
        //cerco i numeri passati come parametro
        //appena ne trovo uno leggo: Nome, Difficoltà e Griglia
        //devo interpretare la griglia e creare una windowcard
        //leggo ancora nome, difficolta e griglia e creo la seconda windowcard
        //setto il front e il back della prima WindowCardAssociation
        //ripeto la stessa cosa con il secondo numero passato
        //inserisco le windowcardassociation create nel vettore
        //fine
        WindowCard w1 = new WindowCard();
        WindowCard w2 = new WindowCard();

        WindowCardAssociation a1 = new WindowCardAssociation(w1, w2);
        windowCardAssociations[0] = a1;

        WindowCard w3 = new WindowCard();
        WindowCard w4 = new WindowCard();

        WindowCardAssociation a2 = new WindowCardAssociation(w3, w4);
        windowCardAssociations[1] = a2;

        String line;
        String x;
        String t;
        String d;
        String row;
        int counter=0;
        int cardN = 0;

        //lettura da file
        try (BufferedReader b = new BufferedReader(new FileReader("./src/WindowCard.txt"))) {
            line = b.readLine();
            while (line != null) {
                x = line;

                if (x.equals(String.valueOf(windowCardNumber1)) || x.equals(String.valueOf(windowCardNumber2))) {
                    t = b.readLine();   //nome
                    d = b.readLine();   //difficolta

                    for(int i=0; i<4; i++){
                        row = b.readLine();
                        parseRow(row, i, counter, cardN);
                    }
                    counter++;
                    if (counter == 2) {
                        counter = 0;
                        cardN = 1;
                    }
                    windowCardAssociations[cardN].getFront().setWindowName(t);
                    windowCardAssociations[cardN].getFront().setDifficulty(Integer.valueOf(d.substring(1)));

                    line = b.readLine();
                } else
                    line = b.readLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void parseRow(String row, int x, int side, int cardN){

        String[] splitRow;
        splitRow = row.split("-");

        for(int i=0;i<5;i++)
            selectComponent(splitRow[i], i, x, side, cardN);
    }

    private void selectComponent(String s, int y, int x, int side, int cardN) {

        if(side == 0) {
            switch (s) {
                case "r":
                    //windowCardAssociations[cardN].getFront().getGridCell(x, y).setColor(Color.RED);
                    windowCardAssociations[cardN].getFront().setCell(new Cell(Color.RED, 0), x, y);
                    break;
                case "y":
                    //windowCardAssociations[cardN].getFront().getGridCell(x, y).setColor(Color.YELLOW);
                    windowCardAssociations[cardN].getFront().setCell(new Cell(Color.YELLOW, 0), x, y);
                    break;
                case "b":
                    //windowCardAssociations[cardN].getFront().getGridCell(x, y).setColor(Color.BLUE);
                    windowCardAssociations[cardN].getFront().setCell(new Cell(Color.BLUE, 0), x, y);
                    break;
                case "g":
                    //windowCardAssociations[cardN].getFront().getGridCell(x, y).setColor(Color.GREEN);
                    windowCardAssociations[cardN].getFront().setCell(new Cell(Color.GREEN, 0), x, y);
                    break;
                case "p":
                    //windowCardAssociations[cardN].getFront().getGridCell(x, y).setColor(Color.PURPLE);
                    windowCardAssociations[cardN].getFront().setCell(new Cell(Color.PURPLE, 0), x, y);
                    break;
                case "x":
                    break;
                default:
                    //windowCardAssociations[cardN].getFront().getGridCell(x, y).setShade(Integer.valueOf(s));
                    windowCardAssociations[cardN].getFront().setCell(new Cell(null, Integer.valueOf(s)), x, y);
            }
        }
        else{
            switch (s) {
                case "r":
                    //windowCardAssociations[cardN].getBack().getGridCell(x, y).setColor(Color.RED);
                    windowCardAssociations[cardN].getBack().setCell(new Cell(Color.RED, 0), x, y);
                    break;
                case "y":
                    //windowCardAssociations[cardN].getBack().getGridCell(x, y).setColor(Color.YELLOW);
                    windowCardAssociations[cardN].getBack().setCell(new Cell(Color.YELLOW, 0), x, y);
                    break;
                case "b":
                    //windowCardAssociations[cardN].getBack().getGridCell(x, y).setColor(Color.BLUE);
                    windowCardAssociations[cardN].getBack().setCell(new Cell(Color.BLUE, 0), x, y);
                    break;
                case "g":
                    //windowCardAssociations[cardN].getBack().getGridCell(x, y).setColor(Color.GREEN);
                    windowCardAssociations[cardN].getBack().setCell(new Cell(Color.GREEN, 0), x, y);
                    break;
                case "p":
                    //windowCardAssociations[cardN].getBack().getGridCell(x, y).setColor(Color.PURPLE);
                    windowCardAssociations[cardN].getBack().setCell(new Cell(Color.PURPLE, 0), x, y);
                    break;
                case "x":
                    break;
                default:
                    windowCardAssociations[cardN].getBack().setCell(new Cell(null, Integer.valueOf(s)), x, y);
                    //windowCardAssociations[cardN].getBack().getGridCell(x, y).setShade(Integer.valueOf(s));
            }
        }
    }
}