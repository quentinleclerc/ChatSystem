package controller;

import model.Message;
import model.User;

 interface CommunicationControllerListener {

    void sendMessage(Message message, User selectedRecipient);
    Message receiveMessage(User me);

}
