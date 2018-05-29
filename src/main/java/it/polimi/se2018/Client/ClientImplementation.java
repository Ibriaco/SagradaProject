package it.polimi.se2018.Client;


import it.polimi.se2018.Message;

public class ClientImplementation implements ClientInterface{

    public void notify ( Message message ) {

        System.out.println("Received: " + message.getMessage());

    }

}
