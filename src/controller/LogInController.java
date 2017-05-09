package controller;

import javafx.beans.binding.Bindings;
import javafx.fxml.Initializable;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import view.MainApp;
import model.UserCredentialsRetriever;
import model.UserCredentialsSaver;

public class LogInController implements Initializable {

    @FXML
    private PasswordField passwordField;
    @FXML
    private TextField username;
    @FXML
    private Label incorrectPassword;
    @FXML
    private Label userAlreadyRegistered;
    @FXML
    private Label userCorrectlyRegistered;
    @FXML
    private TextField port;
    @FXML
    private Button signUp;
    @FXML
    private Button signIn;

    private Stage prevStage;

    private UserCredentialsRetriever credentialsRetriever;

    private UserCredentialsSaver credentialSaver;

    private MainApp mainApp;

    private MulticastController multiControl;

  
    public LogInController(){
        System.out.println("LogIn Controller initialized.");
    }

    public void setPrevStage(Stage stage){
        this.prevStage = stage;
    }

    public void setCredentialsRetriever(UserCredentialsRetriever credentialsRetriever){
        this.credentialsRetriever = credentialsRetriever;
    }

    public void setCredentialSaver(UserCredentialsSaver credentialSaver){
        this.credentialSaver = credentialSaver;
    }
  
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    public void setMultiControl(MulticastController multiControl) {
        this.multiControl = multiControl;
    }

    @Override
    public void initialize(URL location, ResourceBundle rb) {
        signIn.disableProperty().bind(
                Bindings.isEmpty(username.textProperty())
                        .or(Bindings.isEmpty(passwordField.textProperty()))
                                );

        signUp.disableProperty().bind(
                Bindings.isEmpty(username.textProperty())
                        .or(Bindings.isEmpty(passwordField.textProperty()))
        );
    }

    @FXML
    void onSignUp(ActionEvent event) {
        if (this.credentialsRetriever.checkUserRegistered(this.username.getText())) {
            setVisible(this.userAlreadyRegistered);
        } else {
            this.credentialSaver.saveUserCredentials(this.username.getText(), this.passwordField.getText());
            setVisible(userCorrectlyRegistered);
        }
    }

    @FXML
    void onSignIn(ActionEvent event) throws IOException {
        String hashed = credentialsRetriever.getHashedPassword(this.username.getText());
        String password = this.passwordField.getText();

        if (credentialsRetriever.checkPasswordCorrect(hashed, password)) {
            this.mainApp.showCommunicationView(this.prevStage, username.getText(), port.getText(), multiControl);
        }
        else {
            setVisible(incorrectPassword);
        }
    }
  
    private void setVisible(Label lab) {
        incorrectPassword.setVisible(false);
        userAlreadyRegistered.setVisible(false);
        userCorrectlyRegistered.setVisible(false);
        lab.setVisible(true);
    }
}




