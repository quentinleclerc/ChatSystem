package controller;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import java.io.IOException;
import java.net.InetAddress;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.User;
import model.UserList;
import view.MainView;

public class LogInController implements Initializable, ControlledScreen {

	private ViewsController myController;
	@FXML
	private Text actiontarget;
	@FXML
	private PasswordField passwordField;
	@FXML
	private TextField username;
	@FXML
	private TextField port;
	@FXML
	private Button signIn;

	private Stage prevStage;


	public LogInController() {
		System.out.println("LogIn Controller initialized.");
	}

	public void setPrevStage(Stage stage){
		this.prevStage = stage;
	}

	@Override
	public void initialize(URL location, ResourceBundle rb) {
		actiontarget.setText("");
		signIn.disableProperty().bind(
				username.textProperty().isEmpty().or(
						passwordField.textProperty().isEmpty().or(
								port.textProperty().isEmpty()
								)));
	}

	@Override
	public void setScreenParent(ViewsController screenParent){
		myController = screenParent;
	}

	@FXML
	void onSignIn(ActionEvent event) throws IOException {
		//myController.setScreen(MainView.screen2ID);
		Stage stage = new Stage();
		stage.setTitle("Communication View");
		GridPane myPane;
		FXMLLoader loader = new FXMLLoader(getClass().getResource(MainView.CommunicationViewFXML));
		myPane = loader.load();
		Scene scene = new Scene(myPane);
		stage.setScene(scene);

		// Get communication controller to set the prevStage
		CommunicationController controller = loader.getController();
		controller.setPrevStage(stage);
		controller.setListener(new LazyCommunicationControllerListener() );

		User localUser = new User(username.getText(), InetAddress.getByName("127.0.0.1"), Integer.parseInt(port.getText()), User.typeConnect.CONNECTED);
		UserList.getInstance().setLocalUser(localUser);

		(new Thread(){
			public void run() {
				controller.enableReception();
			}
		}).start();

		prevStage.close();
		stage.show();
		MulticastController.startAll(username.getText());
	}

	public void setUsername(String username){
		this.username.setText(username);
	}

	public String getUsername(){
		return this.username.getText();
	}
	
	public ViewsController getController(){
		return myController;
	}

}

