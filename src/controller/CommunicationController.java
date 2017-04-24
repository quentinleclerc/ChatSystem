package controller;

import javafx.scene.input.MouseEvent;
import model.*;
import view.MainView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

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
	void onDisconnect(ActionEvent event){
        myController.setScreen(MainView.screen1ID);
		MulticastController.stopAll();
    }

	@FXML
	void onMouseClicked(MouseEvent event) {
		System.out.println(listView.getSelectionModel().getSelectedItem());
	}

}
