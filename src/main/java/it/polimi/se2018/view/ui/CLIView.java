package it.polimi.se2018.view.ui;


import it.polimi.se2018.controller.ChangeDieEvent;
import it.polimi.se2018.model.*;
import it.polimi.se2018.model.Color;
import it.polimi.se2018.model.event.*;
import it.polimi.se2018.MyObservable;
import it.polimi.se2018.MyObserver;
import it.polimi.se2018.network.client.NetworkHandler;
import it.polimi.se2018.view.viewevents.*;
import it.polimi.se2018.view.viewevents.PlaceDieEvent;
import it.polimi.se2018.view.viewevents.RollDiceEvent;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.*;
import java.util.List;
import java.util.concurrent.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import static it.polimi.se2018.view.ui.CLIUtils.*;

/**
 * Command line interface
 * @author Ibrahim El Shemy
 * @author Marco Gasperini
 */
public class CLIView implements ViewInterface {

    private NetworkHandler nh;
    private VCEvent vcEvent;
    private String user;
    private ArrayList<MyObserver> observersCollection = new ArrayList<>();
    private List<WindowCard> myCardList;
    private static final Logger LOGGER = Logger.getLogger( CLIView.class.getName() );

    /**
     * Updates a window card
     */
    @Override
    public void updateWindowCard() {

    }

    /**
     * Shows the user interface
     * @throws RemoteException thrown exception
     * @throws InvalidConnectionException thrown exception
     * @throws InvalidViewException thrown exception
     */
    @Override
    public void showUI() throws IOException, InvalidConnectionException, InvalidViewException, ParseException, InvalidDieException {
        String choice;
        boolean validInput = false;
        while(!validInput) {
            choice = printConnectionChoice();
            if (choice.equals("1") || choice.equals("2")) {
                createNH(choice);
                validInput = true;
                loginScreen();
            } else
                consoleErrorWriter.println("Invalid input, please try again!");
        }
    }

    /**
     * Shows the login interface to the user
     * @throws RemoteException thrown exception
     * @throws InvalidConnectionException thrown exception
     * @throws InvalidViewException thrown exception
     */
     public void loginScreen() throws IOException, InvalidConnectionException, InvalidViewException, ParseException, InvalidDieException {
        printOnConsole("~~~~~~~~~~ Login page ~~~~~~~~~~");
        printOnConsole("Insert your username here: ");
        setUsername(consoleScanner.next());
        createLoginEvent();
    }


    @Override
    public void receiveEvent(VCEvent event) {
        /*Intentionally left void, used only in VirtualView*/
    }

    private String printConnectionChoice() throws IOException {
         Scanner scanner = new Scanner(System.in);
        printOnConsole("Select the Connection type you want to use:");
        printOnConsole("1) " + rmi);
        printOnConsole("2) " + socket);
        return scanner.nextLine();
        //return consoleScanner.nextLine();
    }

    @Override
    public void registerObserver(MyObserver observer) {
        observersCollection.add(observer);
    }

    @Override
    public void unregisterObserver(MyObserver observer) {
        observersCollection.remove(observer);
    }

    @Override
    public void notifyObservers() throws IOException, InvalidConnectionException, InvalidViewException, ParseException, InvalidDieException {
        for (MyObserver o : observersCollection) {
            o.update(this, vcEvent);
        }
    }

    @Override
    public void update(MyObservable o, VCEvent arg){
        /*Intentionally left void, used only in VirtualView*/
    }

    @Override
    public void update(MyObservable o, MVEvent arg) throws IOException, InvalidConnectionException, InvalidViewException, ParseException, InvalidDieException {

        arg.accept(this);
    }


    //METODI PER CREARE EVENTI VC
    @Override
    public void createLoginEvent() throws InvalidConnectionException, IOException, InvalidViewException, ParseException, InvalidDieException {
        vcEvent = new LoginEvent(user);
        notifyObservers();
    }

