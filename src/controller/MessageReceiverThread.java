package controller;


import communication.Message;
import communication.User;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.nio.channels.InterruptibleChannel;

public class MessageReceiverThread implements Runnable, InterruptibleChannel {

    private User localUser;
    private ServerSocket socket;
    private CommunicationController comController;

    public MessageReceiverThread(User localUser) {
        this.localUser = localUser;
    }

    @Override
    public void run() {
        while(true) {
            Message receivedMessage = null;
            try {
                receivedMessage = receiveMessage(localUser);
            } catch (SocketException se) {
                System.out.println("Socket exception caught in MessageReceiver");
                Thread.currentThread().interrupt();
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (receivedMessage != null) {
                comController.updateDiscussion(receivedMessage, receivedMessage.getSender());
            }
        }
    }

    public Message receiveMessage(User localUser) throws IOException {
        Message msg = null;

        try {
            socket = new ServerSocket(localUser.getPort());
            socket.setReuseAddress(true);
        } catch (IOException e) {
            e.printStackTrace();
        }


        Socket socketDist = socket.accept();

        try {
            ObjectInputStream ois = new ObjectInputStream(socketDist.getInputStream());

            msg = (Message) ois.readObject();
            socket.close();
            socketDist.close();
            ois.close();
        }  catch (IOException e) {
            System.out.println("Error in message reception at the socket creation...");
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            System.out.println("Error in message reception...");
            e.printStackTrace();
        }

        return msg;
    }

    @Override
    public boolean isOpen() {
        return !socket.isClosed();
    }

    @Override
    public void close() throws IOException {
        socket.close();
        System.out.println("Socket closed");
    }

    public void setComController(CommunicationController comController) {
        this.comController = comController;
    }
}
