package view;

import controller.CommunicationController;
import controller.LazyCommunicationControllerListener;
import controller.LogInController;
import controller.MulticastController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import model.User;

import java.io.IOException;
import java.net.InetAddress;

public class MainView extends Application {

	/**
	 * Be careful, depending on the IDE you have to use the relative
	 * or absolute path. Don't forget to add the resources folder
	 * to your java build path.
	 */
    private static String LoginViewFXML = "/fxml/LogInView.fxml";
    private static String CommunicationViewFXML = "/fxml/CommunicationView.fxml";


    public MainView() {
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        showLoginView(primaryStage, true);
    }

    public static void main(String[] args) {
        launch(args);
    }

    public void showLoginView(Stage prevStage, Boolean start) {
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

            if (!start) {
                prevStage.close();
                MulticastController.stopAll();
            }

            stage.show();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showCommunicationView(Stage prevStage, String username, String port){
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

            controller.setPrevStage(stage);
            controller.setListener(new LazyCommunicationControllerListener() );
            controller.setLocalUser(localUser);
            controller.setMainView(this);

            (new Thread(){
                public void run() {
                    controller.enableReception();
                }
            }).start();

            prevStage.close();
            stage.show();
            MulticastController.startAll(localUser);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }



}