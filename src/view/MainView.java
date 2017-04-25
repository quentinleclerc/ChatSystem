package view;

import controller.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

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
    public void start(Stage primaryStage) throws IOException {

        /*
        http://stackoverflow.com/questions/30814258/javafx-pass-parameters-while-instantiating-controller-class
         */
        /* ***************************
        Method by instantiating the controller manually

        FXMLLoader loader = new FXMLLoader(getClass().getResource(screen1File));
        LogInController logControl = new LogInController();
        loader.setController(logControl);

        ViewsController mainContainer = new ViewsController();

        mainContainer.loadScreen(MainView.screen1ID, MainView.screen1File);
        mainContainer.setScreen(screen1ID);

        MainView.primaryStage = primaryStage;
        Group root = new Group();
        root = loader.load();
        root.getChildren().addAll(mainContainer);
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();

        *************************  */




        /* *************************
        Method by fetching the controller instance and setting values
        */

        //ViewsController mainContainer = new ViewsController();


        //mainContainer.loadScreen(MainView.screen1ID, MainView.screen1File);
        //mainContainer.setScreen(screen1ID);


        FXMLLoader loader = new FXMLLoader(getClass().getResource(screen1File));
        GridPane grid = loader.load();

        LogInController controller = loader.getController();
        controller.setUsername("Quentin");
        controller.setPrevStage(primaryStage);

        //controller.setListener(new LazyCommunicationControllerListener() );

        //MainView.primaryStage = primaryStage;

        //Group root = new Group();
        //root.getChildren().addAll(mainContainer);
        Scene scene = new Scene(grid);
        primaryStage.setScene(scene);
        primaryStage.show();

        /*
        *************************** */


        /*
        Old Method


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

        */
    }

    public static void main(String[] args) {
        launch(args);
    }

    public static Stage getMainStage(){
        return primaryStage;
    }
}