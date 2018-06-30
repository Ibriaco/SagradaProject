package it.polimi.se2018.network.client;

import it.polimi.se2018.model.InvalidDieException;
import it.polimi.se2018.model.event.MVEvent;
import it.polimi.se2018.model.InvalidConnectionException;
import it.polimi.se2018.model.InvalidViewException;
import it.polimi.se2018.MyObservable;
import it.polimi.se2018.MyObserver;
import it.polimi.se2018.network.client.rmi.RMIClient;
import it.polimi.se2018.network.client.socket.SocketClient;
import it.polimi.se2018.view.viewevents.ChooseCardEvent;
import it.polimi.se2018.view.viewevents.VCEvent;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;

import static it.polimi.se2018.view.ui.CLIUtils.consoleScanner;

/**
 * Class that works as a proxy on the client side
 * It is observed by the view
 * It observes the view
 * @author Gregorio Galletti
 */
public class NetworkHandler implements MyObserver, MyObservable {

    private ClientInterface selectedClient;
    private ArrayList<MyObserver> observerCollection = new ArrayList<>();
    private MVEvent mvEvent;

    public NetworkHandler(String value) throws RemoteException {
        if(value.equals("1")){
            selectedClient = new RMIClient(this);
        }
        else if(value.equals("2")){
            int port = requestPort();
            String ip = requestIP();
            selectedClient = new SocketClient(ip, port, this);
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

    public void receciveMVEvent(MVEvent event) throws InvalidConnectionException, IOException, InvalidViewException, ParseException {
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
    public void notifyObservers() throws IOException, InvalidConnectionException, InvalidViewException, ParseException {
        for (MyObserver o: observerCollection) {
            try {
                o.update(this, mvEvent);
            } catch (InvalidDieException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void update(MyObservable o, VCEvent event) throws IOException, InvalidConnectionException, InvalidViewException, ParseException, InvalidDieException {
        System.out.println("Ho ricevuto evento da view: " + event.getUsername());
        selectedClient.sendEvent(event);


        if(event instanceof ChooseCardEvent){
            ChooseCardEvent c = (ChooseCardEvent) event;
            System.out.println(c.getWindowCard().getWindowName());
        }
    }

    @Override
    public void update(MyObservable o, MVEvent arg) throws RemoteException, InvalidConnectionException, InvalidViewException {
        //da non scrivere perchè in update non riceverò mai gli MV. da implementare su virtualView
    }
}
