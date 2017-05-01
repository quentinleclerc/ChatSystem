package view;

import controller.CommunicationController;
import controller.LazyCommunicationControllerListener;
import controller.LogInController;
import controller.MulticastController;

import model.HashedUserCredentialsRetriever;
import model.HashedUserCredentialsSaver;
import model.User;
import model.UserList;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.InetAddress;

public class MainView extends Application {
 
    private static String LoginViewFXML = "/fxml/LogInView.fxml";
    private static String CommunicationViewFXML = "/fxml/CommunicationView.fxml";

    private UserList userList;
    private MulticastController multiControl;

    public MainView() {
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        System.setProperty("java.net.preferIPv4Stack", "true");
        userList = new UserList() ;
        multiControl = new MulticastController(userList);

        showLoginView(primaryStage, true, multiControl);
    }

    public static void main(String[] args) {
        launch(args);
    }

    public void showLoginView(Stage prevStage, Boolean start, MulticastController multiControl) {
        try {
            Stage stage = new Stage();
            stage.setTitle("Login View");
            GridPane myPane;
            FXMLLoader loader = new FXMLLoader(getClass().getResource(MainView.LoginViewFXML));
            myPane = loader.load();
            Scene scene = new Scene(myPane);
            stage.setScene(scene);

            LogInController controller = loader.getController();
            controller.setPrevStage(stage);
            controller.setMainView(this);
            controller.setMultiControl(multiControl);
            controller.setCredentialsRetriever(new HashedUserCredentialsRetriever());
            controller.setCredentialSaver(new HashedUserCredentialsSaver());
            controller.setPrevStage(primaryStage);

            if (!start) {
                prevStage.close();
                multiControl.stopAll();
            }

            stage.show();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showCommunicationView(Stage prevStage, String username, String port, MulticastController multiControl){
        try {
            Stage stage = new Stage();
            stage.setTitle("Communication View");
            GridPane myPane;
            FXMLLoader loader = new FXMLLoader(getClass().getResource(MainView.CommunicationViewFXML));
            myPane = loader.load();
            Scene scene = new Scene(myPane);
            stage.setScene(scene);

            CommunicationController controller = loader.getController();
            User localUser = new User(username, InetAddress.getByName("127.0.0.1"), Integer.parseInt(port), User.typeConnect.CONNECTED);

            controller.setModel(this.userList);
            controller.setPrevStage(stage);
            controller.setListener(new LazyCommunicationControllerListener() );
            controller.setLocalUser(localUser);
            controller.setMainView(this);
            controller.setMultiControl(multiControl);

            (new Thread(){
                public void run() {
                    controller.enableReception();
                }
            }).start();

            prevStage.close();
            stage.show();
            multiControl.startAll(localUser);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }



}