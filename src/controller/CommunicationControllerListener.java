package controller;

import model.Message;
import model.User;

 interface CommunicationControllerListener {

    void sendMessage(String message, User selectedRecipient);
    Message receiveMessage(User me);

}
