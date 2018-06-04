package it.polimi.se2018.Network.Socket;

import it.polimi.se2018.Network.ClientInterface;
import it.polimi.se2018.View.LoginEvent;
import it.polimi.se2018.View.VCEvent;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class SocketClient implements ClientInterface{
    private String username;
    private static Socket socket;
    private ListeningThread listeningThread;

    public SocketClient(String serverIP, Integer port, String username) {
        this.username = username;
        try {
            socket = new Socket(serverIP, port);
        } catch (IOException e) {
        }

        loginRequest();

        listeningThread = new ListeningThread(socket);
        listeningThread.start();
    }


    @Override
    public void loginRequest() {
        VCEvent loginE = new LoginEvent("Socket", username);
        ObjectOutputStream toServer;
        try {
            toServer = new ObjectOutputStream(socket.getOutputStream());
            toServer.writeObject(loginE);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
