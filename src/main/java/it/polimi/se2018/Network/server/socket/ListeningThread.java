package it.polimi.se2018.Network.server.socket;

import it.polimi.se2018.Message;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

public class ListeningThread extends Thread{

    private Socket socket;

    public ListeningThread(Socket socket){
        this.socket = socket;
    }

    @Override
    public void run() {
        boolean loop = true;

        ObjectInputStream fromServer = null;
        try {
            fromServer = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

        while(loop) {
            try {
                Message receivedE = (Message) fromServer.readObject();
                if (receivedE == null)
                    loop = false;
                else
                    System.out.println(receivedE.getMessage());
            }catch (EOFException e){

            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
