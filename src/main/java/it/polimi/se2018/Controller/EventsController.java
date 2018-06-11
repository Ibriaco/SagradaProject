package it.polimi.se2018.Controller;

import it.polimi.se2018.MyObservable;
import it.polimi.se2018.MyObserver;
import it.polimi.se2018.Network.server.rmi.RMIServer;
import it.polimi.se2018.Network.server.socket.SocketServer;
import it.polimi.se2018.Model.Die;
import it.polimi.se2018.Model.Event.PlaceDieEvent;
import it.polimi.se2018.Model.Game;
import it.polimi.se2018.Model.Player;
import it.polimi.se2018.View.ViewEvents.ChooseCardEvent;
import it.polimi.se2018.View.ViewEvents.LoginEvent;
import it.polimi.se2018.View.ViewEvents.SelectDieEvent;
import it.polimi.se2018.View.ViewEvents.SkipTurnEvent;

import java.rmi.RemoteException;

public class EventsController extends Controller implements MyObserver, MyObservable {

    private Game game;
    private boolean control1;
    private boolean control2;
    private RMIServer rmiServer;
    private SocketServer socketServer;

    public EventsController(RMIServer rmiS, SocketServer socketS) {
        super();
        this.rmiServer = rmiS;
        this.socketServer = socketS;
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

        return (control1 && control2);
    }

    public void checkSelectDieEvent (SelectDieEvent e){

    }

    @Override
    public void registerObserver(MyObserver observer) throws RemoteException {

    }

    @Override
    public void unregisterObserver(MyObserver observer) throws RemoteException {

    }

    @Override
    public void notifyObservers() throws RemoteException {

    }

    @Override
    public void update(MyObservable o, Object arg) throws RemoteException {

    }
}
