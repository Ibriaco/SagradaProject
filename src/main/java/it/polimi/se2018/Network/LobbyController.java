package it.polimi.se2018.Network;

public class LobbyController {

    private Lobby waitingLobby;

    public LobbyController() {
        waitingLobby = new Lobby();
        System.out.println("Lobby controller creato");
    }

    public boolean checkUser(String user){
        for (String u : waitingLobby.getOnlinePlayers()) {
            //System.out.println("Lista utenti" + u);
            if(user.equals(u))
                return false;
        }
        return true;
    }

    public void addInLobby(String user){
        waitingLobby.addOnlinePlayer(user);
    }
}
