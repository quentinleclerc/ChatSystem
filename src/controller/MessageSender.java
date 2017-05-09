package controller;

import communication.Message;
import communication.User;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class MessageSender {

    public void sendMessage(Message msg, User selectedRecipient) {
        System.out.println(msg + " to " + selectedRecipient);

        try {
            System.out.println(selectedRecipient.getIP().toString() + selectedRecipient.getPort());
            Socket socket = new Socket(selectedRecipient.getIP(), selectedRecipient.getPort());

            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());

            oos.writeObject(msg);
            oos.flush();
            oos.close();
            socket.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

}
