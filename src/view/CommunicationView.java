package view;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import model.UserList;

import java.util.Observable;
import java.util.Observer;


public class CommunicationView extends Application implements Observer {

    private UserList users;

    private ListView<String> listView = null;

    private void init(Stage primaryStage) {

        users = UserList.getInstance();

        Group root = new Group();
        primaryStage.setScene(new Scene(root));

        /*final ListView<String>*/ listView = new ListView<String>(users.getObsUsersList());


        listView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        root.getChildren().add(listView);
    }

    @Override public void start(Stage primaryStage) throws Exception {
        init(primaryStage);
        primaryStage.show();
    }


    @Override
    public void update(Observable o, Object arg) {
        System.out.println("User received, updating the listView");
        listView.refresh();
    }

    public static void main(String[] args) { launch(args); }



}