package controller;


import model.Message;
import model.User;
import model.UserList;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class LazyCommunicationControllerListener implements CommunicationControllerListener {

    @Override
    public void sendMessage(String message, String selectedRecipient) {
        System.out.println(message + " to " + selectedRecipient);
        UserList list = UserList.getInstance();
        User recipient = list.getUserByUsername(selectedRecipient);
        Message msg = new Message(message);



        try {
            System.out.println(recipient.getIP().toString() + recipient.getPort());
            Socket socket = new Socket(recipient.getIP(), recipient.getPort());

            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());

            oos.writeObject(msg);
            oos.flush();
            oos.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }


    }


}
