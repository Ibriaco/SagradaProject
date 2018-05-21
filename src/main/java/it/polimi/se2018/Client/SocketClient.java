package it.polimi.se2018.Client;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class SocketClient{
    private String ip;
    private int port;

    public SocketClient(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }

    public void connect() throws IOException {

        Socket socket = null;
        try (OutputStreamWriter writer = new OutputStreamWriter(new Socket(ip, port).getOutputStream())){
            writer.write("csjkd");
        }

        System.out.println("Connected to the server.");

    }
}
