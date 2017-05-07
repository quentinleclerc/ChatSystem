package view;

import controller.*;

import communication.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.*;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

public class MainView extends Application {
 
    private static String LoginViewFXML = "/fxml/LogInView.fxml";
    private static String CommunicationViewFXML = "/fxml/CommunicationView.fxml";

    private ObservableList<User> userList;
    private MessageReceiverThread receiver;
    private Thread receiverThread;

    public MainView() {
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        System.setProperty("java.net.preferIPv4Stack", "true");

        List<User > users = new ArrayList<>();
        userList = FXCollections.observableArrayList(users);

        MulticastController multiControl = new MulticastController(userList);

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


            if (!start) {
                multiControl.stopAll();
                try {
                    receiver.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    receiverThread.interrupt();
                }
                prevStage.close();
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

            receiver = new MessageReceiverThread(localUser);
            receiver.setComController(controller);
            receiverThread = new Thread(receiver);
            receiverThread.start();

            UserDiscussionLink discussionLink = new UserDiscussionLink(localUser);
            controller.setModel(this.userList);
            controller.setPrevStage(stage);
            controller.setSender(new MessageSender());
            controller.setLocalUser(localUser);
            controller.setMainView(this);
            multiControl.setUserDiscLink(discussionLink);
            controller.setMultiControl(multiControl);
            controller.setUserDiscussionLink(discussionLink);

            prevStage.close();
            stage.show();
            multiControl.startAll(localUser);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }



}