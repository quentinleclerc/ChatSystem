package controller;


import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class CommunicationController implements Initializable, ControlledScreen{

    private ViewsController myController;

    @Override
    public void setScreenParent(ViewsController screenParent) {
        myController = screenParent;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
