package controller;

import javafx.fxml.Initializable;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import view.MainView;

public class LogInController implements Initializable, ControlledScreen {
	
    private ViewsController myController;
    @FXML
    private Text actiontarget;
    @FXML
    private PasswordField passwordField;
    @FXML
    private TextField username;


    @Override
    public void initialize(URL location, ResourceBundle rb) {
        actiontarget.setText("");
    }
    
    @Override
    public void setScreenParent(ViewsController screenParent){
        myController = screenParent;
    }

    @FXML
    void onSignIn(ActionEvent event) {
        myController.setScreen(MainView.screen2ID);
        MulticastController.startAll(username.getText());
    }

}

