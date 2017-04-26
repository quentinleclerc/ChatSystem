package controller;

import model.Message;
import model.User;

public interface CommunicationControllerListener {

    public void sendMessage(String message, String selectedRecipient);
    public Message receiveMessage(User me);

}
