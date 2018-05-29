package it.polimi.se2018.Server.Network;


import it.polimi.se2018.Client.ClientInterface;
import it.polimi.se2018.Message;

public class ServerImplementation implements ServerInterface {

    private final Server server;

    public ServerImplementation( Server server) {
        this.server = server;
    }

    public void send ( Message message ) {

        System.out.println("Broadcasting: "+message.getMessage());

        for( ClientInterface client: server.getClients() ) {
            client.notify(message);

        }
    }
}
