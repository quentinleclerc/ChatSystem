package view;

import controller.ViewsController;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainView extends Application {
	/**
	 * Be careful, depending on the IDE you have to use the relative
	 * or absolute path. Don't forget to add the resources folder
	 * to your java build path
	 */
    public static String screen1ID = "LoginView";
    public static String screen1File  = "/fxml/LogInView.fxml";
    public static String screen2ID = "CommunicationView";
    public static String screen2File  = "/fxml/CommunicationView.fxml";
    
    private static Stage primaryStage;

    
    @Override
    public void start(Stage primaryStage) {

        ViewsController mainContainer = new ViewsController();

        mainContainer.loadScreen(MainView.screen1ID, MainView.screen1File);
        mainContainer.loadScreen(MainView.screen2ID, MainView.screen2File);

        mainContainer.setScreen(MainView.screen1ID);
        MainView.primaryStage = primaryStage;
        Group root = new Group();
        root.getChildren().addAll(mainContainer);
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    public static Stage getMainStage(){
        return primaryStage;
    }
}