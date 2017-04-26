package controller;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.UserCredentialsRetriever;
import model.UserCredentialsSaver;
import view.MainView;
import org.mindrot.jbcrypt.BCrypt;

public class LogInController implements Initializable, ControlledScreen {
	
    private ViewsController myController;
    @FXML
    private Text actiontarget;
    @FXML
    private PasswordField passwordField;
    @FXML
    private TextField username;
    @FXML
    private Label incorrectPassword;


    private Stage prevStage;
    private UserCredentialsRetriever credentialsRetriever;
    private UserCredentialsSaver credentialSaver;


    public LogInController(){
        System.out.println("LogIn Controller initialized.");
    }

    public void setPrevStage(Stage stage){
        this.prevStage = stage;
    }


    public void setUsername(String username){
        this.username.setText(username);
    }

    public void setCredentialsRetriever(UserCredentialsRetriever credentialsRetriever){
        this.credentialsRetriever = credentialsRetriever;
    }

    public void setCredentialSaver(UserCredentialsSaver credentialSaver){
        this.credentialSaver = credentialSaver;
    }

    @Override
    public void setScreenParent(ViewsController screenParent){
        myController = screenParent;
    }

    @Override
    public void initialize(URL location, ResourceBundle rb) {
        actiontarget.setText("");
    }

    @FXML
    void onSignUp(ActionEvent event) {
        /*
        TODO : Save credentials into the database
         */
    }

    @FXML
    void onSignIn(ActionEvent event) throws IOException {


        System.out.println("credentialsRetriever : " + credentialsRetriever);

        String hashed = credentialsRetriever.getHashedPassword(this.username.getText());
        String password = this.passwordField.getText();

        System.out.println("hashed : " + hashed + " | password " + password);

        if (credentialsRetriever.checkPasswordCorrect(hashed, password)) {
            incorrectPassword.setVisible(false);
            System.out.println("It matches");

            //myController.setScreen(MainView.screen2ID);
            Stage stage = new Stage();
            stage.setTitle("Communication View");
            GridPane myPane = null;
            FXMLLoader loader = new FXMLLoader(getClass().getResource(MainView.screen2File));
            myPane = loader.load();
            Scene scene = new Scene(myPane);
            stage.setScene(scene);

            // Get communication controller to set the prevStage
            CommunicationController controller = loader.getController();
            controller.setPrevStage(stage);
            controller.setListener(new LazyCommunicationControllerListener() );
            /* ***************************************** */

            prevStage.close();

            stage.show();

            MulticastController.startAll(username.getText());

        }
        else {
            incorrectPassword.setVisible(true);
            System.out.println("It does not match");
        }
    }
}

