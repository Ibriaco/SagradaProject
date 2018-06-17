package it.polimi.se2018.Network.Server.Socket;

import it.polimi.se2018.Model.Event.MVEvent;
import it.polimi.se2018.Model.InvalidConnectionException;
import it.polimi.se2018.Model.InvalidViewException;
import it.polimi.se2018.Model.WindowCardAssociationException;
import it.polimi.se2018.MyObservable;
import it.polimi.se2018.MyObserver;
import it.polimi.se2018.Network.Client.ClientInterface;
import it.polimi.se2018.Network.Server.VirtualView;
import it.polimi.se2018.View.ViewEvents.VCEvent;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.rmi.RemoteException;

/**
 * @author Gregorio Galletti
 */
public class SocketConnection extends Thread implements ClientInterface{
    private SocketServer socketServer;
    private Socket connectionSocket;
    private VCEvent receivedEvent;
    private VirtualView virtualView;

    public SocketConnection(Socket socket, SocketServer socketServer, VirtualView v){
        connectionSocket = socket;
        this.socketServer = socketServer;
        virtualView = v;
    }

    @Override
    public void run(){
        System.out.println("Client connesso!");
        ObjectInputStream fromClient = null;
        try {
            fromClient = new ObjectInputStream(connectionSocket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        boolean loop = true;
        while(loop)
        try {
            receivedEvent = (VCEvent) fromClient.readObject();
            virtualView.addClientToMap(receivedEvent.getUsername(),this);
            System.out.println("Arrivato un evento da " + receivedEvent.getUsername() + ", lo giro alla virtual view.");
            virtualView.receiveEvent(receivedEvent);
            virtualView.addClientToMap(receivedEvent.getUsername(), this);

        } catch (IOException | ClassNotFoundException | InvalidConnectionException | InvalidViewException | NullPointerException | ParseException | WindowCardAssociationException e) {
            e.printStackTrace();
        }
    }

    public Socket getConnectionSocket() {
        return connectionSocket;
    }



    //metodi implementati solo per correttezza

    @Override
    public void sendMVEvent(MVEvent event) throws RemoteException, InvalidConnectionException, InvalidViewException {
        try {
            ObjectOutputStream toClient = new ObjectOutputStream(this.getConnectionSocket().getOutputStream());
            toClient.writeObject(event);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sendEvent(VCEvent event) throws RemoteException, InvalidConnectionException, InvalidViewException {

    }

    @Override
    public void registerObserver(MyObserver observer) throws RemoteException {

    }

    @Override
    public void unregisterObserver(MyObserver observer) throws RemoteException {

    }

    @Override
    public void notifyObservers() throws RemoteException, InvalidConnectionException, InvalidViewException {

    }


    @Override
    public void update(MyObservable o, VCEvent arg) throws RemoteException, InvalidConnectionException, InvalidViewException, WindowCardAssociationException {

    }

    @Override
    public void update(MyObservable o, MVEvent arg) throws RemoteException, InvalidConnectionException, InvalidViewException {

    }
}
