package it.polimi.se2018.Server.Network.Socket;


public class ServerImplementation implements ServerInterface {

    private final SocketServer server;

    public ServerImplementation( SocketServer server) {
        this.server = server;
    }

    public void send ( String message ) {

        System.out.println("Broadcasting: "+message);

        for( ClientInterface client: server.getClients() ) {
            client.notify(message);

        }
    }
}
