package it.polimi.se2018.View;

import java.io.PrintWriter;
import java.util.Scanner;

public final class CLIUtils {


    public static final PrintWriter consoleWriter = new PrintWriter(System.out, true);
    public static final Scanner consoleScaner = new Scanner(System.in);
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

}
