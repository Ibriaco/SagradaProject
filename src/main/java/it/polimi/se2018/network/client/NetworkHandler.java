package it.polimi.se2018.network.client;

import it.polimi.se2018.MyObservable;
import it.polimi.se2018.MyObserver;
import it.polimi.se2018.model.InvalidConnectionException;
import it.polimi.se2018.model.InvalidDieException;
import it.polimi.se2018.model.InvalidViewException;
import it.polimi.se2018.model.event.MVEvent;
import it.polimi.se2018.network.client.rmi.RMIClient;
import it.polimi.se2018.network.client.socket.SocketClient;
import it.polimi.se2018.org.json.simple.parser.ParseException;
import it.polimi.se2018.view.viewevents.VCEvent;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import static it.polimi.se2018.ClientConfig.SOCKET_CLIENT_ADDRESS;

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
    private static final Logger LOGGER = Logger.getGlobal();

    public NetworkHandler(String value) throws RemoteException {
        if(value.equals("1")) {
            selectedClient = new RMIClient(this);

        }

        else if(value.equals("2"))
            selectedClient = new SocketClient(SOCKET_CLIENT_ADDRESS, 10000, this);
    }


    public void receiveMVEvent(MVEvent event) throws InvalidConnectionException, IOException, InvalidViewException, ParseException {
        mvEvent = event;
        notifyObservers();
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
    public void notifyObservers() throws IOException, InvalidConnectionException, InvalidViewException, ParseException {
        for (MyObserver o: observerCollection) {
            try {
                o.update(this, mvEvent);
            } catch (InvalidDieException e) {
                LOGGER.log(Level.SEVERE, e.toString(), e);
            }
        }
    }

    @Override
    public void update(MyObservable o, VCEvent event) throws IOException, InvalidConnectionException, InvalidViewException, ParseException, InvalidDieException {
        selectedClient.sendEvent(event);
    }

    @Override
    public void update(MyObservable o, MVEvent arg) throws RemoteException, InvalidConnectionException, InvalidViewException {
        /*Intentionally left empty*/
    }
}
