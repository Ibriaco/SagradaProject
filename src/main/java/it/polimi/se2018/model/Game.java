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

import static it.polimi.se2018.model.Color.*;

/**Game class. This is the main class, where players, cards and dice are saved.
 * @author Gregorio Galletti
 * @author Ibrahim El Shemy
 */
public class Game implements MyObservable{
    private int playerNumber;
    private int turn = 1;
    private int round = 1;
    private int redAmount = 18;
    private int greenAmount = 18;
    private int yellowAmount = 18;
    private int purpleAmount = 18;
    private int blueAmount = 18;
    private boolean init = true;
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
    private static final Logger LOGGER = Logger.getLogger( Game.class.getName() );

    public Game(int playerNumber) {

        this.playerNumber = playerNumber;
        players = new ArrayList<>();
        rolledDice = new ArrayList<>();
        colorList = new ArrayList<>();
        publicCards = new ArrayList<>();
        toolCards = new ArrayList<>();
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

        for(int i = 0; i < 2*playerNumber + 1; i++){
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
        System.out.println(turn);
        if (turn == 2*playerNumber){
            turn = 1;
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
        ArrayList<String> publicCard = new ArrayList<>();
        List<Integer> randomNumbers = randomizeList(new ArrayList<>(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9)));
        for (int i=0; i<3; i++){
            publicCards.add(drawPublicCard(randomNumbers.get(i)));
            publicCard.add("[PUBLIC OBJECTIVE]:\tTITLE: " + getPublicCards().get(i).getTitle() + "\tDESCRIPTION: " + getPublicCards().get(i).getDescription() + "\tSCORE: " + getPublicCards().get(i).getScore());
        }
        PublicCardEvent publicCardEvent = new PublicCardEvent("ALL", publicCard);
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
        ArrayList<String> toolCard = new ArrayList<>();
        //ToolCardFactory toolCardFactory = new ToolCardFactory();
        List<Integer> randomNumbers = randomizeList(new ArrayList<>(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11)));
        for (int i=0; i<3; i++){
            toolCards.add(drawToolCard(randomNumbers.get(i)));
            toolCard.add("[TOOL CARD]:\tTITLE: " + getToolCards().get(i).getTitle() + "\tDESCRIPTION: " + getToolCards().get(i).getDescription());
            //toolCards.get(i).getEffectList().add(toolCardFactory.createEffect(getToolCards().get(i).getTitle()));

        }
        ToolCardEvent toolCardEvent = new ToolCardEvent("ALL", toolCard);
        setMvEvent(toolCardEvent);
        notifyObservers();
    }

    private ToolCard drawToolCard(int pos) throws ParseException, IOException{
        JSONParser parser = new JSONParser();
        ToolCardFactory toolCardFactory = new ToolCardFactory();
        JSONArray cards = (JSONArray) parser.parse(new FileReader("./src/main/resources/GameResources/toolCards.json"));
        JSONObject obj = (JSONObject) cards.get(pos);
        ToolCard tc = new ToolCard((String) obj.get("Title"),(String)obj.get("Description"));
        //return new ToolCard((String) obj.get("Title"),(String)obj.get("Description"));
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
                new Thread(new Runnable(){
                    public void run() {setMvEvent(windowCardEvent);
                        try {
                            notifyObservers();
                        } catch (IOException | ParseException | InvalidViewException | InvalidConnectionException e) {
                            e.printStackTrace();
                        }
                    }}).start();
                j+=2;
            }

        }
        catch (IOException e){
            LOGGER.log(Level.SEVERE, e.toString(), e);
        }
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
                e.printStackTrace();
            }
        }
    }
}
