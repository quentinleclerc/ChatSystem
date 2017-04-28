package controller;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.geometry.NodeOrientation;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import model.*;
import view.MainView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

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

	@FXML
	ListView<User> listViewUser;


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
			}
		});

		listViewUser.setItems(model.getObsUsersListList());
		listViewUser.setCellFactory((list) -> new ListCell<User>() {
            @Override
            protected void updateItem(User item, boolean empty) {
                super.updateItem(item, empty);

                if (item == null || empty) {
                    setText(null);
                } else {
                    setText(item.getPseudo() + " ["+ item.getIP() +":" +item.getPort()+ "]");
                }
            }
        });
		listViewUser.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
			if(newSelection != null) {
				send.setDisable(false);
				recipientField.setText(newSelection.getPseudo());
				System.out.println(newSelection);
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
		String message = messageToSend.getText();
		String selectedRecipient = getSelectedRecipient();
		Platform.runLater(() -> discussion.appendText("\n" + message));
		listener.sendMessage(message, selectedRecipient);
	}


	public void setListener(CommunicationControllerListener listener) {
		System.out.println("Listener correctly set");
		this.listener = listener;
	}

	public void enableReception() {
		User localUser = UserList.getInstance().getLocalUser();
		while(true){
			Message msgReceived = listener.receiveMessage(localUser);
			String msgText = msgReceived.getData();
			Platform.runLater(() -> discussion.appendText("\n" + msgText));
		}
	}

}
