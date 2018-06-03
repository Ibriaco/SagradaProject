package it.polimi.se2018.Network.Socket;

import it.polimi.se2018.Network.ClientInterface;

import java.io.*;
import java.net.Socket;

public class SocketClient implements ClientInterface{
    private String username;
    private static Socket socket;

    public SocketClient(String serverIP, Integer port, String username) {
        this.username = username;
        try {
            socket = new Socket(serverIP, port);
        } catch (IOException e) {
        }

        loginRequest();
    }


    @Override
    public void loginRequest() {
    }
}
