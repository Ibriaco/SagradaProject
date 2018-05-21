package it.polimi.se2018.Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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

        String command = null;
        Socket socket = null;

        try (OutputStreamWriter writer = new OutputStreamWriter(new Socket(ip, port).getOutputStream())){

            System.out.println("Connected to the server.");
            System.out.println("Insert your username to login.");
            BufferedReader b = new BufferedReader(new InputStreamReader(System.in));

            command = b.readLine();

            writer.write(command);
        }


    }
}
