package network;


import communication.*;

import model.UserDiscussionLink;

import java.io.*;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.SocketException;

import java.nio.channels.InterruptibleChannel;

import javafx.application.Platform;
import javafx.collections.ObservableList;

public class MulticastReceiver implements Runnable, InterruptibleChannel {

    private UserDiscussionLink userDiscLink;
    // multicast group adress where the message is received
    private InetAddress INET_ADDR;
    // MultiCast socket used to receive message
    private MulticastSocket socket;
    // List of received users
    private ObservableList<User> users;


    public MulticastReceiver(String adr, int port, ObservableList<User> userList, UserDiscussionLink userDiscLink) throws IOException {
        this.INET_ADDR = InetAddress.getByName(adr);
        this.socket = new MulticastSocket(port);
        this.users = userList;
        this.userDiscLink = userDiscLink;
        System.out.println("MulticastReceiver started");
    }

    public void run() {
        User userReceived;

        System.out.println("MulticastReceiver socket running..");

        try {
            socket.joinGroup(INET_ADDR);
            System.out.println("MulticastReceiver joined group : " + INET_ADDR);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        while(true) {
            try {
                userReceived = recvUser();
                treatUser(userReceived);
            }
            catch (SocketException se) {
                System.out.println("Socket exception caught in receiver");
                Thread.currentThread().interrupt();
                break;
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void treatUser(User userReceived)  {
        User.typeConnect userState = userReceived.getEtat();

        if (userState == User.typeConnect.CONNECTED) {
            if (users.contains(userReceived)) {
                int indexUserReceived = users.indexOf(userReceived);

            	if (!userReceived.getStatut().equals(users.get(indexUserReceived).getStatut()) ) {
                    System.out.println("User already in connected users list but his pseudo has changed");
                    users.set(indexUserReceived, userReceived);
                } else {
                	System.out.println("User already in connected users list");
            	}
            	
            } else {
                Platform.runLater(() -> users.add(userReceived));
                this.userDiscLink.addDiscussion(userReceived);
                System.out.println("User "+ userReceived.getPseudo() + " added to connected users list");
            }
        }
        else {
            if (users.contains(userReceived)) {
                Platform.runLater(() ->
                    users.remove(userReceived)
                ) ;
                System.out.println("User "+ userReceived + "successfully removed from connected users list");
            }
            else {
                System.out.println("User not removed because it is not in connected users list");
            }
        }
    }

    private User recvUser() throws Exception {
        byte[] recvBuf = new byte[5000];
        DatagramPacket packet = new DatagramPacket(recvBuf, recvBuf.length);
        socket.receive(packet);

        ByteArrayInputStream byteStream = new ByteArrayInputStream(recvBuf);
        ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(byteStream));

        User userReceived = (User) ois.readObject();

        ois.close();

        return userReceived;
    }

    @Override
    public boolean isOpen() {
        return !socket.isClosed();
    }

    @Override
    public void close() throws IOException {
        socket.close();
    }
}
