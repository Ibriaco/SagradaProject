package it.polimi.se2018.Model;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

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
    private List<WindowCard> windowCardList;


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

    public List<WindowCard> getWindowCardList() {
        return windowCardList;
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
     *Method that lets the Player choose a WindowCard.
     * @param windowName Refers to a name of WindowCard that player choose.
     */
    public void chooseWindowCard(String windowName){
        for (WindowCard w : windowCardList) {
            if (w.getWindowName().equals(windowName))
                this.windowCard = w;
        }
    }

    public void setWindowCard(WindowCard w){

        this.windowCard = w;
    }

    /**
     * Method that lets the Player choose either the first or the second WindowCard.
     * @param windowCardNumber1 Refers to the first WindowCard.
     * @param windowCardNumber2 Refers to the second WindowCard.
     */
    public void drawWindowCards(JSONArray cards, int windowCardNumber1, int windowCardNumber2){

        windowCardList = new ArrayList<>();

        WindowCard w1 = createCard(cards, windowCardNumber1);
        WindowCard w2 = createCard(cards, windowCardNumber1 + 1);
        WindowCard w3 = createCard(cards, windowCardNumber2);
        WindowCard w4 = createCard(cards, windowCardNumber2 + 1);

        windowCardList.add(w1);
        windowCardList.add(w2);
        windowCardList.add(w3);
        windowCardList.add(w4);

        for (WindowCard w: windowCardList) {
            System.out.println(w.getWindowName() + " " + w.getDifficulty());
        }

    }

    private WindowCard createCard(JSONArray cards, int windowCardNumber) {

        int r = 0;
        int c = 0;
        WindowCard w = new WindowCard();
        JSONObject obj = (JSONObject) cards.get(windowCardNumber);

        w.setWindowName((String) obj.get("Title"));
        w.setDifficulty(Integer.valueOf((String)obj.get("Difficulty")));

        JSONArray rows = (JSONArray) obj.get("Grid");

        for (Object row: rows) {
            JSONArray elements = (JSONArray) row;
            for (Object element: elements) {
                //System.out.println(element);
                String stringElement = (String) element;
                Color currentColor = Color.returnMatch((stringElement));

                if(currentColor.equals(Color.NOT_A_COLOR))
                    w.setCell(new Cell(Color.WHITE, Integer.parseInt(stringElement)), r, c);
                else
                    w.setCell(new Cell(currentColor, 0), r, c);
                c++;
            }
            r++;
            c = 0;
        }

        return w;
    }
}