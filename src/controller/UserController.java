package controller;


import view.CommunicationView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UserController implements ActionListener {
    private UserController controller ;
    private CommunicationView view ;

    public UserController(UserController cont, CommunicationView vi) {
        controller = cont;
        view = vi;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
