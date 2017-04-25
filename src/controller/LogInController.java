package controller;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import sun.applet.Main;
import view.MainView;

public class LogInController implements Initializable, ControlledScreen {
	
    private ViewsController myController;
    @FXML
    private Text actiontarget;
    @FXML
    private PasswordField passwordField;
    @FXML
    private TextField username;

    private Stage prevStage;


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
        /* ***************************************** */


        prevStage.close();

        stage.show();

        MulticastController.startAll(username.getText());
    }

    public void setUsername(String username){
        this.username.setText(username);
    }

}

