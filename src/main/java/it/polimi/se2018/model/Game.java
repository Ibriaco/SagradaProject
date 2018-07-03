package it.polimi.se2018.model;

import it.polimi.se2018.ToolCardFactory;
import it.polimi.se2018.model.event.*;
import it.polimi.se2018.MyObservable;
import it.polimi.se2018.MyObserver;
import it.polimi.se2018.PublicCardFactory;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import static it.polimi.se2018.ServerConfig.*;
import static it.polimi.se2018.model.Color.*;

/**Game class. This is the main class, where players, cards and dice are saved.
 * @author Gregorio Galletti
 * @author Ibrahim El Shemy
 */
public class Game implements MyObservable{
    private int playerNumber;
    private int turn = ONE_VALUE;
    private int round = ONE_VALUE;
    private int redAmount = RED_AMOUNT;
    private int greenAmount = GREEN_AMOUNT;
    private int yellowAmount = YELLOW_AMOUNT;
    private int purpleAmount = PURPLE_AMOUNT;
    private int blueAmount = BLUE_AMOUNT;
    private boolean init = BOOL_TRUE;
    private Player firstPlayer; //attriubuto che indica il palyer che inizai il round. NON IL TURNO.
    private ArrayList<WindowCard> windowCardList= new ArrayList<>();
    private List<PublicObjective> publicCards;
    private List<ToolCard> toolCards;
    private List<Player> players;
    private List<Die> rolledDice;
    private List<RoundCell> roundCells;
    private List<Color> colorList;
    private ArrayList<MyObserver> observerCollection = new ArrayList<>();
    private MVEvent mvEvent;
    private static final Logger LOGGER = Logger.getGlobal();

    public Game(int playerNumber) {

        this.playerNumber = playerNumber;
        players = new ArrayList<>();
        rolledDice = new ArrayList<>();
        colorList = new ArrayList<>();
        publicCards = new ArrayList<>();
        toolCards = new ArrayList<>();
        roundCells = new ArrayList<>();
        getAvailableColor();
    }

    public int getPlayerNumber() {

        return playerNumber;
    }

    public int getTurn() {

        return turn;
    }

    public int getRound() {

        return round;
    }

    public List<WindowCard> getWindowCardList() {
        return windowCardList;
    }



    public List<PublicObjective> getPublicCards() {

        return publicCards;
    }

    public List<Player> getPlayers() {

        return players;
    }

    public List<Die> getRolledDice() {

        return rolledDice;
    }

    public List<RoundCell> getRoundCells() {

        return roundCells;
    }

    public List<ToolCard> getToolCards() {

        return toolCards;
    }

    public Player getFirstPlayer() {
        return firstPlayer;
    }

    public void setFirstPlayer(Player firstPlayer) {
        this.firstPlayer = firstPlayer;
    }

    public void setPlayerNumber(int playerNumber) {

        this.playerNumber = playerNumber;
    }

    public void setRolledDice() {

        for(int i = 0; i < TWO_VALUE*playerNumber + 1; i++){
            rolledDice.add(i, new Die(colorList));
            reduceAmount(rolledDice.get(i).getColor());
            getAvailableColor();
        }
    }

    private void reduceAmount(Color color) {

        switch(color){
            case BLUE:
                blueAmount--;
                break;

            case GREEN:
                greenAmount--;
                break;

            case PURPLE:
                purpleAmount--;
                break;

            case RED:
                redAmount--;
                break;

            case YELLOW:
                yellowAmount--;
                break;
                default:
                    break;
        }
    }

    public void nextRound(){

        round++;
    }

    public void nextTurn(){
        if (turn == TWO_VALUE*playerNumber){
            turn = ONE_VALUE;
            nextRound();
        }
        else
            turn++;
    }

    public void addPlayer(Player p){

        if(players.size() < playerNumber)
            players.add(p);
    }

    public void setMvEvent(MVEvent mvEvent) {

        this.mvEvent = mvEvent;
    }

    public void dealPrivateCards() throws IOException, InvalidConnectionException, InvalidViewException, ParseException {

        List<Integer> randomNumbers = randomizeList(new ArrayList<>(Arrays.asList(0, 1, 2, 3, 4)));

        int j = 0;

        for (Player p: players) {
            p.drawCard(randomNumbers.get(j));
            PrivateCardEvent privateCardEvent = new PrivateCardEvent(p.getUsername(),p.getPrivateObjective().getTitle());
            setMvEvent(privateCardEvent);
            notifyObservers();
            j++;
        }
    }

