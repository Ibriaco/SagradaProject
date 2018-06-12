package it.polimi.se2018.Controller;

import it.polimi.se2018.Model.*;
import it.polimi.se2018.Model.Event.GameUpdateEvent;
import it.polimi.se2018.Model.Event.MVEvent;
import it.polimi.se2018.Model.Event.PlaceDieEvent;
import it.polimi.se2018.MyObservable;
import it.polimi.se2018.MyObserver;
import it.polimi.se2018.Network.LobbyController;
import it.polimi.se2018.View.ViewEvents.*;

import java.rmi.RemoteException;
import java.util.ArrayList;

public class EventsController extends Controller implements MyObserver, MyObservable {

    private Game game;
    private boolean control1;
    private boolean control2;

    private LobbyController lobbyController;
    private ArrayList<MyObserver> observerCollection = new ArrayList<>();
    private MVEvent mvEvent;

    public EventsController() throws RemoteException{
        lobbyController = new LobbyController(this);
    }

    private boolean checkPlayer(String username) {

        return (game.getPlayers().indexOf(game.findPlayer(username)) == game.getTurn());

    }

    private boolean checkValidPlacementMove(PlaceDieEvent e) {
        control1 = this.checkPlayer(e.getUsername());
        Die die = null;
        for (Die d : game.getRolledDice()) {
            if (d.getValue() == e.getValue() && d.getColor() == e.getColor()) {
                die = d;
                control2 = true;
                break;
            }
        }
        if (!game.findPlayer(e.getUsername()).getWindowCard().checkLegalPlacement(die, e.getCoordX(), e.getCoordY(), true, true))
            return false;
        else
            return (control1 && control2);
    }

    private boolean checkValidSkip(SkipTurnEvent e) {

        return this.checkPlayer(e.getUsername());
    }

    private boolean checkLoginEvent(LoginEvent e) {
        control1 = this.checkPlayer(e.getUsername());
        if (game.getPlayerNumber() < 4) {
            control2 = true;
        } else
            return false;
        for (Player p : game.getPlayers()) {
            if (p.getUsername().equals(e.getUsername())) {
                return false;
            }
        }
        return (control1 && control2);
    }

    private boolean checkChooseCardEvent(ChooseCardEvent e) {
        control1 = this.checkPlayer(e.getUsername());
        Player p = game.findPlayer(e.getUsername());

        if (p.getWindowCardAssociations()[0].getFront().getWindowName().equals(e.getWindowName()) ||
                p.getWindowCardAssociations()[0].getBack().getWindowName().equals(e.getWindowName()) ||
                p.getWindowCardAssociations()[1].getFront().getWindowName().equals(e.getWindowName()) ||
                p.getWindowCardAssociations()[1].getBack().getWindowName().equals(e.getWindowName())) {
            control2 = true;
        } else
            return false;

        return control1;
    }

    public void checkSelectDieEvent (SelectDieEvent e){

    }

    public void setMvEvent(MVEvent mvEvent) {

        this.mvEvent = mvEvent;
    }

    public LobbyController getLobbyController(){

        return lobbyController;
    }




    //METODI OBSERVER E OBSERVABLE

    @Override
    public void registerObserver(MyObserver observer) {
        observerCollection.add(observer);
    }

    @Override
    public void unregisterObserver(MyObserver observer) {
        observerCollection.remove(observer);
    }

    @Override
    public void notifyObservers() throws RemoteException, InvalidConnectionException, InvalidViewException {
        for (MyObserver o: observerCollection) {
            o.update(this, mvEvent);
        }
    }

    @Override
    public void update(MyObservable o, Object arg) throws RemoteException, InvalidConnectionException, InvalidViewException {

        if(arg.toString().equals("Login Event")){
            lobbyController.handleLogin((VCEvent) arg);
        }
    }

    //METODI PER GENERARE EVENTI MV

    public void createGameUpdateEvent () throws InvalidConnectionException, RemoteException, InvalidViewException {
        mvEvent = new GameUpdateEvent("ALL");
        notifyObservers();
    }
}
