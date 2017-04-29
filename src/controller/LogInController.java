package controller;

import javafx.fxml.Initializable;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import view.MainView;

public class LogInController implements Initializable, ControlledScreen {
	
    private ViewsController myController;
    @FXML
    private Text actiontarget;
    @FXML
    private PasswordField passwordField;
    @FXML
    private TextField username;
    @FXML
    private TextField port;

    private Stage prevStage;

    private MainView mainView;


    public LogInController() {
        System.out.println("LogIn Controller initialized.");
    }

    public void setPrevStage(Stage stage){
        this.prevStage = stage;
    }

    @Override
    public void initialize(URL location, ResourceBundle rb) {
        actiontarget.setText("");
    }
    
    @Override
    public void setScreenParent(ViewsController screenParent){
        myController = screenParent;
    }

    @FXML
    void onSignIn(ActionEvent event) throws IOException {
        this.mainView.showCommunicationView(this.prevStage, username.getText(), port.getText());
    }

    public void setMainView(MainView mainView) {
        this.mainView = mainView;
    }
}