    public void dealPublicCards() throws IOException, ParseException, InvalidConnectionException, InvalidViewException {
        List<String> publicCardName = new ArrayList<>();
        List<String> publicCardDesc = new ArrayList<>();
        List<String> publicCardScore = new ArrayList<>();
        List<Integer> randomNumbers = randomizeList(new ArrayList<>(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9)));
        for (int i=0; i<THREE_VALUE; i++){
            publicCards.add(drawPublicCard(randomNumbers.get(i)));
            publicCardName.add(getPublicCards().get(i).getTitle());
            publicCardDesc.add(getPublicCards().get(i).getDescription());
            publicCardScore.add(String.valueOf(getPublicCards().get(i).getScore()));
        }
        PublicCardEvent publicCardEvent = new PublicCardEvent("ALL", publicCardName, publicCardDesc, publicCardScore);
        setMvEvent(publicCardEvent);
        notifyObservers();
    }

    private PublicObjective drawPublicCard(int pos) throws ParseException, IOException{
        JSONParser parser = new JSONParser();

        JSONArray cards = (JSONArray) parser.parse(new FileReader("./src/main/resources/GameResources/publicCards.json"));
        JSONObject obj = (JSONObject) cards.get(pos);

        PublicCardFactory publicCardFactory = new PublicCardFactory();


        return publicCardFactory.createCard((String) obj.get("Title"), (String) obj.get("Description"), ((Long)obj.get("Score")).intValue());
    }

    public void dealToolCards() throws IOException, ParseException, InvalidConnectionException, InvalidViewException {
        List<String> toolCardName = new ArrayList<>();
        List<String> toolCardDesc = new ArrayList<>();
        List<Integer> randomNumbers = randomizeList(new ArrayList<>(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11)));
        for (int i=0; i<THREE_VALUE; i++){
            toolCards.add(drawToolCard(randomNumbers.get(i)));
            toolCardName.add(getToolCards().get(i).getTitle());
            toolCardDesc.add(getToolCards().get(i).getDescription());
        }
        ToolCardEvent toolCardEvent = new ToolCardEvent("ALL", toolCardName, toolCardDesc);
        setMvEvent(toolCardEvent);
        notifyObservers();
    }

    private ToolCard drawToolCard(int pos) throws ParseException, IOException{
        JSONParser parser = new JSONParser();
        ToolCardFactory toolCardFactory = new ToolCardFactory();
        JSONArray cards = (JSONArray) parser.parse(new FileReader("./src/main/resources/GameResources/toolCards.json"));
        JSONObject obj = (JSONObject) cards.get(pos);
        ToolCard tc = new ToolCard((String) obj.get("Title"),(String)obj.get("Description"),(String)obj.get("Type"));
        tc.getEffectList().add(toolCardFactory.createEffect(tc.getTitle()));
        return tc;
    }


    public void dealWindowCards() throws ParseException{

        JSONParser parser = new JSONParser();

        try{
            JSONArray cards = (JSONArray) parser.parse(new FileReader("./src/main/resources/GameResources/windows.json"));

            List<Integer> randomNumbers = new ArrayList<>();

            int windowN = cards.size();

            for (int i = 0; i < windowN; i+=2)
                randomNumbers.add(i);

            randomNumbers = randomizeList(randomNumbers);

            int j = 0;

            for (Player p: players) {
                p.drawWindowCards(cards, randomNumbers.get(j), randomNumbers.get(j+1));
                WindowCardEvent windowCardEvent = new WindowCardEvent(p.getUsername(),p.getWindowCardList());
                windowCardThread(windowCardEvent);
                j+=2;
            }

        }
        catch (IOException e){
            LOGGER.log(Level.SEVERE, e.toString(), e);
        }
    }

    private synchronized void windowCardThread(WindowCardEvent windowCardEvent){
        new Thread(() -> {setMvEvent(windowCardEvent);
            try {
                notifyObservers();
            } catch (IOException | ParseException | InvalidViewException | InvalidConnectionException e) {
                LOGGER.log(Level.SEVERE, e.toString(), e);
            }
        }).start();
    }

    private List<Integer> randomizeList(List<Integer> list){

        Random rnd = new Random();
        for (int i = list.size() - 1; i > 0; i--)
        {
            int index = rnd.nextInt(i + 1);
            int a = list.get(index);
            list.set(index, list.get(i));
            list.set(i, a);
        }
        return list;
    }

    private void getAvailableColor() {

        if(init) {
            colorList.add(GREEN);
            colorList.add(PURPLE);
            colorList.add(YELLOW);
            colorList.add(RED);
            colorList.add(BLUE);

            init = false;
        }
        else
            checkRemainingColors();
    }

    private void checkRemainingColors() {
        if (redAmount == 0)
            colorList.remove(RED);

        if (blueAmount == 0)
            colorList.remove(BLUE);

        if (greenAmount == 0)
            colorList.remove(GREEN);

        if (yellowAmount == 0)
            colorList.remove(YELLOW);

        if (purpleAmount == 0)
            colorList.remove(PURPLE);
    }

    public List<Color> getColorList(){

        return colorList;
    }

    public Player findPlayer(String username) {

        return players.stream()
                .filter(p -> username.equals(p.getUsername()))
                .findFirst().orElse(null);

    }

    public void updateWindowCardList(){
        int i=0;
        for (WindowCard w: windowCardList) {
            for (Player p: players) {
                if (w.getWindowName().equals(p.getWindowCard().getWindowName()))
                    windowCardList.set(i,p.getWindowCard());
            }i++;
        }
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
    public void notifyObservers() throws IOException, InvalidConnectionException, InvalidViewException, ParseException {
        for (MyObserver o: observerCollection) {
            try {
                o.update(this, mvEvent);
            } catch (InvalidDieException e) {
                LOGGER.log(Level.SEVERE, e.toString(), e);
            }
        }
    }
}
