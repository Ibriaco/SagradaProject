package it.polimi.se2018.Server.Network;

import it.polimi.se2018.Server.Network.Socket.SocketServer;

import java.io.IOException;


public class Server {

    private static SocketServer socketServer;

    public static void main( String[] args ) throws IOException {
        System.out.println("SERVER RUNNING.");

        System.out.println("This is the server.");

        System.out.println("Creating socket server....");

        socketServer = new SocketServer(123);

    }
}
