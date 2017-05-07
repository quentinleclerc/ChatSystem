package controller;

import javafx.application.Platform;

import javafx.collections.ObservableList;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

import javafx.scene.control.*;

import javafx.stage.Stage;
import model.*;
import view.MainView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

import communication.*;

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
	TextField status;
	@FXML
	TextArea filDiscussion;

	private MessageSender sender;

	private Stage prevStage;

	protected ObservableList<User> model;

	private User localUser;

	private MainView mainView;

	private MulticastController multiControl;

	private UserDiscussionLink userDiscLink;

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
		listViewUser.setItems(model);
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

				Discussion discussion = this.userDiscLink.getUserMessageQueue(getSelectedRecipient());
				System.out.println(discussion.toString());

				Platform.runLater(() -> filDiscussion.setText(discussion.toString()));
			}
		});
	}

	@FXML
	public void onDisconnect(ActionEvent event) {
		this.model.clear();
		this.mainView.showLoginView(this.prevStage, false, multiControl);
	}

	private User getSelectedRecipient() {
		return listViewUser.getSelectionModel().getSelectedItem();
	}

	private void onSend(){
		User selectedRecipient = getSelectedRecipient();
		Message msg = new Message(messageToSend.getText(), localUser);

		sender.sendMessage(msg, selectedRecipient);
		messageToSend.clear();
		updateDiscussion(msg, selectedRecipient);
	}

	@FXML
	public void onSendClicked(MouseEvent event) {
		this.onSend();
	}

	@FXML
	public void onSendEnter(KeyEvent ke) {
		if(ke.getCode().equals(KeyCode.ENTER) && getSelectedRecipient() != null){
			this.onSend();
			listViewUser.requestFocus();
		}
	}

	@FXML
	public void onStatusClicked(MouseEvent event){
		status.setText("");
	}

	@FXML
	public void onStatusEnter(KeyEvent ke){
		if(ke.getCode().equals(KeyCode.ENTER)){
			localUser.setStatut(status.getText());
			listViewUser.requestFocus();
		}
	}

	public void updateDiscussion(Message msg, User recipient) {
		Discussion discussion = this.userDiscLink.getUserMessageQueue(recipient);
		discussion.addMessage(msg);
		Platform.runLater(() -> filDiscussion.setText(discussion.toString()));

	}

	public void setLocalUser(User localUser) {
		this.localUser = localUser;
	}

	public void setMainView(MainView mainView) {
		this.mainView = mainView;
	}

	public void setModel(ObservableList<User> model) {
		this.model = model;
		initModel();
	}

	public void setMultiControl(MulticastController multiControl) {
		this.multiControl = multiControl;
	}

	public void setUserDiscussionLink(UserDiscussionLink userDiscussionLink) {
		this.userDiscLink = userDiscussionLink;
	}

	public void setSender(MessageSender sender) {
		this.sender = sender;
	}
}
