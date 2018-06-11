package it.polimi.se2018.Model;

import java.io.BufferedReader;
import java.io.FileReader;
/**Player class of the game.
 * @author Gregorio Galletti
 */
public class Player {

    private String username;
    private String viewType;
    private int tokens;
    private int playerScore;
    private PrivateObjective privateObjective;
    private WindowCard windowCard;
    private WindowCardAssociation[] windowCardAssociations;
    private int windowFrameNumber;


    public Player(String username, String viewType) throws InvalidViewException {

        if(viewType.equals("GUI") || viewType.equals("CLI"))
            this.viewType = viewType;
        else
            throw new InvalidViewException();

        windowCardAssociations = new WindowCardAssociation[2];
        this.username = username;
        this.playerScore = 0;
        this.tokens = 0;
        windowCard = new WindowCard();
    }

    public String getUsername() {

        return username;
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

    public void reduceTokens(int tokens){
        if(this.tokens >= tokens)
            this.tokens -= tokens;
    }

    public int getPlayerScore() {

        return playerScore;
    }

    public WindowCard getWindowCard() {

        return windowCard;
    }

    public void setPlayerScore(int playerScore) {

        this.playerScore += playerScore;
    }

    public WindowCardAssociation[] getWindowCardAssociations() {

        return windowCardAssociations;
    }

    public PrivateObjective getPrivateObjective() {

        return privateObjective;
    }


    /**
     * Method that draws PrivateObjective.
     * @param cardNumber Refers to the identification number of Private Objective Card .
     */
    public void drawCard(int cardNumber){

        String line;
        String x;
        String t;
        String d;
        Color c=null;

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
            default: break;
        }

        try (BufferedReader b = new BufferedReader(new FileReader("./src/PrivateObjective.txt"))) {
            line = b.readLine();
            while(line != null) {
                x = line;

                if(x.equals(String.valueOf(cardNumber))) {
                    t = b.readLine();
                    d = b.readLine();
                    privateObjective = new PrivateObjective(Integer.parseInt(x), t, d,0, c);
                    line = null;
                }
                else
                    line = b.readLine();
            }
        }
        catch (Exception e) {
        }
    }

    /**
     *Method that lets the Player choose either taking the front or the back of a given WindowCard.
     * @param w Refers to a WindowCard.
     * @param side Refers to a side of the WindowCard.
     */
    public void chooseWindowCard(WindowCardAssociation w, int side){
        if(side == 0)
            windowCard = w.getFront();
        if(side == 1)
            windowCard = w.getBack();
    }

    public void setWindowCard(WindowCard w){

        this.windowCard = w;
    }

    public int getWindowFrameNumber(){

        return windowFrameNumber;
    }

    /**
     * Method that lets the Player choose either the first or the second WindowCard.
     * @param windowCardNumber1 Refers to the first WindowCard.
     * @param windowCardNumber2 Refers to the second WindowCard.
     */
    public void drawWindowCardAssociation(int windowCardNumber1, int windowCardNumber2){
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

        WindowCardAssociation a1 = null;
        try {
            a1 = new WindowCardAssociation(w1, w2);
        } catch (WindowCardAssociationException e) {
        }
        windowCardAssociations[0] = a1;

        WindowCard w3 = new WindowCard();
        WindowCard w4 = new WindowCard();

        WindowCardAssociation a2 = null;
        try {
            a2 = new WindowCardAssociation(w3, w4);
        } catch (WindowCardAssociationException e) {
        }
        windowCardAssociations[1] = a2;

        String line;
        String x;
        String t;
        String d;
        String row;
        int counter = 0;
        int cardN = 0;
        boolean front = true;

        //lettura da file
        try (BufferedReader b = new BufferedReader(new FileReader("./src/WindowCard.txt"))) {
            line = b.readLine();
            while (counter < 4) {
                x = line;
                if (x.equals(String.valueOf(windowCardNumber1)) || x.equals(String.valueOf(windowCardNumber2))) {
                    t = b.readLine();
                    d = b.readLine();

                    for(int i = 0; i < 4; i++){
                        row = b.readLine();
                        parseRow(row, i, counter, cardN);
                    }

                    front = updateSide(cardN,t,d,front);
                    if(front)
                        cardN = 1;
                    counter++;
                    line = b.readLine();

                } else
                    line = b.readLine();
            }
        } catch (Exception e) {
        }

    }

    private boolean updateSide(int cardN, String t, String d, boolean front) {

        if(front) {
            windowCardAssociations[cardN].getFront().setWindowName(t);
            windowCardAssociations[cardN].getFront().setDifficulty(Integer.valueOf(d.substring(1)));
            return false;
        }
        else {
            windowCardAssociations[cardN].getBack().setWindowName(t);
            windowCardAssociations[cardN].getBack().setDifficulty(Integer.valueOf(d.substring(1)));
            return true;
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
                    windowCardAssociations[cardN].getFront().setCell(new Cell(Color.RED, 0), x, y);
                    break;
                case "y":
                    windowCardAssociations[cardN].getFront().setCell(new Cell(Color.YELLOW, 0), x, y);
                    break;
                case "b":
                    windowCardAssociations[cardN].getFront().setCell(new Cell(Color.BLUE, 0), x, y);
                    break;
                case "g":
                    windowCardAssociations[cardN].getFront().setCell(new Cell(Color.GREEN, 0), x, y);
                    break;
                case "p":
                    windowCardAssociations[cardN].getFront().setCell(new Cell(Color.PURPLE, 0), x, y);
                    break;
                case "x":
                    windowCardAssociations[cardN].getFront().setCell(new Cell(Color.WHITE, 0), x, y);
                    break;
                default:
                    windowCardAssociations[cardN].getFront().setCell(new Cell(Color.WHITE, Integer.valueOf(s)), x, y);
            }
        }
        else{
            switch (s) {
                case "r":
                    windowCardAssociations[cardN].getBack().setCell(new Cell(Color.RED, 0), x, y);
                    break;
                case "y":
                    windowCardAssociations[cardN].getBack().setCell(new Cell(Color.YELLOW, 0), x, y);
                    break;
                case "b":
                    windowCardAssociations[cardN].getBack().setCell(new Cell(Color.BLUE, 0), x, y);
                    break;
                case "g":
                    windowCardAssociations[cardN].getBack().setCell(new Cell(Color.GREEN, 0), x, y);
                    break;
                case "p":
                    windowCardAssociations[cardN].getBack().setCell(new Cell(Color.PURPLE, 0), x, y);
                    break;
                case "x":
                    windowCardAssociations[cardN].getBack().setCell(new Cell(Color.WHITE, 0), x, y);
                    break;
                default:
                    windowCardAssociations[cardN].getBack().setCell(new Cell(Color.WHITE, Integer.valueOf(s)), x, y);
            }
        }
    }
}