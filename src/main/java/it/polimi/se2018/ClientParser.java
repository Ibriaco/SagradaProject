package it.polimi.se2018;
import it.polimi.se2018.org.json.simple.JSONObject;
import it.polimi.se2018.org.json.simple.parser.JSONParser;
import it.polimi.se2018.org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URLDecoder;

import static it.polimi.se2018.ClientConfig.*;

public class ClientParser {

    public Object createObj(){
        JSONParser jsonParser = new JSONParser();
        Object object = null;
        try{
            try {
                object = jsonParser.parse(new FileReader("./src/main/resources/GameResources/ClientConfiguration.json"));
            }catch (FileNotFoundException e){
                String decodedPath = URLDecoder.decode("./ClientConfiguration.json", "UTF-8");
                object = jsonParser.parse(new FileReader(decodedPath));

            }
        }catch (Exception e){
            try {
                object = jsonParser.parse(new InputStreamReader(getClass().getResourceAsStream("./ClientConfiguration.json")));
            } catch (IOException e1) {
                e1.printStackTrace();
            } catch (ParseException e1) {
                e1.printStackTrace();
            }
        }
        return object;
    }

    public void reader(Object object) throws ParseException {

            JSONObject jsonObject = (JSONObject)object;
            RMI_CONNECTION = (String)jsonObject.get("RMI_CONNECTION");
            SOCKET_CONNECTION = (String)jsonObject.get("SOCKET_CONNECTION");
            ONE_STRING = (String)jsonObject.get("ONE_STRING");
            TWO_STRING = (String)jsonObject.get("TWO_STRING");
            THREE_STRING = (String)jsonObject.get("THREE_STRING");
            LOGIN_PAGE = (String)jsonObject.get("LOGIN_PAGE");
            INSERT_YOUR_USERNAME = (String)jsonObject.get("INSERT_YOUR_USERNAME");
            SELECT_CONNECTION_TYPE = (String)jsonObject.get("SELECT_CONNECTION_TYPE");
            INSERT_DIE_NUMBER = (String)jsonObject.get("INSERT_DIE_NUMBER");
            INSERT_COLUMN = (String)jsonObject.get("INSERT_COLUMN");
            INSERT_ROW = (String)jsonObject.get("INSERT_ROW");
            DRAFT_POOL = (String)jsonObject.get("DRAFT_POOL");
            CHOOSE_MINI_MOVE = (String)jsonObject.get("CHOOSE_MINI_MOVE");
            CHOOSE_MINI_CHOICE = (String)jsonObject.get("CHOOSE_MINI_CHOICE");
            INSERT_TOOL_NUMBER = (String)jsonObject.get("INSERT_TOOL_NUMBER");
            WINDOW_CARD = (String)jsonObject.get("WINDOW_CARD");
            CHOOSE_WINDOW_CARD = (String)jsonObject.get("CHOOSE_WINDOW_CARD");
            NEW_DIE_VALUE = (String)jsonObject.get("NEW_DIE_VALUE");
            COLUMN_DIE_TO_MOVE = (String)jsonObject.get("COLUMN_DIE_TO_MOVE");
            ROW_DIE_TO_MOVE = (String)jsonObject.get("ROW_DIE_TO_MOVE");
            COLUMN_WHERE_TO_MOVE = (String)jsonObject.get("COLUMN_WHERE_TO_MOVE");
            ROW_WHERE_TO_MOVE = (String)jsonObject.get("ROW_WHERE_TO_MOVE");
            INVALID_MOVE_RETRY = (String)jsonObject.get("INVALID_MOVE_RETRY");
            ONE_INCREASE_TWO_DECREASE = (String)jsonObject.get("ONE_INCREASE_TWO_DECREASE");
            NOT_ENOUGH_TOKENS = (String)jsonObject.get("NOT_ENOUGH_TOKENS");
            ROUND_TRACK = (String)jsonObject.get("ROUND_TRACK");
            CHOOSE_YOUR_MOVE = (String)jsonObject.get("CHOOSE_YOUR_MOVE");
            INVALID_CHOICE = (String)jsonObject.get("INVALID_CHOICE");
            SAGRADA_TITLE = (String)jsonObject.get("SAGRADA_TITLE");
            LOGIN_PATH = (String)jsonObject.get("LOGIN_PATH");
            ALERT_MESSAGE = (String)jsonObject.get("ALERT_MESSAGE");
            WAITING_LOBBY_PATH = (String)jsonObject.get("WAITING_LOBBY_PATH");
            GAME_SCREEN_PATH = (String)jsonObject.get("GAME_SCREEN_PATH");
            SELECTION_MESSAGE = (String)jsonObject.get("SELECTION_MESSAGE");
            WINDOW_CHOICE_PATH = (String)jsonObject.get("WINDOW_CHOICE_PATH");
            PNG = (String)jsonObject.get("PNG");
            PRIVATE_PATH = (String)jsonObject.get("PRIVATE_PATH");
            PUBLIC_PATH = (String)jsonObject.get("PUBLIC_PATH");
            ROUND_PATH = (String)jsonObject.get("ROUND_PATH");
            DICE_PATH = (String)jsonObject.get("DICE_PATH");
            DICE_PATHD = (String)jsonObject.get("DICE_PATHD");
            ERROR_TYPE = (String)jsonObject.get("ERROR_TYPE");
            INFO_TYPE = (String)jsonObject.get("INFO_TYPE");
            SAGRADA_TITLE = (String)jsonObject.get("SAGRADA_TITLE");
            RMI_CLIENT_ADDRESS =(String)jsonObject.get("RMI_CLIENT_ADDRESS");
            SOCKET_CLIENT_ADDRESS =(String)jsonObject.get("SOCKET_CLIENT_ADDRESS");
            POSITION_ROUND_TRACK =(String)jsonObject.get("POSITION_ROUND_TRACK");
            POSITION_DIE_ROUND_TRACK =(String)jsonObject.get("POSITION_DIE_ROUND_TRACK");
            NUMBER_DIE_MOVE =(String)jsonObject.get("NUMBER_DIE_MOVE");
            AFTER_DRAFTING_ERROR =(String)jsonObject.get("AFTER_DRAFTING_ERROR");
            CHOOSE_DIE_VALUE =(String)jsonObject.get("CHOOSE_DIE_VALUE");
            ROUND_TRACK_POSITION =(String)jsonObject.get("ROUND_TRACK_POSITION");
            CELL_POSITION =(String)jsonObject.get("CELL_POSITION");
            AUTENTICATE =(String)jsonObject.get("AUTENTICATE");
            LOGIN_EVENT =(String)jsonObject.get("LOGIN_EVENT");
            URL_NOT_FOUND =(String)jsonObject.get("URL_NOT_FOUND");
            CONNECTION_ERROR =(String)jsonObject.get("CONNECTION_ERROR");
            NOT_BOUND_MESSAGE =(String)jsonObject.get("NOT_BOUND_MESSAGE");
            SELECT_UI =(String)jsonObject.get("SELECT_UI");

    }
}
