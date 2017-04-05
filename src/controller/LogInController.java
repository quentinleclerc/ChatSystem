package controller;

import javafx.fxml.Initializable;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.text.Text;
import view.MainView;

public class LogInController implements Initializable, ControlledScreen {

    @FXML
    private Text actiontarget;

    @FXML
    private PasswordField passwordField;

    private ViewsController myController;


    @Override
    public void initialize(URL location, ResourceBundle rb) {
        actiontarget.setText("");
    }

    @FXML
    void onSignIn(ActionEvent event) {
        myController.setScreen(MainView.screen2ID);
    }

    public void setScreenParent(ViewsController screenParent){
        myController = screenParent;
    }

}

