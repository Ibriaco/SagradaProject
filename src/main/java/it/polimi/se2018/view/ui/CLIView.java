package it.polimi.se2018.view.ui;


import it.polimi.se2018.model.*;
import it.polimi.se2018.model.Color;
import it.polimi.se2018.model.event.*;
import it.polimi.se2018.MyObservable;
import it.polimi.se2018.MyObserver;
import it.polimi.se2018.network.client.NetworkHandler;
import it.polimi.se2018.view.viewevents.*;
import it.polimi.se2018.view.viewevents.RollDiceEvent;
import org.json.simple.parser.ParseException;
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
    public void showUI() throws IOException, InvalidConnectionException, InvalidViewException, ParseException {
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
     public void loginScreen() throws IOException, InvalidConnectionException, InvalidViewException, ParseException {
        printOnConsole("~~~~~~~~~~ Login page ~~~~~~~~~~");
        printOnConsole("Insert your username here: ");
        setUsername(consoleScanner.next());
        createLoginEvent();
    }


    @Override
    public void receiveEvent(VCEvent event) {
        /*Intentionally left void, used only in VirtualView*/
    }

    private String printConnectionChoice() {
        printOnConsole("Select the Connection type you want to use:");
        printOnConsole("1) " + rmi);
        printOnConsole("2) " + socket);
        return consoleScanner.nextLine();
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
    public void notifyObservers() throws IOException, InvalidConnectionException, InvalidViewException, ParseException {
        for (MyObserver o : observersCollection) {
            o.update(this, vcEvent);
        }
    }

    @Override
    public void update(MyObservable o, VCEvent arg){
        /*Intentionally left void, used only in VirtualView*/
    }

    @Override
    public void update(MyObservable o, MVEvent arg) throws IOException, InvalidConnectionException, InvalidViewException, ParseException {

        arg.accept(this);
    }


    //METODI PER CREARE EVENTI VC
    @Override
    public void createLoginEvent() throws InvalidConnectionException, IOException, InvalidViewException, ParseException {
        vcEvent = new LoginEvent(user);
        notifyObservers();
    }

    public void createPlaceDieEvent() throws InvalidConnectionException, IOException, InvalidViewException, ParseException {
        consoleWriter.println("insert the number of Die");
        int pos = getNumber()-1;
        consoleWriter.println("insert the number of column in which you want place the Die");
        int coordX = getNumber()-1;
        consoleWriter.println("insert the number of row in which you want place the Die");
        int coordY = getNumber()-1;
        vcEvent = new PlaceDieEvent(user, pos, coordX, coordY);
        notifyObservers();
    }

    public void createSkipTurnEvent() throws InvalidConnectionException, IOException, InvalidViewException, ParseException {
        vcEvent = new SkipTurnEvent(user);
        notifyObservers();
    }

    public void createUseToolEvent() throws InvalidConnectionException, IOException, InvalidViewException, ParseException {
        int pos = getNumber()-1;
        vcEvent = new UseToolEvent(user, pos);
        notifyObservers();
    }

    public void createRollDiceEvent() throws InvalidConnectionException, IOException, InvalidViewException, ParseException {
        vcEvent = new RollDiceEvent(user);
        notifyObservers();
    }

    private void createChooseCardEvent(WindowCard windowCard) throws InvalidConnectionException, IOException, InvalidViewException, ParseException {
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
    public void handleMVEvent (PrivateCardEvent event){
         event.printPrivateName();
    }


    @Override
    public void handleMVEvent(WindowCardEvent event) throws RemoteException, InvalidConnectionException, InvalidViewException {
        Color color;
        int value;
        consoleWriter.println("[WINDOW CARD]\n");
        myCardList = event.getWindowCards();

        for (WindowCard w: event.getWindowCards()) {
            consoleWriter.println(w.getWindowName() + " " + w.getDifficulty());
            for(int i=0; i<4; i++){
                for(int j=0; j<5; j++){
                    color = w.getGridCell(i, j).getColor();
                    value = w.getGridCell(i, j).getShade();
                    printCell(color, value);
                }
                consoleWriter.println("");
            }
        }
        launchThread();
    }


    @Override
    public void handleMVEvent(UpdateGameEvent event) {
        Color color;
        int value;
        int userN = 0;

        for (WindowCard w: event.getWindowCardList()) {
            consoleWriter.println(event.getUser().get(userN) + "\t" + w.getWindowName());
            for(int i = 0; i < w.getRows(); i++){
                for(int j = 0; j < w.getCols(); j++){
                    if (w.getGridCell(i,j).isPlaced()) {
                        //System.out.println("sono in is placed");
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
    }


    @Override
    public void handleMVEvent(IsTurnEvent event) throws InvalidConnectionException, InvalidViewException, ParseException, IOException {
        menuGame();
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

    private void launchThread() {
         new Thread(() -> {

             while(true) {
                 consoleWriter.println("inserisci carta:");
                 Scanner scanner = new Scanner(System.in);
                 String fromThread;

                 fromThread = scanner.nextLine();

                 consoleWriter.println(fromThread);
                 WindowCard selectedW = findInCards(fromThread);
                 if(selectedW != null) {
                     try {
                         createChooseCardEvent(findInCards(fromThread));
                     } catch (InvalidConnectionException | IOException | InvalidViewException | ParseException e) {
                         LOGGER.log(Level.SEVERE, e.toString(), e);
                     }
                     break;
                 }

             }
         }).start();
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

    private void menuGame() throws InvalidConnectionException, ParseException, InvalidViewException, IOException {
         boolean turn = true;
         String choose;
         System.out.println("");
         while (turn){
             System.out.println("CHOOSE YOUR MOVE\n1- Place Die\n2- Use Toolcard\n3- Skip turn");
             choose = consoleScanner.next();
             if (choose.equals("1") || choose.equals("2") || choose.equals("3"))
             switch (choose) {
                 case "1" :createPlaceDieEvent();
                     createSkipTurnEvent();
                     turn = false;
                     break;
                 case "2":
                     createUseToolEvent();//devo gestirlo lato server
                     turn = false;
                     break;
                 case "3":createSkipTurnEvent();
                     turn = false;
                     break;
                 default:
                     break;
             }
             else
                 System.out.println("Invalid choice, try again!");
         }

    }


}