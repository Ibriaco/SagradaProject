package it.polimi.se2018.Network.RMI;

import it.polimi.se2018.View.LoginEvent;
import it.polimi.se2018.View.VCEvent;
import it.polimi.se2018.Message;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class RMIClient implements RMIClientInterface {

    private String username;
    private RMIClientInterface remoteRef;
    private RMIServerInterface server;

    public void notify(Message message) throws RemoteException {
        System.out.println(message.getMessage());
    }

    public RMIClient(String username) throws RemoteException{
    this.username = username;
        try{
        server = (RMIServerInterface) Naming.lookup("//localhost/MyServer");
        remoteRef = (RMIClientInterface) UnicastRemoteObject.exportObject(this, 0);

    } catch (MalformedURLException e) {
        System.err.println("URL non trovato!");
    } catch (RemoteException e) {
        System.err.println("Errore di connessione: " + e.getMessage() + "!");
    } catch (NotBoundException e) {
        System.err.println("Il riferimento passato non è associato a nulla!");
    }
    loginRequest();
}


    public void loginRequest() throws RemoteException{
        VCEvent loginE = new LoginEvent("RMI", username);
        server.addClient(remoteRef);
        server.loginUser(loginE);

    }

    public String getUsername(){
        return username;
    }


}
