package it.polimi.se2018.Model.Event;

import it.polimi.se2018.View.UI.ViewInterface;

import java.io.Serializable;

public interface MVEvent extends Serializable{

    void accept(ViewInterface vi);
    String getUsername();
}
