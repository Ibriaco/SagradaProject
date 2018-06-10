package it.polimi.se2018.Network;

import it.polimi.se2018.Model.InvalidConnectionException;
import it.polimi.se2018.Model.InvalidViewException;
import it.polimi.se2018.MyObservable;
import it.polimi.se2018.MyObserver;
import it.polimi.se2018.Network.client.ClientInterface;
import it.polimi.se2018.Network.client.rmi.RMIClient;
import it.polimi.se2018.Network.client.socket.SocketClient;

import java.rmi.RemoteException;
import java.util.ArrayList;

import static it.polimi.se2018.View.UI.CLIUtils.consoleScanner;
import static it.polimi.se2018.View.UI.CLIUtils.printOnConsole;

public class NetworkHandler implements MyObserver, MyObservable {

    private ClientInterface selectedClient;
    private ArrayList<MyObserver> observerCollection = new ArrayList<>();

    public NetworkHandler(int value){
        if(value == 1){
            try{
                selectedClient = new RMIClient();
                selectedClient.registerObserver(this);
            }
            catch(RemoteException e){
                e.printStackTrace();
            }
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

    public void loginScreen() throws RemoteException, InvalidConnectionException, InvalidViewException {
        String user;
        printOnConsole("~~~~~~~~~~ Login page ~~~~~~~~~~");
        printOnConsole("Insert your username here: ");
        user = consoleScanner.next();
        selectedClient.loginRequest(user);
    }

    @Override
    public void registerObserver(MyObserver observer) throws RemoteException {
        observerCollection.add(observer);
    }

    @Override
    public void unregisterObserver(MyObserver observer) throws RemoteException {
        observerCollection.remove(observer);
    }

    @Override
    public void notifyObservers() throws RemoteException {
        for (MyObserver o: observerCollection) {
            update(this, "");
        }
    }

    @Override
    public void update(MyObservable o, Object arg) throws RemoteException {
        System.out.println(arg.toString());
    }
}
