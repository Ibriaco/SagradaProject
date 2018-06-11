package it.polimi.se2018.Network;

import it.polimi.se2018.Controller.EventsController;
import it.polimi.se2018.Message;
import it.polimi.se2018.Model.Game;
import it.polimi.se2018.MyObserver;
import it.polimi.se2018.Network.server.VirtualView;
import it.polimi.se2018.View.ViewEvents.VCEvent;

import java.rmi.RemoteException;
import java.util.ArrayList;
import static it.polimi.se2018.View.UI.CLIUtils.printOnConsole;

/**
 * Class that controls everything concerned with the game Lobby
 * @author Ibrahim El Shemy
 * @author Gregorio Galletti
 * @author Marco Gasperini
 */
public class LobbyController {

    private Lobby waitingLobby;
    private Game game;
    private static int timer = 10;
    private EventsController eventsController;
    private VirtualView virtualView;
    private ArrayList<MyObserver> observerCollection = new ArrayList<>();

    public LobbyController(VirtualView vv) throws RemoteException {
        virtualView = vv;
        waitingLobby = new Lobby(virtualView);
        printOnConsole("Lobby controller creato");
    }

    /**
     * Handles the login event that is notified by the VirtualView
     * @param event notified by the Virtual View
     */
    public void handleLogin(VCEvent event) throws RemoteException {
        String username = event.getUsername();
        if(checkUser(username)&&checkOnlinePlayers()&&checkTime()) {
            addInLobby(username);
            String playersNumber = String.valueOf(getLobby().getOnlinePlayers().size());
            printOnConsole("User " + username + " logged in successfully!");
            printOnConsole("Online players " + playersNumber);
            getLobby().getVirtualView().getClients().get(username).notify("User " + username + " logged in!");
        }
        else if(!checkTime())
            getLobby().getVirtualView().getClients().get(username).notify("Timer expired!");
        else if(!checkOnlinePlayers())
            getLobby().getVirtualView().getClients().get(username).notify("Lobby is already full! Please join another lobby!");
        else if(!checkUser(username)) {
            getLobby().getVirtualView().getClients().get(username).notify("Invalid username! Please try again!");
        }
    }

    /**
     * Method that checks that there are no occurrences of the same username
     * @param user of the player
     * @return true if there are no occurrences, false instead
     */
    public boolean checkUser(String user) {
        for (String u : waitingLobby.getOnlinePlayers()) {
            if (user.equals(u))
                return false;
        }
        return true;
    }

    /**
     * Method that checks that the limit of player in lobby must be 4
     * @return true if the numbers of player is lower than 4, false instead
     */
    public boolean checkOnlinePlayers() {
        if (waitingLobby.getOnlinePlayersN() == 4)
            return false;
        else
            return true;
    }

    /**
     * Method that checks if the timer's expired
     * @return true if timer's expired, false instead
     */
    public boolean checkTime() {
        if (waitingLobby.getOnlinePlayersN() >= 2 && getTimer() == 0)
            return false;
        else
            return true;
    }

    /**
     * Adds a player in the lobby
     * @param user of the player
     */
    public synchronized void addInLobby(String user) {

        waitingLobby.addOnlinePlayer(user);

    }

    /**
     * Creates a thread and decrements an integer value every second to simulate a timer
     * @author Gregorio Galletti
     */
    public void launchTimer() {
        new Thread(() -> {
            Message timeout = new Message("");
            boolean startGame = false;
            while (!startGame) {
                    while (timer != 0) {
                        timeout.setMessage(String.valueOf(timer));
                        //socketServer.send(timeout);

                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                            Thread.currentThread().interrupt();
                        }
                        timer--;
                        if(getTimer()==0)
                            startGame = true;
                    }
            }
        }).start();
    }

    /*public void setupGame() throws InvalidConnectionException, InvalidViewException, RemoteException {
        game = new Game(virtualView.getClients().size());
        for (String s: virtualView.getClients().keySet()) {
            game.getPlayers().add(new Player(s, "CLI"));

        }
        List<Integer> list = new ArrayList<>();
        IntStream.range(1,5).forEach(list::add);
        Collections.shuffle(list);
        for(int i = 0; i<game.getPlayers().size(); i++) {
            game.getPlayers().get(i).drawCard(list.get(i));
        }
        int j=0;
        for (String username: virtualView.getClients().keySet()){
            virtualView.getClients().get(username).notify(game.getPlayers().get(j).getPrivateObjective().toString());
            j++;
            }
    }*/

    public int getTimer() {
        return timer;
    }

    public void setTimer(int timer) {
        LobbyController.timer = timer;
    }

    public Lobby getLobby() {
        return waitingLobby;
    }

}
