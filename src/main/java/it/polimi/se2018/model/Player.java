package it.polimi.se2018.model;

import it.polimi.se2018.MyObservable;
import it.polimi.se2018.MyObserver;
import it.polimi.se2018.model.event.MVEvent;
import it.polimi.se2018.org.json.simple.JSONArray;
import it.polimi.se2018.org.json.simple.JSONObject;
import it.polimi.se2018.org.json.simple.parser.JSONParser;
import it.polimi.se2018.org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import static it.polimi.se2018.ServerConfig.CLI_UI;
import static it.polimi.se2018.ServerConfig.GUI_UI;

/**Player class of the game.
 * @author Gregorio Galletti
 */
public class Player implements MyObservable{

    private String username;
    private String viewType;
    private int tokens;
    private boolean ad = false;
    private int playerScore;
    private PrivateObjective privateObjective;
    private WindowCard windowCard;
    private List<WindowCard> windowCardList;
    private ArrayList<MyObserver> observerCollection = new ArrayList<>();
    private MVEvent mvEvent;
    private boolean disconnected = false;


    public Player(String username, String viewType) throws InvalidViewException {

        if(viewType.equals(GUI_UI) || viewType.equals(CLI_UI))
            this.viewType = viewType;
        else
            throw new InvalidViewException();

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

    public PrivateObjective getPrivateObjective() {

        return privateObjective;
    }

    public List<WindowCard> getWindowCardList() {
        return windowCardList;
    }

    public boolean isAd(){
        return ad;
    }

    public void setAd(boolean myVar){
        ad = myVar;
    }


    /**
     * Method that draws PrivateObjective.
     * @param cardNumber Refers to the identification number of Private Objective Card .
     */
    public void drawCard(int cardNumber) throws IOException, ParseException {

        JSONParser parser = new JSONParser();

        JSONArray cards = (JSONArray) parser.parse(new FileReader("./src/main/resources/GameResources/privateCards.json"));
        JSONObject obj = (JSONObject) cards.get(cardNumber);

        String title = (String) obj.get("Title");
        System.out.println(title);
        String description = (String) obj.get("Description");
        Color color = Color.returnMatch((String) obj.get("Color"));

        privateObjective = new PrivateObjective(title, description, 0, color);

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

    @Override
    public void registerObserver(MyObserver observer) throws RemoteException {
        observerCollection.add(observer);
    }

    @Override
    public void unregisterObserver(MyObserver observer) throws RemoteException {
        observerCollection.remove(observer);
    }

    @Override
    public void notifyObservers() throws IOException, InvalidConnectionException, InvalidViewException, ParseException, InvalidDieException {
        for (MyObserver o: observerCollection) {
            o.update(this, mvEvent);
        }
    }

    public void setDisconnected(boolean disconnected) {
        this.disconnected = disconnected;
    }

    public boolean isDisconnected() {
        return disconnected;
    }
}