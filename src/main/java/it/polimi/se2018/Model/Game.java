package it.polimi.se2018.Model;

import it.polimi.se2018.Controller.ToolCard;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Game {
    private int playerNumber;
    private int turn = 1;
    private int round = 1;
    private int gameDifficulty;
    private String gameType;
    private List<PublicObjective> publicCards;
    private List<ToolCard> toolCards;
    private List<Player> players;
    private List<Die> rolledDice;
    private List<RoundCell> roundCells;


    public Game(int playerNumber, String gameType) {
        this.playerNumber = playerNumber;
        this.gameType = gameType;
        players = new ArrayList<Player>();
        rolledDice = new ArrayList<Die>();
        publicCards = setPublicObjectives();

    }

    public ArrayList<PublicObjective> setPublicObjectives() {

        //creo 3 valori 1-10 random che indicano le 3 PO da creare
        //"cerco" gli indici nel file e per ognuno leggo titolo, descrizione e costo
        //creo quindi 3 diversi oggetti
        //li inserisco nell'arraylist

        ArrayList<PublicObjective> list = new ArrayList<PublicObjective>();

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

        //leggo dal file
        String line;
        String x;
        String t;
        String d;
        String c;

        //lettura da file
        try (BufferedReader b = new BufferedReader(new FileReader("./src/PublicObjective.txt"))) {
            line = b.readLine();
            while(line != null) {
                x = line;

                if(x.equals(String.valueOf(index[0])) || x.equals(String.valueOf(index[1])) || x.equals(String.valueOf(index[2]))) {
                    t = b.readLine();
                    d = b.readLine();
                    c = b.readLine();
                    list.add(selectType(x, t, d, c));
                    line = b.readLine();
                }
                else
                    line = b.readLine();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    private PublicObjective selectType(String numberStr, String title, String desc, String score) {
        int scoreInt = Integer.parseInt(score.substring(1));
        int number = Integer.parseInt(numberStr);

        switch (number){
        case 1:
            return new LightShade(number, title, desc, "PublicObjective", scoreInt);
        case 2:
            return new MediumShade(number, title, desc, "PublicObjective", scoreInt);
        case 3:
            return new DeepShade(number, title, desc, "PublicObjective", scoreInt);
        case 4:
            return new ColorBonus(number, title, desc, "PublicObjective", scoreInt);
        case 5:
            return new Shade(number, title, desc, "PublicObjective", scoreInt);
        case 6:
            return new ColumnShade(number, title, desc, "PublicObjective", scoreInt);
        case 7:
            return new RowShade(number, title, desc, "PublicObjective", scoreInt);
        case 8:
            return new RowColor(number, title, desc, "PublicObjective", scoreInt);
        case 9:
            return new ColumnShade(number, title, desc, "PublicObjective", scoreInt);
        case 10:
            return new ColorDiagonals(number, title, desc, "PublicObjective", scoreInt);
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

    public int getGameDifficulty() {

        return gameDifficulty;
    }

    public String getGameType() {

        return gameType;
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

    public void setPlayerNumber(int playerNumber) {

        this.playerNumber = playerNumber;
    }

    public void setRolledDice(Die toRoll) {

        for(int i=0; i<2*playerNumber + 1; i++){
            rolledDice.add(i,toRoll.rollDie());
        }
    }

    // da testare
    public void nextRound(){

        round++;
    }

    // da testare
    public void nextTurn(){

        turn++;
    }

    public void addPlayer(Player p){

        players.add(p);
    }

    public void dealPrivateCards(){

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

  /*      for(int i=0;i<ar.length;i++)
            System.out.println(ar[i]);
*/
        int j = 0;


        for (Player p: players) {
            p.drawCard(ar[j]);
            j++;
        }
    }

    public void dealWindowCards(){
        //creo vettore e lo shufflo
        int[] ar= {1,2,3,4,5,6,7,8,9,10,11,12};
        Random rnd = new Random();
        for (int i = ar.length - 1; i > 0; i--)
        {
            int index = rnd.nextInt(i + 1);
            int a = ar[index];
            ar[index] = ar[i];
            ar[i] = a;
        }

       /* for(int i=0;i<ar.length;i++)
            System.out.println(ar[i]);
*/
        int j = 0;

        for (Player p: players) {
            p.drawWindowCardAssociation(ar[j], ar[j+1]);
            System.out.println("Numeri casuali: " + ar[j] + ", " + ar[j+1]);
            j+=2;
        }
    }
}
