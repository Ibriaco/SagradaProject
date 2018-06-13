package it.polimi.se2018.Controller;

import it.polimi.se2018.Model.*;
import it.polimi.se2018.Model.Event.GameUpdateEvent;
import it.polimi.se2018.Model.Event.MVEvent;
import it.polimi.se2018.MyObservable;
import it.polimi.se2018.MyObserver;
import it.polimi.se2018.Network.Server.VirtualView;
import it.polimi.se2018.View.ViewEvents.*;

import java.rmi.RemoteException;
import java.util.ArrayList;

public class EventsController implements ControllerInterface, MyObserver, MyObservable {

    private Game game;
    private boolean control1;
    private boolean control2;

    private LobbyController lobbyController;
    private ArrayList<MyObserver> observerCollection = new ArrayList<>();
    private MVEvent mvEvent;

    public EventsController(VirtualView virtualView) throws RemoteException{
        lobbyController = new LobbyController(this, virtualView);
    }

    private boolean checkPlayer(String username) {

        return (game.getPlayers().indexOf(game.findPlayer(username)) == game.getTurn());

    }

    /*private boolean checkValidPlacementMove(PlacedDieEvent e) {
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
    }*/

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
    public void update(MyObservable o, VCEvent arg) throws RemoteException, InvalidConnectionException, InvalidViewException, WindowCardAssociationException {

        arg.accept(this);
    }

    @Override
    public void update(MyObservable o, MVEvent arg) throws RemoteException, InvalidConnectionException, InvalidViewException {

    }

    //METODI PER GENERARE EVENTI MV

    public void createGameUpdateEvent () throws InvalidConnectionException, RemoteException, InvalidViewException {
        mvEvent = new GameUpdateEvent("ALL");
        notifyObservers();
    }

    @Override
    public void handleVCEvent(LoginEvent event) throws InvalidConnectionException, RemoteException, InvalidViewException, WindowCardAssociationException {
        lobbyController.handleLogin(event);
    }

    @Override
    public void handleVCEvent(PlaceDieEvent event) {

    }

    @Override
    public void handleVCEvent(RollDiceEvent event) {

    }

    @Override
    public void handleVCEvent(SkipTurnEvent event) {

    }

    @Override
    public void handleVCEvent(SelectDieEvent event) {

    }

    @Override
    public void handleVCEvent(UseToolEvent event) {

    }

    @Override
    public void handleVCEvent(ChooseCardEvent event) {

    }
}