    public void createPlaceDieEvent() throws InvalidConnectionException, IOException, InvalidViewException, ParseException, InvalidDieException {
        consoleWriter.println("Insert the number of Die: ");
        int pos = getNumber()-1;
        consoleWriter.println("In which column do you want to place the Die?");
        int coordX = getNumber()-1;
        consoleWriter.println("In which row do you want to place the Die?");
        int coordY = getNumber()-1;
        vcEvent = new PlaceDieEvent(user, pos, coordX, coordY);
        notifyObservers();
    }

    public void createSkipTurnEvent() throws InvalidConnectionException, IOException, InvalidViewException, ParseException, InvalidDieException {
        vcEvent = new SkipTurnEvent(user);
        notifyObservers();
    }

    public void createUseToolEvent() throws InvalidConnectionException, IOException, InvalidViewException, ParseException, InvalidDieException {
        consoleWriter.println("Insert the number of the Tool card you want to use");
        int pos = getNumber()-1;
        vcEvent = new UseToolEvent(user, pos);
        notifyObservers();
    }

    public void createRollDiceEvent() throws InvalidConnectionException, IOException, InvalidViewException, ParseException, InvalidDieException {
        vcEvent = new RollDiceEvent(user);
        notifyObservers();
    }

    private void createChooseCardEvent(WindowCard windowCard) throws InvalidConnectionException, IOException, InvalidViewException, ParseException, InvalidDieException {
        vcEvent = new ChooseCardEvent(user, windowCard);
        notifyObservers();
    }

    public String getString() {

        return consoleScanner.nextLine();
    }

    public int getNumber() {

        return consoleScanner.nextInt();
    }

    //METODI PER GESTIRE MVEVENT
    @Override
    public void handleMVEvent(LoggedUserEvent event) {

         event.printState();
    }

    @Override
    public void handleMVEvent(DisconnectedEvent event) {

         event.printDisconnection();
    }

    @Override
    public void handleMVEvent (PrivateCardEvent event){
         event.printPrivateName();
    }


    @Override
    public void handleMVEvent(WindowCardEvent event) throws RemoteException, InvalidConnectionException, InvalidViewException {
        Color color;
        int value;
        System.out.println("[WINDOW CARD]\n");
        myCardList = event.getWindowCards();
        boolean ok = true;
        for (WindowCard w: event.getWindowCards()) {
            System.out.println(w.getWindowName() + " " + w.getDifficulty());
            for(int i=0; i<4; i++){
                for(int j=0; j<5; j++){
                    color = w.getGridCell(i, j).getColor();
                    value = w.getGridCell(i, j).getShade();
                    printCell(color, value);
                }
                System.out.println("");
            }
        }
        String fromThread;
        while (true) {
            consoleWriter.println("inserisci carta:");
            Scanner scanner = new Scanner(System.in);

            fromThread = scanner.nextLine();
            consoleWriter.println(fromThread);
            WindowCard selectedW = findInCards(fromThread);
            if (selectedW != null) {
                break;
            }

        }

        try {
            createChooseCardEvent(findInCards(fromThread));
        } catch (InvalidConnectionException | IOException | InvalidViewException | ParseException | InvalidDieException e) {
            LOGGER.log(Level.SEVERE, e.toString(), e);
        }

    }


    @Override
    public void handleMVEvent(UpdateGameEvent event) {
        Color color;
        int value;
        int userN = 0;

        for (WindowCard w: event.getWindowCardList()) {
            System.out.println("\n" + event.getUser().get(userN) + "\t" + w.getWindowName());
            for(int i = 0; i < w.getRows(); i++){
                for(int j = 0; j < w.getCols(); j++){
                    if (w.getGridCell(i,j).isPlaced()) {
                        printDie(w.getGridCell(i, j).getPlacedDie());
                    }
                    else {
                        color = w.getGridCell(i, j).getColor();
                        value = w.getGridCell(i, j).getShade();
                        printCell(color, value);
                    }
                }
                consoleWriter.println("");

            }
            userN++;
            consoleWriter.println("\n");

        }
        int i=1;
        for (Die d: event.getDice()) {
            System.out.print(i + ")  ");
            printDie(d);
            System.out.print("\t");
            i++;
        }
        if (!event.getRoundTrack().isEmpty()) {
            printRoundTrack(event.getRoundTrack());
        }
     }


