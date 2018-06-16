package it.polimi.se2018.Model;

import it.polimi.se2018.Model.Event.MVEvent;
import it.polimi.se2018.Model.Event.PrivateCardEvent;
import it.polimi.se2018.Model.Event.PublicCardEvent;
import it.polimi.se2018.Model.Event.WindowCardEvent;
import it.polimi.se2018.MyObservable;
import it.polimi.se2018.MyObserver;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static it.polimi.se2018.Model.Color.*;

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
    private List<PublicObjective> publicCards;
    private List<ToolCard> toolCards;
    private List<Player> players;
    private List<Die> rolledDice;
    private List<RoundCell> roundCells;
    private List<Color> colorList;
    private ArrayList<MyObserver> observerCollection = new ArrayList<>();
    private MVEvent mvEvent;

    public Game(int playerNumber) {

        this.playerNumber = playerNumber;
        players = new ArrayList<>();
        rolledDice = new ArrayList<>();
        colorList = new ArrayList<>();
        publicCards = new ArrayList<>();
        setPublicObjectives();
        getAvailableColor();
    }

    /**
     *Method that reads from a file 3 PublicObjectives in a random way and returns them.
     */
    public void setPublicObjectives() {

        //creo 3 valori 1-10 random che indicano le 3 PO da creare
        //"cerco" gli indici nel file e per ognuno leggo titolo, descrizione e costo
        //creo quindi 3 diversi oggetti
        //li inserisco nell'arraylist

        String line;
        String x;
        String t;
        String d;
        String c;

        //1) creo i 3 indici random per estrarre le carte dal file
        int[] index = {0,0,0};
        Random rnd = new Random();

        index[0] = rnd.nextInt(9)+1;

        do
            index[1] = rnd.nextInt(9)+1;
        while (index[1] == index[0]);

        do
            index[2] = rnd.nextInt(9)+1;
        while (index[2] == index[1] || index[2] == index[0]);

        int i = 0;

        //lettura da file
        try (BufferedReader b = new BufferedReader(new FileReader("./src/PublicObjective.txt"))) {
            line = b.readLine();

            while(line != null) {
                x = line;
                if(x.equals(String.valueOf(index[0])) || x.equals(String.valueOf(index[1])) || x.equals(String.valueOf(index[2]))) {
                    t = b.readLine();
                    d = b.readLine();
                    c = b.readLine();
                    publicCards.add(selectType(x, t, d, c));
                    PublicCardEvent publicCardEvent = new PublicCardEvent("ALL",publicCards.get(i).toString());
                    setMvEvent(publicCardEvent);
                    notifyObservers();

                    i++;
                    if(i == 3)
                        line = null;
                    else
                        line = b.readLine();
                }
                else
                    line = b.readLine();
            }
        }
        catch (Exception e) {

        }
    }

    private PublicObjective selectType(String numberStr, String title, String desc, String score) {
        int scoreInt = Integer.parseInt(score.substring(1));
        int number = Integer.parseInt(numberStr);

        switch (number){
        case 1:
            return new LightShade(number, title, desc, scoreInt);
        case 2:
            return new MediumShade(number, title, desc, scoreInt);
        case 3:
            return new DeepShade(number, title, desc, scoreInt);
        case 4:
            return new ColorBonus(number, title, desc, scoreInt);
        case 5:
            return new Shade(number, title, desc, scoreInt);
        case 6:
            return new ColumnShade(number, title, desc, scoreInt);
        case 7:
            return new RowShade(number, title, desc, scoreInt);
        case 8:
            return new RowColor(number, title, desc, scoreInt);
        case 9:
            return new ColumnShade(number, title, desc, scoreInt);
        case 10:
            return new ColorDiagonals(number, title, desc, scoreInt);
        default: return null;
        }
    }

    public int getPlayerNumber() {

        return playerNumber;
    }

    public Player getPlayer (String username) throws InvalidViewException {
        Player player = new Player(username, "CLI");
        for (Player p: players) {
            if (p.getUsername().equals(username))
                player = p;
        }
        return player;
    }

    public int getTurn() {

        return turn;
    }

    public int getRound() {

        return round;
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

        turn++;
    }

    public void addPlayer(Player p){

        if(players.size() < playerNumber)
            players.add(p);
    }

    public void setMvEvent(MVEvent mvEvent) {

        this.mvEvent = mvEvent;
    }

    public void dealPrivateCards() throws RemoteException, InvalidConnectionException, WindowCardAssociationException, InvalidViewException {

        List<Integer> randomNumbers = randomizeList(new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5)));

        int j = 0;

        for (Player p: players) {
            p.drawCard(randomNumbers.get(j));
            PrivateCardEvent privateCardEvent = new PrivateCardEvent(p.getUsername(),p.getPrivateObjective().toString());
            setMvEvent(privateCardEvent);
            notifyObservers();
            j++;
        }
    }

    public void dealWindowCards(){

        JSONParser parser = new JSONParser();

        try{
            JSONArray cards = (JSONArray) parser.parse(new FileReader("./src/windows.json"));

            List<Integer> randomNumbers = new ArrayList<>();

            int windowN = cards.size();

            for (int i = 0; i < windowN; i+=2)
                randomNumbers.add(i);

            randomNumbers = randomizeList(randomNumbers);

            int j = 0;

            for (Player p: players) {
                p.drawWindowCards(cards, randomNumbers.get(j), randomNumbers.get(j+1));
                WindowCardEvent windowCardEvent = new WindowCardEvent(p.getUsername(),p.getWindowCardList());
                setMvEvent(windowCardEvent);
                notifyObservers();
                j+=2;
            }

        }
        catch (IOException | ParseException e){
            e.printStackTrace();
        } catch (WindowCardAssociationException e) {
            e.printStackTrace();
        } catch (InvalidConnectionException e) {
            e.printStackTrace();
        } catch (InvalidViewException e) {
            e.printStackTrace();
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

    @Override
    public void registerObserver(MyObserver observer) throws RemoteException {
        observerCollection.add(observer);
    }

    @Override
    public void unregisterObserver(MyObserver observer) throws RemoteException {
        observerCollection.remove(observer);
    }

    @Override
    public void notifyObservers() throws RemoteException, InvalidConnectionException, InvalidViewException, WindowCardAssociationException {
        for (MyObserver o: observerCollection) {
            o.update(this, mvEvent);
        }
    }
}
