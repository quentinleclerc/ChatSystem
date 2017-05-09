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
import javafx.scene.Group;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import network.IpGetter;

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

        /* *****
        Group root = new Group();
        Scene scene = new Scene(root, 300, 250);
        // 5 pixels space between child nodes
        VBox vbox = new ConversationView();
        // 1 pixel padding between child nodes only
        root.getChildren().add(vbox);

        primaryStage.setScene(scene);
        primaryStage.show();
        /* ****** */

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
            stage.setMaximized(true);

            CommunicationController controller = loader.getController();


            int portParsed;
            try {
                portParsed = Integer.parseInt(port);
            } catch (NumberFormatException  e) {
                portParsed = 0;
            }

            IpGetter ipgetter = new IpGetter();

            User localUser = new User(username, ipgetter.getIP(), portParsed, User.typeConnect.CONNECTED);

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