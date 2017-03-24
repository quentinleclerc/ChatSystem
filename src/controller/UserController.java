package controller;


import view.CommunicationView;

import java.awt.event.ActionListener;

public class UserController {
    private UserController controller ;
    private CommunicationView view ;

    public UserController(UserController cont, CommunicationView vi) {
        controller = cont;
        view = vi;
    }

}
