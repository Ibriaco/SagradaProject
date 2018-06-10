package it.polimi.se2018.View.ViewEvents;

public class PrivateEvent extends VCEvent {

    private String connectionType;

    public PrivateEvent(String connectionType, String username) {

        super(username);
        this.connectionType = connectionType;
    }

    public String getConnectionType() {
        return connectionType;
    }
}
