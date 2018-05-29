package it.polimi.se2018.Server.Network;

import it.polimi.se2018.Client.ClientInterface;

import java.net.Socket;
import java.util.ArrayList;

public class Server {

    private static int PORT = 1111;
    private static ServerInterface server;

    private ArrayList<ClientInterface> clients = new ArrayList<>();

    public Server() {

       // servizio offerto ai client
       this.server = new ServerImplementation(this);

       // Avvio il ClientGatherer, un nuovo thread che si occupa di gestire la connessione di nuovi client
       (new ClientGatherer(this, PORT)).start();

    }

    /**
     * Aggiungi un nuovo client alla lista
     * @param clientConnection
     */
    protected synchronized void addClient( Socket clientConnection ) {

        // Creo un VirtualClient per ogni nuovo client,
        // per ogni client un thread che ascolta i messaggi provenienti da quel client
        VirtualClient cm = new VirtualClient(this, clientConnection);
        clients.add(cm);
        cm.start();

    }

    /**
     * Ritorna tutti i client
     * @return
     */
    protected synchronized ArrayList<ClientInterface> getClients() {
        return this.clients;
    }

    /**
     * Rimuovi un client disconnesso
     * @param client
     */
    protected synchronized void removeClient(ClientInterface client) {
        this.clients.remove(client);
    }

    protected synchronized ServerInterface getImplementation() {
        return this.server;
    }

    public static void main(String[] args) {
            new Server();
    }
}
