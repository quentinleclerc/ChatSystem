package network;

import communication.Message;
import communication.User;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class MessageSender {

    public void sendMessage(Message msg, User selectedRecipient) {
        try {
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
