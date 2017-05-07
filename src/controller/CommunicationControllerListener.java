package controller;

import communication.*;

 interface CommunicationControllerListener {

    void sendMessage(Message message, User selectedRecipient);
    Message receiveMessage(User me);

}
