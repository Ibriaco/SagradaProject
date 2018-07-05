package it.polimi.se2018.view.ui;


import it.polimi.se2018.controller.ChangeDieEvent;
import it.polimi.se2018.model.*;
import it.polimi.se2018.model.Color;
import it.polimi.se2018.model.event.*;
import it.polimi.se2018.MyObservable;
import it.polimi.se2018.MyObserver;
import it.polimi.se2018.network.client.NetworkHandler;
import it.polimi.se2018.org.json.simple.parser.ParseException;
import it.polimi.se2018.view.viewevents.*;
import it.polimi.se2018.view.viewevents.PlaceDieEvent;
import it.polimi.se2018.view.viewevents.RollDiceEvent;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.*;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import static it.polimi.se2018.ClientConfig.*;
import static it.polimi.se2018.view.ui.CLIUtils.*;
import static it.polimi.se2018.view.ui.CLIUtils.ANSI_BLUE;
import static it.polimi.se2018.view.ui.CLIUtils.ANSI_GREEN;
import static it.polimi.se2018.view.ui.CLIUtils.ANSI_PURPLE;
import static it.polimi.se2018.view.ui.CLIUtils.ANSI_RED;
import static it.polimi.se2018.view.ui.CLIUtils.ANSI_RESET;
import static it.polimi.se2018.view.ui.CLIUtils.ANSI_WHITE;
import static it.polimi.se2018.view.ui.CLIUtils.ANSI_YELLOW;

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
    private static final Logger LOGGER = Logger.getLogger(CLIView.class.getName());

    /**
     * Updates a window card
     */
    @Override
    public void updateWindowCard() {

    }

    /**
     * Shows the user interface
     *
     * @throws RemoteException            thrown exception
     * @throws InvalidConnectionException thrown exception
     * @throws InvalidViewException       thrown exception
     */
    @Override
    public void showUI() throws IOException, InvalidConnectionException, InvalidViewException, ParseException, InvalidDieException {
        String choice;
        boolean validInput = false;
        while (!validInput) {
            choice = printConnectionChoice();
            if (choice.equals(ONE_STRING) || choice.equals(TWO_STRING)) {
                createNH(choice);
                validInput = true;
                loginScreen();
            } else
                printOnConsole(INVALID_INPUT);
        }
    }

    /**
     * Shows the login interface to the user
     *
     * @throws RemoteException            thrown exception
     * @throws InvalidConnectionException thrown exception
     * @throws InvalidViewException       thrown exception
     */
    public void loginScreen() throws IOException, InvalidConnectionException, InvalidViewException, ParseException, InvalidDieException {
        printOnConsole(LOGIN_PAGE);
        printOnConsole(INSERT_YOUR_USERNAME);
        setUsername(consoleScanner.nextLine());
        createLoginEvent();
    }


    @Override
    public void receiveEvent(VCEvent event) {
        /*Intentionally left void, used only in VirtualView*/
    }

    private String printConnectionChoice() throws IOException {

        Scanner scanner = new Scanner(System.in);
        printOnConsole(SELECT_CONNECTION_TYPE);
        printOnConsole(RMI_CONNECTION);
        printOnConsole(SOCKET_CONNECTION);
        return scanner.nextLine();
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
    public void update(MyObservable o, VCEvent arg) {
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
        printOnConsole(INSERT_DIE_NUMBER);
        int pos = getNumber() - 1;
        printOnConsole(INSERT_ROW);
        int coordY = getNumber() - 1;
        printOnConsole(INSERT_COLUMN);
        int coordX = getNumber() - 1;
        vcEvent = new PlaceDieEvent(user, pos, coordX, coordY);
        notifyObservers();
    }

    public void createSkipTurnEvent() throws InvalidConnectionException, IOException, InvalidViewException, ParseException, InvalidDieException {
        vcEvent = new SkipTurnEvent(user);
        notifyObservers();
    }

    public void createUseToolEvent() throws InvalidConnectionException, IOException, InvalidViewException, ParseException, InvalidDieException {
        printOnConsole(INSERT_TOOL_NUMBER);
        int pos = getNumber() - 1;
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
        Scanner scanner = new Scanner(System.in);
        return Integer.parseInt(scanner.nextLine());
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
    public void handleMVEvent(PrivateCardEvent event) {
        event.printPrivateName();
    }


    @Override
    public void handleMVEvent(WindowCardEvent event) throws RemoteException, InvalidConnectionException, InvalidViewException {
        Color color;
        int value;
        printOnConsole(WINDOW_CARD);
        myCardList = event.getWindowCards();
        boolean ok = true;
        for (WindowCard w : event.getWindowCards()) {
            printOnConsole(w.getWindowName() + " " + w.getDifficulty());
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 5; j++) {
                    color = w.getGridCell(i, j).getColor();
                    value = w.getGridCell(i, j).getShade();
                    printCell(color, value);
                }
                printOnConsole("");
            }
        }
        String fromThread;
        while (true) {
            printOnConsole(CHOOSE_WINDOW_CARD);
            Scanner scanner = new Scanner(System.in);

            fromThread = scanner.nextLine();
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

        for (WindowCard w : event.getWindowCardList()) {
            printOnConsole("\n" + event.getUser().get(userN) + "\t" + w.getWindowName());
            for (int i = 0; i < w.getRows(); i++) {
                for (int j = 0; j < w.getCols(); j++) {
                    if (w.getGridCell(i, j).isPlaced()) {
                        printDie(w.getGridCell(i, j).getPlacedDie());
                    } else {
                        color = w.getGridCell(i, j).getColor();
                        value = w.getGridCell(i, j).getShade();
                        printCell(color, value);
                    }
                }
                printOnConsole("");
            }
            userN++;
            printOnConsole("\n");
        }
        int i = 1;
        printOnConsole(DRAFT_POOL);
        for (Die d : event.getDice()) {
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
        if (user.equals(event.getUser()))
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
        printOnConsole(INSERT_DIE_NUMBER);
        int pos = getNumber() - 1;
        vcEvent = new SelectDieEvent(changeDieEvent.getUsername(), pos);
        notifyObservers();
    }

    @Override
    public void handleMVEvent(ModifiedPlaceEvent modifiedPlaceEvent) throws InvalidConnectionException, ParseException, InvalidViewException, IOException, InvalidDieException {
        printOnConsole(INSERT_ROW);
        int y = getNumber() - 1;
        printOnConsole(INSERT_COLUMN);
        int x = getNumber() - 1;
        vcEvent = new PlaceDieEvent(modifiedPlaceEvent.getUsername(), modifiedPlaceEvent.getPos(), x, y);
        notifyObservers();
        vcEvent = new SkipTurnEvent(modifiedPlaceEvent.getUsername());
        notifyObservers();
    }

    @Override
    public void handleMVEvent(IsNotYourTurn event) {
        event.printMessage();
    }

    @Override
    public void handleMVEvent(ChangedDieEvent changedDieEvent) throws InvalidDieException, InvalidConnectionException, ParseException, InvalidViewException, IOException {
        printOnConsole(NEW_DIE_VALUE);
        printDie(changedDieEvent.getDie());
        printOnConsole("");
    }

    @Override
    public void handleMVEvent(MoveDieEvent moveDieEvent) throws InvalidDieException, InvalidConnectionException, ParseException, InvalidViewException, IOException {
        printOnConsole(ROW_DIE_TO_MOVE);
        int oldY = getNumber() - 1;
        printOnConsole(COLUMN_DIE_TO_MOVE);
        int oldX = getNumber() - 1;
        printOnConsole(INSERT_ROW);
        int newY = getNumber() - 1;
        printOnConsole(INSERT_COLUMN);
        int newX = getNumber() - 1;
        vcEvent = new MovingDieEvent(moveDieEvent.getUsername(), oldX, oldY, newX, newY);
        notifyObservers();


    }

    @Override
    public void handleMVEvent(WrongPlaceEvent event) throws InvalidDieException, InvalidConnectionException, InvalidViewException, ParseException, IOException {
        printOnConsole(INVALID_MOVE_RETRY);
        menuGame();
    }

    @Override
    public void handleMVEvent(IncDecEvent incDecEvent) throws InvalidDieException, InvalidConnectionException, ParseException, InvalidViewException, IOException {
        int choice = 0;
        Scanner scanner = new Scanner(System.in);
        while (choice != 1 && choice != 2) {
            printOnConsole(ONE_INCREASE_TWO_DECREASE);
            choice = scanner.nextInt();
        }
        vcEvent = new IncrementDecrementDieEvent(incDecEvent.getUsername(), choice);
        notifyObservers();
    }

    @Override
    public void handleMVEvent(InvalidToolEvent invalidToolEvent) {
        printOnConsole(NOT_ENOUGH_TOKENS);
        try {
            menuGame();
        } catch (InvalidConnectionException | ParseException | InvalidViewException | IOException | InvalidDieException e) {
            LOGGER.log(Level.SEVERE, e.toString(), e);
        }
    }

    @Override
    public void handleMVEvent(PerformActionEvent performActionEvent) throws InvalidDieException, InvalidConnectionException, InvalidViewException, ParseException, IOException {
        miniMenu();
    }

    @Override
    public void handleMVEvent(RetryToolEvent retryToolEvent) throws InvalidDieException, InvalidConnectionException, InvalidViewException, ParseException, IOException {
        printOnConsole("This ia an \"After Drafting\" tool card ! Please try with a different one!");
        miniChoice();
    }

    @Override
    public void handleMVEvent(RollingDiceEvent rollDiceEvent) throws InvalidDieException, InvalidConnectionException, ParseException, InvalidViewException, IOException {
        vcEvent = new RollDiceEvent(rollDiceEvent.getUsername());
        notifyObservers();
    }

    @Override
    public void handleMVEvent(SetDieEvent setDieEvent) throws InvalidDieException, InvalidConnectionException, ParseException, InvalidViewException, IOException {
        printOnConsole("Choose the value you want to give to the die!");
        Scanner sc = new Scanner(System.in);
        boolean ok = true;
        while(ok){
            String value = sc.nextLine();
            if(value.equals("1")||value.equals("2")||value.equals("3")||value.equals("4")||value.equals("5")||value.equals("6")) {
                int val = Integer.parseInt(value);
                vcEvent = new UpdateDieEvent(user, val, setDieEvent.getPos());
                notifyObservers();
                ok = false;
            }
            else
                printOnConsole(INVALID_CHOICE);
        }
    }

    @Override
    public void handleMVEvent(SwapDieEvent swapDieEvent) throws InvalidDieException, InvalidConnectionException, ParseException, InvalidViewException, IOException {
        printOnConsole("Inserisci posizione nel round track");
        int roundPos = getNumber()-1;
        printOnConsole("Inserisci posizione nella cella");
        int cellPos = getNumber()-1;
        vcEvent = new SwappingDieEvent(user, roundPos, cellPos);
        notifyObservers();
    }

    @Override
    public void handleMVEvent(MiniMenuEvent miniMenuEvent) throws InvalidDieException, InvalidConnectionException, InvalidViewException, ParseException, IOException {
        miniChoice();
    }

    @Override
    public void handleMVEvent(DoublePlaceEvent doublePlaceEvent) throws InvalidDieException, InvalidConnectionException, ParseException, InvalidViewException, IOException {
        System.out.println("ho ricevuto double place event");
        //createPlaceDieEvent();
        printOnConsole(INSERT_DIE_NUMBER);
        int pos = getNumber() - 1;
        printOnConsole(INSERT_ROW);
        int coordY = getNumber() - 1;
        printOnConsole(INSERT_COLUMN);
        int coordX = getNumber() - 1;
        vcEvent = new PlaceDieEvent(doublePlaceEvent.getUsername(), pos, coordX, coordY);
        notifyObservers();
        //createPlaceDieEvent();
    }


    private void printDie(Die d) {
        Color color = d.getColor();
        int value = d.getValue();
        String number = "";
        switch (value) {
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


    private void printCell(Color color, int value) {
        String toPrint;

        if (value == 0) {
            toPrint = "\u25FC";
            if (color == null) {
                consoleWriter.print(toPrint);
            } else {
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

            switch (value) {
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


    private void printRoundTrack(List<RoundCell> roundTrack) {
        int counter = 1;
        printOnConsole(ROUND_TRACK);
        for (RoundCell roundCell : roundTrack) {
            System.out.print(counter + ")  ");
            for (Die die : roundCell.getDiceList()) {
                printDie(die);
                System.out.print("\t");
            }
            counter++;
            printOnConsole("");
        }
    }

    private void menuGame() throws InvalidConnectionException, ParseException, InvalidViewException, IOException, InvalidDieException {
        boolean turn = true;
        Scanner scanner = new Scanner(System.in);
        String choose;
        printOnConsole("");
        while (turn) {
            printOnConsole(CHOOSE_YOUR_MOVE);
            choose = scanner.nextLine();

            if (choose.equals(ONE_STRING)) {
                createPlaceDieEvent();
                turn = false;
            } else if (choose.equals(TWO_STRING)) {
                createUseToolEvent();
                turn = false;
            } else if (choose.equals(THREE_STRING)) {
                createSkipTurnEvent();
                turn = false;
            } else
                printOnConsole(INVALID_CHOICE);
        }

    }

    private void miniMenu() throws InvalidDieException, ParseException, InvalidViewException, IOException, InvalidConnectionException {
        boolean turn = true;
        Scanner scanner = new Scanner(System.in);
        String choose;
        printOnConsole("");
        while (turn) {
            printOnConsole(CHOOSE_MINI_MOVE);
            choose = scanner.nextLine();
            if (choose.equals(ONE_STRING)) {
                createPlaceDieEvent();
                turn = false;
            }
            if (choose.equals(TWO_STRING)) {
                createSkipTurnEvent();
                turn = false;
            }
        }
    }

    private void miniChoice() throws InvalidDieException, InvalidConnectionException, ParseException, InvalidViewException, IOException {
        boolean turn = true;
        Scanner scanner = new Scanner(System.in);
        String choose;
        printOnConsole("");
        while (turn) {
            printOnConsole(CHOOSE_MINI_CHOICE);
            choose = scanner.nextLine();
            if (choose.equals(ONE_STRING)) {
                createUseToolEvent();
                //createSkipTurnEvent();
                turn = false;
            }
            if (choose.equals(TWO_STRING)) {
                createSkipTurnEvent();
                turn = false;
            }
        }
    }
}

