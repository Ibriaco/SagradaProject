package it.polimi.se2018.model;

import it.polimi.se2018.controller.effects.Effect;

import java.util.ArrayList;
import java.util.List;

public class ToolCard extends Card {
    private boolean used = false;
    private List<Effect> effectList;
    private String type;

    public ToolCard(String title, String description, String type) {
        super(title, description);
        this.type = type;
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

    public String getType() {
        return type;
    }

    public List<Effect> getEffectList() {
        return effectList;
    }

}
