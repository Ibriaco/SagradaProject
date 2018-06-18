package it.polimi.se2018.view.ui.guicontrollers;

import com.jfoenix.controls.*;
import it.polimi.se2018.model.event.*;
import it.polimi.se2018.model.InvalidConnectionException;
import it.polimi.se2018.model.InvalidViewException;
import it.polimi.se2018.MyObservable;
import it.polimi.se2018.MyObserver;
import it.polimi.se2018.network.client.NetworkHandler;
import it.polimi.se2018.view.ui.ViewInterface;
import it.polimi.se2018.view.viewevents.LoginEvent;
import it.polimi.se2018.view.viewevents.VCEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ArrayList;

public class GUILoginController implements ViewInterface{

    private ArrayList<MyObserver> observerCollection = new ArrayList<>();
    private VCEvent myEvent;

    @FXML
    JFXButton loginBtn;
    @FXML
    JFXSpinner spin;
    @FXML
    JFXToggleButton rmiBtn;
    @FXML
    JFXToggleButton socketBtn;
    @FXML
    JFXTextField userField;

    @FXML
    public void handleLogin(MouseEvent event) throws RemoteException{
        int c;
        if((rmiBtn.isSelected() || socketBtn.isSelected()) && !userField.getText().equals("")) {
            spin.setVisible(true);
            NetworkHandler nh;

            if (rmiBtn.isSelected())
                nh = new NetworkHandler(1);
            else
                nh = new NetworkHandler(2);

            try{
            nh.registerObserver(this);
            registerObserver(nh);
                myEvent = new LoginEvent(userField.getText());
                notifyObservers();
            } catch (RemoteException e) {
                e.printStackTrace();
            }

            Node node = (Node) event.getSource();
            Stage stage = (Stage) node.getScene().getWindow();
            stage.centerOnScreen();
            Scene scene = stage.getScene();
            URL url = null;
            try {
                url = new File("src/main/resources/GUIUtils/waitingLobby.fxml").toURI().toURL();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            Parent root = null;
            try{root = FXMLLoader.load(url);
            } catch (IOException e) {
                e.printStackTrace();
            }
            stage.setHeight(540);
            stage.setWidth(960);
            scene.setRoot(root);
        }
        else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Insert a valid connection type and username please.");
            alert.showAndWait()
                    .filter(response -> response == ButtonType.OK);
        }
    }

    public void handleToggle(MouseEvent mouseEvent) {
        if(mouseEvent.getSource().equals(rmiBtn)) {
            rmiBtn.setSelected(true);
            socketBtn.setSelected(false);
        }
        else{
            rmiBtn.setSelected(false);
            socketBtn.setSelected(true);
        }
    }

    @Override
    public void updateWindowCard() {

    }

    @Override
    public void showUI() throws RemoteException {

    }

    @Override
    public void receiveEvent(VCEvent event) throws InvalidConnectionException, RemoteException, InvalidViewException {

    }

    @Override
    public void loginScreen() throws RemoteException, InvalidConnectionException, InvalidViewException {

    }

    @Override
    public void handleMVEvent(LoggedUserEvent event) {

    }

    @Override
    public void handleMVEvent(PrivateCardEvent privateCardEvent) {

    }

    @Override
    public void handleMVEvent(WindowCardEvent event) {

    }

    @Override
    public void handleMVEvent(NewGameEvent newGameEvent) {

    }

    @Override
    public void handleMVEvent(PublicCardEvent publicCardEvent) {

    }

    @Override
    public void handleMVEvent(ToolCardEvent toolCardEvent) {

    }

    @Override
    public void registerObserver(MyObserver observer) throws RemoteException {
        observerCollection.add(observer);
    }

    @Override
    public void unregisterObserver(MyObserver observer) throws RemoteException {

    }

    @Override
    public void notifyObservers() throws RemoteException {
        for (MyObserver o: observerCollection) {
            try {
                o.update(this, myEvent);
            } catch (InvalidConnectionException | IOException | ParseException | InvalidViewException e) {
                e.printStackTrace();
            }
        }

    }

    /*@Override
    public void update(MyObservable o, Object arg) throws RemoteException {
        System.out.println("Sono la gui: " + arg.toString());
    }*/

    @Override
    public void update(MyObservable o, VCEvent arg) throws RemoteException, InvalidConnectionException, InvalidViewException {
        System.out.println("Sono la gui " + arg.toString());
    }

    @Override
    public void update(MyObservable o, MVEvent arg) throws RemoteException, InvalidConnectionException, InvalidViewException {

    }
}
