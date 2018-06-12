package it.polimi.se2018.Network.Client;

import it.polimi.se2018.Model.Event.MVEvent;
import it.polimi.se2018.Model.InvalidConnectionException;
import it.polimi.se2018.Model.InvalidViewException;
import it.polimi.se2018.MyObservable;
import it.polimi.se2018.MyObserver;
import it.polimi.se2018.Network.Client.ClientInterface;
import it.polimi.se2018.Network.Client.RMI.RMIClient;
import it.polimi.se2018.Network.Client.Socket.SocketClient;
import it.polimi.se2018.View.ViewEvents.VCEvent;

import java.rmi.RemoteException;
import java.util.ArrayList;

import static it.polimi.se2018.View.UI.CLIUtils.consoleScanner;

/**
 * Class that works as a proxy on the Client side
 * It is observed by the View
 * It observes the View
 * @author Gregorio Galletti
 */
public class NetworkHandler implements MyObserver, MyObservable {

    private ClientInterface selectedClient;
    private ArrayList<MyObserver> observerCollection = new ArrayList<>();
    private MVEvent mvEvent;

    public NetworkHandler(int value) throws RemoteException {
        if(value == 1){
            selectedClient = new RMIClient(this);
        }
        else if(value == 2){
            int port = requestPort();
            String ip = requestIP();
            selectedClient = new SocketClient(ip, port);
        }
        System.out.println("CREATOOOO");
    }

    private int requestPort(){
        System.out.println("Select which port you want to use:");
        return consoleScanner.nextInt();
    }

    private String requestIP(){
        System.out.println("Select the IP you want to connect to:");
        return consoleScanner.next();
    }

    public void getMVEvent(MVEvent event) throws InvalidConnectionException, RemoteException, InvalidViewException {
        mvEvent = event;
        notifyObservers();
    }

   /*public void loginScreen() throws RemoteException, InvalidConnectionException, InvalidViewException, WindowCardAssociationException {
        String user;
        printOnConsole("~~~~~~~~~~ Login page ~~~~~~~~~~");
        printOnConsole("Insert your username here: ");
        user = consoleScanner.next();
        selectedClient.loginRequest(user);
    }*/

    @Override
    public void registerObserver(MyObserver observer) throws RemoteException {
        observerCollection.add(observer);
    }

    @Override
    public void unregisterObserver(MyObserver observer) throws RemoteException {
        observerCollection.remove(observer);
    }

    @Override
    public void notifyObservers() throws RemoteException, InvalidConnectionException, InvalidViewException {
        for (MyObserver o: observerCollection) {
            o.update(this, mvEvent);
        }
    }

    @Override
    public void update(MyObservable o, Object event) throws RemoteException, InvalidConnectionException, InvalidViewException {
        selectedClient.sendEvent((VCEvent) event);
    }
}