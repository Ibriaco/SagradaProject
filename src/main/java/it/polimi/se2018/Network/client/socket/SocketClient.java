package it.polimi.se2018.Network.client.socket;

import it.polimi.se2018.MyObservable;
import it.polimi.se2018.MyObserver;
import it.polimi.se2018.Network.server.socket.ListeningThread;
import it.polimi.se2018.Network.client.ClientInterface;
import it.polimi.se2018.View.LoginEvent;
import it.polimi.se2018.View.VCEvent;

import java.io.*;
import java.net.Socket;

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

    @Override
    public void registerObserver(MyObserver observer) {

    }

    @Override
    public void unregisterObserver(MyObserver observer) {

    }

    @Override
    public void notifyObservers() {

    }

    @Override
    public void update(MyObservable o, Object arg) {

    }
}
