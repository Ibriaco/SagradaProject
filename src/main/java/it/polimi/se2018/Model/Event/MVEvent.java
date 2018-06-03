package it.polimi.se2018.Model.Event;

import it.polimi.se2018.Model.Die;
import it.polimi.se2018.Model.Game;
import it.polimi.se2018.Model.Player;

public abstract class MVEvent {
    Game game;
    Player player;
    Die die;

    public MVEvent(Game game, Player player, Die die) {
        this.game = game;
        this.player = player;
        this.die = die;
    }

    public MVEvent(Game game, Player player){
        this.game = game;
        this.player = player;
    }

    public MVEvent(Game game) {
        this.game = game;
    }

    public MVEvent(Player player) {
        this.player = player;
    }

    public Game getGame() {

        return game;
    }

    public Player getPlayer() {

        return player;
    }

    public Die getDie() {

        return die;
    }
}
