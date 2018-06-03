package it.polimi.se2018;

import java.io.Serializable;

public class Message implements Serializable {

    static final long serialVersionUID = 42L;
    
    private String message;

    public Message(String message) {
        this.message = message+"\n";
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message){
        this.message = message;
    }
}
