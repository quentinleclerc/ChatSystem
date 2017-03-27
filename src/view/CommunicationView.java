package view;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import model.MessageUser;
import model.UserList;

import java.net.InetAddress;
import java.util.Observable;
import java.util.Observer;


public class CommunicationView extends Application implements Observer {

    private UserList users;

    private void init(Stage primaryStage) {
        users = new UserList();
        users.addObserver(this);

        MessageUser myUser1 = null;
        MessageUser myUser2 = null;
        MessageUser myUser3 = null;


        try {
            myUser1 = new MessageUser("Quentin", InetAddress.getByName("127.0.0.1"), 8080, MessageUser.typeConnect.CONNECTED);
            myUser2 = new MessageUser("Luis", InetAddress.getByName("127.0.0.1"), 8080, MessageUser.typeConnect.CONNECTED);
            myUser3 = new MessageUser("Alexandre", InetAddress.getByName("127.0.0.1"), 8080, MessageUser.typeConnect.CONNECTED);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        users.add(1, myUser1);
        users.add(2, myUser2);
        users.add(3, myUser3);

        Group root = new Group();
        primaryStage.setScene(new Scene(root));

        final ListView<String> listView = new ListView<String>(users.getObsUsersList());


        listView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        root.getChildren().add(listView);
    }

    @Override public void start(Stage primaryStage) throws Exception {
        init(primaryStage);
        primaryStage.show();
    }


    @Override
    public void update(Observable o, Object arg) {
        System.out.println("Update");
    }

    public static void main(String[] args) { launch(args); }



}