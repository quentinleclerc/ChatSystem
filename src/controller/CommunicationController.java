package controller;

import javafx.application.Platform;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.*;
import view.MainView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CommunicationController implements Initializable {

	@FXML
	ListView<User> listViewUser;
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


	protected UserList model;

	private User localUser;

	private MainView mainView;

	private MulticastController multiControl;

	public CommunicationController() {
		System.out.println("Communication Controller initialized.");
	}

	public void setPrevStage(Stage stage){
		this.prevStage = stage;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		send.setDisable(true);
	}


	private void initModel() {
		listViewUser.setItems(model.getObsUsersList());
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

	@FXML
	void onDisconnect(ActionEvent event) throws IOException {
		this.mainView.showLoginView(this.prevStage, false, multiControl);
		this.model.remove(localUser);
    }

	private User getSelectedRecipient() {
		return listViewUser.getSelectionModel().getSelectedItem();
	}

	@FXML
	void onSend(ActionEvent event) {
		String message = messageToSend.getText();
		User selectedRecipient = getSelectedRecipient();
		Platform.runLater(() -> discussion.appendText("\n" + message));
		listener.sendMessage(message, selectedRecipient);
	}


	public void setListener(CommunicationControllerListener listener) {
		System.out.println("Listener correctly set");
		this.listener = listener;
	}

	public void enableReception() {
		while(true){
			Message msgReceived = listener.receiveMessage(this.localUser);
			String msgText = msgReceived.getData();
			Platform.runLater(() -> discussion.appendText("\n" + msgText));
		}
	}

	public void setLocalUser(User localUser) {
		this.localUser = localUser;
	}

	public void setMainView(MainView mainView) {
		this.mainView = mainView;
	}

	public void setModel(UserList model) {
		this.model = model;
		initModel();
	}

	public void setMultiControl(MulticastController multiControl) {
		this.multiControl = multiControl;
	}
}