    @Override
    public void handleMVEvent(IsTurnEvent event) throws InvalidConnectionException, InvalidViewException, ParseException, IOException, InvalidDieException {
        if(user.equals(event.getUser()))
            menuGame();
        else
            event.printPlayerInTurn();

    }

    @Override
    public void handleMVEvent(StopTurnEvent event) {
        event.printMessage();
    }


    //metodo per gestire dado da modificare con tool card
    @Override
    public void handleMVEvent(ChangeDieEvent changeDieEvent) throws InvalidConnectionException, ParseException, InvalidViewException, IOException, InvalidDieException {
        consoleWriter.println("Select the die you want to change: ");
        int pos = getNumber()-1;
        vcEvent = new SelectDieEvent(changeDieEvent.getUsername(), pos);
        notifyObservers();
    }

    @Override
    public void handleMVEvent(ModifiedPlaceEvent modifiedPlaceEvent) throws InvalidConnectionException, ParseException, InvalidViewException, IOException, InvalidDieException {
        consoleWriter.println("In which row do you want to place the die?");
        int x = getNumber()-1;
        consoleWriter.println("In which column do you want to place the die?");
        int y = getNumber()-1;
        vcEvent = new PlaceDieEvent(modifiedPlaceEvent.getUsername(), modifiedPlaceEvent.getPos(), x, y);
        notifyObservers();
    }

    @Override
    public void handleMVEvent(IsNotYourTurn event) {
        event.printMessage();
    }

    @Override
    public void handleMVEvent(ChangedDieEvent changedDieEvent) {
        System.out.println("New value of the die is: ");
        printDie(changedDieEvent.getDie());
        System.out.println("");
    }

    @Override
    public void handleMVEvent(MoveDieEvent moveDieEvent) throws InvalidDieException, InvalidConnectionException, ParseException, InvalidViewException, IOException {

        consoleWriter.println("Insert the column of the die you want to move: ");
        int oldX = getNumber()-1;
        consoleWriter.println("Insert the row of the die you want to move: ");
        int oldY = getNumber()-1;
        consoleWriter.println("Insert the column where you want to move the die: ");
        int newX = getNumber()-1;
        consoleWriter.println("Insert the row where you want to move the die: ");
        int newY = getNumber()-1;
        vcEvent = new MovingDieEvent(moveDieEvent.getUsername(), oldX, oldY, newX, newY);
        notifyObservers();


    }

    @Override
    public void handleMVEvent(WrongPlaceEvent event) throws InvalidDieException, InvalidConnectionException, InvalidViewException, ParseException, IOException {
        LOGGER.log(Level.INFO, "Illegal move! Retry!");
        menuGame();
    }

    @Override
    public void handleMVEvent(IncDecEvent incDecEvent) throws InvalidDieException, InvalidConnectionException, ParseException, InvalidViewException, IOException {
        int choice=0;
        Scanner scanner = new Scanner(System.in);
        while (choice != 1 && choice != 2) {
            printOnConsole("inserici 1 per incrementare oppure 2 per decrementare il valore del dado di 1");
            choice = scanner.nextInt();
        }
        vcEvent = new IncrementDecrementDieEvent(incDecEvent.getUsername(),choice);
        notifyObservers();
    }


    private void printDie(Die d){
         Color color = d.getColor();
         int value = d.getValue();
         String number = "";
         switch(value){
            case 1:
                number = "\u2680";
                break;
            case 2:
                number = "\u2681";
                break;
            case 3:
                number = "\u2682";
                break;
            case 4:
                number = "\u2683";
                break;
            case 5:
                number = "\u2684";
                break;
            case 6:
                number = "\u2685";
                break;
            default:
                break;
         }
        switch (color) {
            case BLUE:
                System.out.print(ANSI_BLUE + number + ANSI_RESET);
                break;
            case RED:
                System.out.print(ANSI_RED + number + ANSI_RESET);
                break;
            case GREEN:
                System.out.print(ANSI_GREEN + number + ANSI_RESET);
                break;
            case YELLOW:
                System.out.print(ANSI_YELLOW + number + ANSI_RESET);
                break;
            case PURPLE:
                System.out.print(ANSI_PURPLE + number + ANSI_RESET);
                break;
            default:
                break;
        }
    }


