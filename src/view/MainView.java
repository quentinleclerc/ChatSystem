package view;

import controller.LogInController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;

public class MainView extends Application {

	/**
	 * Be careful, depending on the IDE you have to use the relative
	 * or absolute path. Don't forget to add the resources folder
	 * to your java build path.
	 */
    public static String LoginViewFXML = "/fxml/LogInView.fxml";
    public static String CommunicationViewFXML = "/fxml/CommunicationView.fxml";

    @Override
    public void start(Stage primaryStage) throws IOException {

        /*
        http://stackoverflow.com/questions/30814258/javafx-pass-parameters-while-instantiating-controller-class
         */

        FXMLLoader loader = new FXMLLoader(getClass().getResource(LoginViewFXML));
        GridPane grid = loader.load();

        LogInController controller = loader.getController();
        controller.setUsername("Quentin");
        controller.setPrevStage(primaryStage);

        Scene scene = new Scene(grid);
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }

}