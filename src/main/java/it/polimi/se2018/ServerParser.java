package it.polimi.se2018;

import it.polimi.se2018.org.json.simple.JSONObject;
import it.polimi.se2018.org.json.simple.parser.JSONParser;
import it.polimi.se2018.org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import static it.polimi.se2018.ServerConfig.*;

public class ServerParser {

    public void reader() {
        JSONParser jsonParser = new JSONParser();

        try {
            Object object = jsonParser.parse(new FileReader("./src/main/resources/GameResources/ServerConfiguration.json"));
            JSONObject jsonObject = (JSONObject)object;
            LOCAL_HOST = (String)jsonObject.get("LOCAL_HOST");
            ZERO_VALUE = Integer.parseInt((String)jsonObject.get("ZERO_VALUE"));
            ONE_VALUE = Integer.parseInt((String)jsonObject.get("ONE_VALUE"));
            TWO_VALUE = Integer.parseInt((String)jsonObject.get("TWO_VALUE"));
            THREE_VALUE = Integer.parseInt((String)jsonObject.get("THREE_VALUE"));
            FOUR_VALUE = Integer.parseInt((String)jsonObject.get("FOUR_VALUE"));
            FIVE_VALUE = Integer.parseInt((String)jsonObject.get("FIVE_VALUE"));
            SIX_VALUE = Integer.parseInt((String)jsonObject.get("SIX_VALUE"));
            RED_AMOUNT = Integer.parseInt((String)jsonObject.get("RED_AMOUNT"));
            GREEN_AMOUNT = Integer.parseInt((String)jsonObject.get("GREEN_AMOUNT"));
            YELLOW_AMOUNT = Integer.parseInt((String)jsonObject.get("YELLOW_AMOUNT"));
            PURPLE_AMOUNT = Integer.parseInt((String)jsonObject.get("PURPLE_AMOUNT"));
            BLUE_AMOUNT = Integer.parseInt((String)jsonObject.get("BLUE_AMOUNT"));
            BOOL_TRUE = Boolean.parseBoolean((String)jsonObject.get("BOOL_TRUE"));
            BOOL_FALSE = Boolean.parseBoolean((String)jsonObject.get("BOOL_FALSE"));
            CLI_UI = (String)jsonObject.get("CLI_UI");
            GUI_UI = (String)jsonObject.get("GUI_UI");
            ROWS = Integer.parseInt((String)jsonObject.get("ROWS"));
            COLS = Integer.parseInt((String)jsonObject.get("COLS"));
            SOCKET_PORT = Integer.parseInt((String)jsonObject.get("SOCKET_PORT"));
            RMI_PORT = Integer.parseInt((String)jsonObject.get("RMI_PORT"));
            TURN_TIMER = Integer.parseInt((String)jsonObject.get("TURN_TIMER"));
            LOBBY_TIMER = Integer.parseInt((String)jsonObject.get("LOBBY_TIMER"));
            LOGIN_SUCCESSFULLY = (String)jsonObject.get("LOGIN_SUCCESSFULLY");
            USER = (String)jsonObject.get("USER");
            ONLINE_PLAYERS = (String)jsonObject.get("ONLINE_PLAYERS");
            TIMER_EXPIRED = (String)jsonObject.get("TIMER_EXPIRED");
            USERNAME_ALREADY_USED = (String)jsonObject.get("USERNAME_ALREADY_USED");
            FULL_LOBBY = (String)jsonObject.get("FULL_LOBBY");
            INVALID_USER = (String)jsonObject.get("INVALID_USER");
            SLEEP_TIME = Integer.parseInt((String)jsonObject.get("SLEEP_TIME"));
            AFTER_DRAFTING = (String)jsonObject.get("AFTER_DRAFTING");
            ON_WINDOW = (String)jsonObject.get("ON_WINDOW");
            ON_DRAFT = (String)jsonObject.get("ON_DRAFT");
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
