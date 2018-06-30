package it.polimi.se2018.model;

import it.polimi.se2018.controller.effects.Effect;

import java.util.ArrayList;
import java.util.List;

public class ToolCard extends Card {
    private boolean used = false;
    private List<Effect> effectList;

    public ToolCard(String title, String description) {
        super(title, description);
        this.used = used;
        effectList = new ArrayList<>();

    }

    public boolean isUsed() {

        return used;
    }
    public void setUsed(boolean used) {
        this.used = used;
    }


    public void applyEffect(Player p, Die d, RoundCell r){

    }


    public List<Effect> getEffectList() {
        return effectList;
    }

}
