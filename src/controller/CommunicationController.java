package controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import model.*;
import view.MainView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CommunicationController implements Initializable, ControlledScreen {

	private ViewsController myController;

	protected UserList model;

	@FXML
	ListView<String> listView;
	@FXML
	Button disconnect;
	@FXML
	Button send;
	@FXML
	TextField messageToSend;
	@FXML
	TextField recipientField;
	@FXML
	TextArea discussion;
	private CommunicationControllerListener listener;
	private Stage prevStage;


	public CommunicationController() {
		System.out.println("Communication Controller initialized.");
	}

	public void setPrevStage(Stage stage){
		this.prevStage = stage;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		model = UserList.getInstance();
		initModel();
	}

	@Override
	public void setScreenParent(ViewsController screenParent) {
		setMyController(screenParent);
	}
	
	public void initModel() {

        listView.setItems(model.getObsUsersList());
        listView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> 
            model.setCurrentUser(newSelection));

        model.currentUserProperty().addListener((obs, oldUser, newUser) -> {
            if (newUser == null) {
                listView.getSelectionModel().clearSelection();
            } else {
                listView.getSelectionModel().select(newUser);
            }
        });
    }

	public ViewsController getMyController() {
		return myController;
	}

	public void setMyController(ViewsController myController) {
		this.myController = myController;
	}

	public UserList getModel() {
		return model;
	}

	public void setModel(UserList model) {
		this.model = model;
	}



	@FXML
	void onDisconnect(ActionEvent event) throws IOException {
        //myController.setScreen(MainView.screen1ID);
		Stage stage = new Stage();
		stage.setTitle("Login View");
		GridPane myPane = null;
		FXMLLoader loader = new FXMLLoader(getClass().getResource(MainView.screen1File));
		myPane = loader.load();
		Scene scene = new Scene(myPane);
		stage.setScene(scene);

		// Get LogInController to set prevStage
		LogInController controller = loader.getController();
		controller.setPrevStage(stage);
		/* ***************************************** */

		prevStage.close();

		stage.show();

		MulticastController.stopAll();
    }

	@FXML
	void onMouseClicked(MouseEvent event) {
		System.out.println(getSelectedRecipient());
	}

	private String getSelectedRecipient() {
		return listView.getSelectionModel().getSelectedItem();
	}

	@FXML
	void onSend(ActionEvent event) {
		String message = messageToSend.getText();
		String selectedRecipient = getSelectedRecipient();
		listener.sendMessage(message, selectedRecipient);
	}


	public void setListener(CommunicationControllerListener listener) {
		System.out.println("Listener correctly set");
		this.listener = listener;
	}
}
