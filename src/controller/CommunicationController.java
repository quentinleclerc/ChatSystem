package controller;

import model.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;

import java.net.URL;
import java.util.ResourceBundle;

public class CommunicationController implements Initializable, ControlledScreen{
	 
    private ViewsController myController;
    private UserList model = UserList.getInstance() ;
    @FXML
    ListView<MessageUser> listView ;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
    	Thread lThread = new Thread(this.fillList());
        lThread.start();
    }
    
    @Override
    public void setScreenParent(ViewsController screenParent) {
        setMyController(screenParent);
    }

	public ViewsController getMyController() {
		return myController;
	}

	public void setMyController(ViewsController myController) {
		this.myController = myController;
	}
	
	public Runnable fillList(){
		/**
		 * Try to populate the listView
		 * http://stackoverflow.com/questions/36657299/how-can-i-populate-a-listview-in-javafx-using-custom-objects*/
		return null;
	}

	public UserList getModel() {
		return model;
	}

	public void setModel(UserList model) {
		this.model = model;
	}
}
