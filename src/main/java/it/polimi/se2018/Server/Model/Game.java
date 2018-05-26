package it.polimi.se2018.Server.Model;


import it.polimi.se2018.Server.Controller.ToolCard;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static it.polimi.se2018.Server.Model.Color.*;

/**Game class. This is the main class, where players, cards and dice are saved.
 * @author Gregorio Galletti
 * @author Ibrahim El Shemy
 */
public class Game {
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

    public Game(int playerNumber) throws InvalidGameCreationException {
        if(playerNumber < 2 || playerNumber > 4)
            throw new InvalidGameCreationException();
        else {
            this.playerNumber = playerNumber;
            players = new ArrayList<>();
            rolledDice = new ArrayList<>();
            colorList = new ArrayList<>();
            publicCards = new ArrayList<>();
            setPublicObjectives();
            getAvailableColor();
        }
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

    public void dealPrivateCards() throws WindowCardAssociationException{

        //creo vettore e lo shufflo
        int[] ar= {1,2,3,4,5};
        Random rnd = new Random();
        for (int i = ar.length - 1; i > 0; i--)
        {
            int index = rnd.nextInt(i + 1);
            int a = ar[index];
            ar[index] = ar[i];
            ar[i] = a;
        }

        int j = 0;

        for (Player p: players) {
            p.drawCard(ar[j]);
            j++;
        }
    }

    public void dealWindowCards() throws WindowCardAssociationException{
        //creo vettore e lo shufflo
        //leggere da file il n di windowcard
        //creare array / lista con i valori
        //fare lo shuffle e a
        int windowNumber = 0;
        List<Integer> ar = new ArrayList<>();

        String line;
        //leggo da file
        try (BufferedReader b = new BufferedReader(new FileReader("./src/WindowCard.txt"))) {
            line = b.readLine();
            while (line != null) {
                windowNumber = Integer.valueOf(line);

                for(int i = 0; i < 13; i++)
                    b.readLine();

                line = b.readLine();
            }
        } catch (Exception e) {}

        for (int i = 0; i < windowNumber; i++)
            ar.add(i + 1);

        Random rnd = new Random();
        for (int i = ar.size() - 1; i > 0; i--)
        {
            int index = rnd.nextInt(i + 1);
            int a = ar.get(index);
            ar.set(index, ar.get(i));
            ar.set(i, a);
        }

        int j = 0;

        for (Player p: players) {
            p.drawWindowCardAssociation(ar.get(j), ar.get(j+1));
            j+=2;
        }
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
}
