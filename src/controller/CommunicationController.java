package controller;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
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
	TextArea filDiscussion;
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
		send.setDisable(true);
		initModel();
	}

	@Override
	public void setScreenParent(ViewsController screenParent) {
		setMyController(screenParent);
	}

	public void initModel() {

		listView.setItems(model.getObsUsersList());
		listView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
			if (newSelection != null) {
				send.setDisable(false);
				recipientField.setText(newSelection);
				System.out.println(newSelection);
				User recipient = UserList.getInstance().getUserByUsername(newSelection);
				
				MessageQueue discussion = UserDiscussionLink.getInstance().getUserMessageQueue(recipient);
				System.out.println(discussion.toString());
				filDiscussion.setText(discussion.toString());
			}
		});
	}

	public ViewsController getMyController() {
		return myController;
	}

	public void setMyController(ViewsController myController) {
		this.myController = myController;
	}

	@FXML
	void onDisconnect(ActionEvent event) throws IOException {
		Stage stage = new Stage();
		stage.setTitle("Login View");
		GridPane myPane = null;
		FXMLLoader loader = new FXMLLoader(getClass().getResource(MainView.LoginViewFXML));
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

	private String getSelectedRecipient() {
		return listView.getSelectionModel().getSelectedItem();
	}

	@FXML
	void onSend(ActionEvent event) {
		String selectedRecipient = getSelectedRecipient();
		User recipient = UserList.getInstance().getUserByUsername(selectedRecipient);
		User emetteur = UserList.getInstance().getLocalUser();
		String message = messageToSend.getText();
		
		MessageQueue discussion = UserDiscussionLink.getInstance().getUserMessageQueue(recipient);
		discussion.addMessage(message, emetteur);
		filDiscussion.setText(discussion.toString());
		listener.sendMessage(message, selectedRecipient);
		messageToSend.clear();
	}


	public void setListener(CommunicationControllerListener listener) {
		System.out.println("Listener correctly set");
		this.listener = listener;
	}

	public void enableReception() {
		User localUser = UserList.getInstance().getLocalUser();
		while(true){
			Message msgReceived = listener.receiveMessage(localUser);
			
			UserDiscussionLink udl = UserDiscussionLink.getInstance();
			MessageQueue discussion = udl.getUserMessageQueue(msgReceived.getEmetteur());

			System.out.println("UDL : " + udl);
			System.out.println("Emetteur : "+ msgReceived.getEmetteur());
			System.out.println("Discussion : " + discussion);
			System.out.println("Message : "+ msgReceived.getData());
			discussion.addMessage(msgReceived);
			filDiscussion.setText(discussion.toString());
		}
	}

}
