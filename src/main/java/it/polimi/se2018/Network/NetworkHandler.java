package it.polimi.se2018.Network;

import it.polimi.se2018.Network.client.ClientInterface;
import it.polimi.se2018.Network.client.rmi.RMIClient;
import it.polimi.se2018.Network.client.socket.SocketClient;

import java.rmi.RemoteException;

import static it.polimi.se2018.View.CLIUtils.consoleScanner;
import static it.polimi.se2018.View.CLIUtils.printOnConsole;

public class NetworkHandler {

    private ClientInterface selectedClient;

    public NetworkHandler(int value){
        if(value == 1){
            try{
                selectedClient = new RMIClient();
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
    }

    private int requestPort(){
        System.out.println("Select which port you want to use:");
        return consoleScanner.nextInt();
    }

    private String requestIP(){
        System.out.println("Select the IP you want to connect to:");
        return consoleScanner.nextLine();
    }

    public void loginScreen() throws RemoteException{
        String user;
        printOnConsole("~~~~~~~~~~ Login page ~~~~~~~~~~");
        printOnConsole("Insert your username here: ");
        user = consoleScanner.next();
        selectedClient.loginRequest(user);
    }
}
