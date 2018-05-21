package it.polimi.se2018.Client;

import java.io.IOException;
import java.net.Socket;

public class SocketClient {
    private String ip;
    private int port;
    private Socket socket;

    public SocketClient(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }

    public void connect() throws IOException {
        socket = new Socket(ip, port);
        System.out.println("Connected to the server.");
    }
}
