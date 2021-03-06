package controller;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;


import model.*;

import network.MessageSender;
import view.ConversationView;
import view.MainApp;
import view.BubbleText;

import java.net.URL;
import java.util.ResourceBundle;

import communication.*;

public class CommunicationController implements Initializable {
	@FXML
	Text welcomeField;
	@FXML
	Text localInfo;
	@FXML
	ListView<User> listViewUser;
	@FXML
	Button disconnect;
	@FXML
	Button send;
	@FXML
	TextField messageToSend;
	@FXML
	Text recipientField;
	@FXML
	TextField status;
	@FXML
	Button update;
	@FXML
	VBox vboxDiscussion;

	private MessageSender sender;

	private Stage prevStage;

	protected ObservableList<User> model;

	private User localUser;

	private MainApp mainApp;

	private MulticastController multiControl;

	private UserDiscussionLink userDiscLink;

	public CommunicationController() {
		System.out.println("Communication Controller initialized.");
	}

	public void setLocalUser(User localUser) {
		this.localUser = localUser;
		localInfo.setText(localUser.getPseudo() + ". " + localUser.getStatut());
	}

	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
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

	public void setPrevStage(Stage stage){
		this.prevStage = stage;
	}

	private User getSelectedRecipient() {
		return listViewUser.getSelectionModel().getSelectedItem();
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
				recipientField.setText(newSelection.getPseudo() + ". " + newSelection.getStatut());

				ConversationView discussion = this.userDiscLink.getUserMessageQueue(newSelection);

				Platform.runLater(() -> {
					vboxDiscussion.getChildren().clear();
					vboxDiscussion.getChildren().add(discussion);
				});
			}
		});
	}

	@FXML
	public void onDisconnect(ActionEvent event) {
		this.model.clear();
		this.mainApp.showLoginView(this.prevStage, false, multiControl);
	}

	@FXML
	public void onSendClicked(MouseEvent event) {
		this.onSend();
	}

	@FXML
	public void onSendEnter(KeyEvent ke) {
		if(ke.getCode().equals(KeyCode.ENTER) && getSelectedRecipient() != null){
			this.onSend();
		}
	}

	private void onSend(){
		User selectedRecipient = getSelectedRecipient();
		Message message = new Message(messageToSend.getText(), localUser);

		ConversationView discussion = this.userDiscLink.getUserMessageQueue(selectedRecipient);
		Platform.runLater(() -> discussion.addMessage(message, BubbleText.SpeechDirection.RIGHT));

		sender.sendMessage(message, selectedRecipient);
		messageToSend.clear();
	}

	@FXML
	public void onStatusClicked(MouseEvent event){
		status.setText(localUser.getStatut());
		status.positionCaret(localUser.getStatut().length());
	}

	@FXML
	public void onStatusEnter(KeyEvent ke){
		if(ke.getCode().equals(KeyCode.ENTER)){
			localUser.setStatut(status.getText());
			localInfo.setText(localUser.getPseudo() + ". " + localUser.getStatut());
		}
	}

	@FXML
	public void onUpdateClicked(MouseEvent event) {
		localUser.setStatut(status.getText());
		localInfo.setText(localUser.getPseudo() + ". " + localUser.getStatut());
	}

	public void updateDiscussion(Message messageReceived) {
		ConversationView conversationView = this.userDiscLink.getUserMessageQueue(messageReceived.getSender());
		Platform.runLater(() -> conversationView.addMessage(messageReceived, BubbleText.SpeechDirection.LEFT));
	}
}
