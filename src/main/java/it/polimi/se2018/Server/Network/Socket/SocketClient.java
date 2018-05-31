package it.polimi.se2018.Server.Network.Socket;

import java.io.*;
import java.net.Socket;

public class SocketClient {

    private static Socket socket;

    public SocketClient(String serverIP, Integer port) {
        try {
            socket = new Socket(serverIP, port);


        } catch (IOException e) {
        }
    }
}
