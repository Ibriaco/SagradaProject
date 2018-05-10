package it.polimi.se2018.Model;

import it.polimi.se2018.Controller.ToolCard;

import java.util.ArrayList;

public class Game {
    private int playerNumber;
    private int turn;
    private int round;
    private int gameDifficulty;
    private String gameType;
    private ArrayList<PublicObjective> publicCards;
    private ArrayList<ToolCard> toolCards;
    private ArrayList<Player> players;
    private ArrayList<Die> rolledDice;
    private ArrayList<RoundCell> roundCells;


    public Game(int playerNumber, String gameType) {
        this.playerNumber = playerNumber;
        this.gameType = gameType;
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

    public ArrayList<PublicObjective> getPublicCards() {
        return publicCards;
    }

    public ArrayList<ToolCard> getToolCards() {
        return toolCards;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public ArrayList<Die> getRolledDice() {
        return rolledDice;
    }

    public ArrayList<RoundCell> getRoundCells() {
        return roundCells;
    }

    public void setPlayerNumber(int playerNumber) {
        this.playerNumber = playerNumber;
    }

    public void setRolledDice(ArrayList<Die> rolledDice) {
        this.rolledDice = rolledDice;
    }

    // da testare
    public void nextRound(){

    }

    // da testare
    public void nextTurn(){

    }
}
