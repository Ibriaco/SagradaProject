package it.polimi.se2018.view.ui;

import java.io.PrintWriter;
import java.util.Scanner;
import java.util.logging.Logger;

public final class CLIUtils {

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_WHITE = "\u001B[37m";
    public static final String rmi = "RMI";
    public static final String socket = "SOCKET";
    public static final PrintWriter consoleWriter = new PrintWriter(System.out, true);
    public static final Scanner consoleScanner = new Scanner(System.in);
    public static final PrintWriter consoleErrorWriter = new PrintWriter(System.err, true);
    private final static String SPLASH_ART = " __        __   _                             _           ____                            _       \n" +
            " \\ \\      / /__| | ___ ___  _ __ ___   ___   | |_ ___    / ___|  __ _  __ _ _ __ __ _  __| | __ _ \n" +
            "  \\ \\ /\\ / / _ \\ |/ __/ _ \\| '_ ` _ \\ / _ \\  | __/ _ \\   \\___ \\ / _` |/ _` | '__/ _` |/ _` |/ _` |\n" +
            "   \\ V  V /  __/ | (__ (_) | | | | | |  __/  | |_ (_) |   ___) | (_| | (_| | | | (_| | (_| | (_| |\n" +
            "    \\_/\\_/ \\___|_|\\___\\___/|_| |_| |_|\\___|   \\__\\___/   |____/ \\__,_|\\__, |_|  \\__,_|\\__,_|\\__,_|\n" +
            "                                                                      |___/                       ";
    public static final void printSplashArt(){
        consoleWriter.println(SPLASH_ART);
    }
    public static final void printOnConsole(String toPrint){
        System.out.println(toPrint);
    }


}
