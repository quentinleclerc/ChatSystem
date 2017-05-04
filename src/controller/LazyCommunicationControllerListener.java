package controller;


import model.Message;
import model.User;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class LazyCommunicationControllerListener implements CommunicationControllerListener {

    /*
    TODO : create two different classes for sending and receinving
    TODO : correctly kill the receiver on disconnect, otherwise there is a bind error if the same port is use
     */


    @Override
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
    
    @Override
    public Message receiveMessage(User localUser){
        Message msg = null;
        try {
            ServerSocket socket = new ServerSocket(localUser.getPort());
            Socket socketDist = socket.accept();
            ObjectInputStream ois = new ObjectInputStream(socketDist.getInputStream());

            msg = (Message) ois.readObject();
            socket.close();
            socketDist.close();
            ois.close();
        } catch (IOException e) {
            System.out.println("Error in message reception at the socket creation...");
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            System.out.println("Error in message reception...");
            e.printStackTrace();
        }
        return msg;
    }


}