    private void printCell(Color color, int value){
        String toPrint;

        if (value == 0) {
            toPrint = "\u25FC";
            if(color == null) {
                consoleWriter.print(toPrint);
            }
            else{
                switch (color) {
                    case BLUE:
                        System.out.print(ANSI_BLUE + toPrint + ANSI_RESET);
                        break;
                    case RED:
                        System.out.print(ANSI_RED + toPrint + ANSI_RESET);
                        break;
                    case GREEN:
                        System.out.print(ANSI_GREEN + toPrint + ANSI_RESET);
                        break;
                    case YELLOW:
                        System.out.print(ANSI_YELLOW + toPrint + ANSI_RESET);
                        break;
                    case PURPLE:
                        System.out.print(ANSI_PURPLE + toPrint + ANSI_RESET);
                        break;
                    case WHITE:
                        System.out.print(ANSI_WHITE + toPrint + ANSI_RESET);
                        break;
                    default:
                        break;
                }
            }
        } else {

            switch(value){
                case 1:
                    System.out.print("\u2680");
                    break;
                case 2:
                    System.out.print("\u2681");
                    break;
                case 3:
                    System.out.print("\u2682");
                    break;
                case 4:
                    System.out.print("\u2683");
                    break;
                case 5:
                    System.out.print("\u2684");
                    break;
                case 6:
                    System.out.print("\u2685");
                    break;
                default:
                    break;
            }
        }
    }


    @Override
    public void handleMVEvent(PublicCardEvent event) {
        event.printPublicName();
    }

    @Override
    public void handleMVEvent(ToolCardEvent event) {
        event.printToolCards();
    }

    @Override
    public void createNH(String choice) throws RemoteException {
        nh = new NetworkHandler(choice);
        registerObserver(nh);
        nh.registerObserver(this);
    }

    @Override
    public void setUsername(String u) {
        user = u;
    }

    private WindowCard findInCards(String n) {
        return myCardList.stream().filter(w -> w.getWindowName().equalsIgnoreCase(n)).findFirst().orElse(null);
    }

    private String testL() throws InterruptedException, ExecutionException
    {
        ExecutorService executor = Executors.newCachedThreadPool();
        Callable<String> callable = () -> {
            consoleWriter.println("inserisci");
            try (Scanner scanner = new Scanner(System.in)) {
                return scanner.nextLine();
            }
        };
        Future<String> future = executor.submit(callable);
        executor.shutdown();
        return future.get();
    }

    private void printRoundTrack(List<RoundCell> roundTrack) {
        int counter = 1;
        System.out.println("\n----------ROUND TRACK----------");
        for (RoundCell roundCell : roundTrack) {
            System.out.print(counter + ")  ");
            for (Die die : roundCell.getDiceList()) {
                printDie(die);
                System.out.print("\t");
            }
            counter++;
            System.out.println("");
        }
    }

    private void menuGame() throws InvalidConnectionException, ParseException, InvalidViewException, IOException, InvalidDieException {
         boolean turn = true;
         Scanner scanner = new Scanner(System.in);
         String choose;
         System.out.println("");
         while (turn) {
             System.out.println("CHOOSE YOUR MOVE\n1- Place Die\n2- Use Toolcard\n3- Skip turn");
             choose = scanner.nextLine();
             if (choose.equals("1") || choose.equals("2") || choose.equals("3"))
                 switch (choose) {
                     case "1":
                         createPlaceDieEvent();
                         createSkipTurnEvent();
                         turn = false;
                         break;
                     case "2":
                         createUseToolEvent();
                         createSkipTurnEvent();
                         turn = false;
                         break;
                     case "3":
                         createSkipTurnEvent();
                         turn = false;
                         break;
                     default:
                         break;
                 }
             else
                 LOGGER.log(Level.INFO, "Invalid choice, try again!");
         }
     }

}