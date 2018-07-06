package it.polimi.se2018.model.event;

import it.polimi.se2018.model.InvalidConnectionException;
import it.polimi.se2018.model.InvalidDieException;
import it.polimi.se2018.model.InvalidViewException;
import it.polimi.se2018.org.json.simple.parser.ParseException;
import it.polimi.se2018.view.ui.ViewInterface;

import java.io.IOException;
import java.util.Map;

public class EndGameEvent implements MVEvent {

    private String username;
    private String content;
    private Map<String,String> results;

    public EndGameEvent(String content, String username, Map<String,String> results) {
        this.username = username;
        this.content = content;
        this.results = results;
    }

    @Override
    public void accept(ViewInterface vi) throws IOException, InvalidConnectionException, InvalidViewException, ParseException, InvalidDieException {
        vi.handleMVEvent(this);
    }

    @Override
    public String getUsername() {
        return username;
    }

    public String getContent() {
        return content;
    }

    public Map<String,String> getResults() {
        return results;
    }
}
