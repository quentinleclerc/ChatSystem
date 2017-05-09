package network;


import communication.*;

import model.UserDiscussionLink;

import java.io.*;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.SocketException;

import java.nio.channels.InterruptibleChannel;

import java.util.Observable;
import java.util.Observer;

import javafx.application.Platform;
import javafx.collections.ObservableList;

public class MulticastReceiver implements Runnable, Observer, InterruptibleChannel {

    private UserDiscussionLink userDiscLink;
    // multicast group adress where the message is received
    private InetAddress INET_ADDR;
    // port to receive
    //private int PORT;
    // MultiCast socket used to receive message
    private MulticastSocket socket;
    // List of received users
    private ObservableList<User> users;


    public MulticastReceiver(String adr, int port, ObservableList<User> userList, UserDiscussionLink userDiscLink) throws IOException {
        this.INET_ADDR = InetAddress.getByName(adr);
        // this.PORT = port;
        this.socket = new MulticastSocket(port);
        this.users = userList;
        this.userDiscLink = userDiscLink;
        System.out.println("MulticastReceiver started, users = "+ users);
    }

    public void run() {
        User userReceived;

        System.out.println("MulticastReceiver socket running at : "+ socket.getLocalSocketAddress());

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
                System.out.println("User received : " + userReceived);
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
                User userInList = users.get(indexUserReceived);

            	if (!userReceived.getStatut().equals(users.get(indexUserReceived).getStatut()) ) {
                    System.out.println("User already in connected users list BUT his pseudo has changed");
            		System.out.println("NEW STATUT " + userReceived.getStatut() + " vs OLD STATUT" + userInList.getStatut());
                    users.set(indexUserReceived, userReceived);
                    // users.replace(userInList, userReceived);
                } else {
                	System.out.println("User already in connected users list");
            	}
            	
            } else {
                Platform.runLater(() -> users.add(userReceived));
                this.userDiscLink.addDiscussion(userReceived);
                System.out.println("User "+ userReceived.getPseudo() + " added to connected users list");
                System.out.println("Creation of a discussion for the user " + userReceived.getPseudo());
            }
        }
        else {
            if (users.contains(userReceived)) {
                Platform.runLater(() -> {
                    users.remove(userReceived);
                }) ;

                System.out.println("User "+ userReceived + "successfully removed from connected users list");
            }
            else {
                System.out.println("User not removed because it is not in connected users list");
            }
        }
    }

    private User recvUser() throws Exception {
        User userReceived = null;

        byte[] recvBuf = new byte[5000];
        DatagramPacket packet = new DatagramPacket(recvBuf, recvBuf.length);

        socket.receive(packet);

        // int byteCount = packet.getLength();
        ByteArrayInputStream byteStream = new ByteArrayInputStream(recvBuf);
        ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(byteStream));

        userReceived = (User) ois.readObject();

        System.out.println("[DEBUGG] USER RECEIVED : " + userReceived);
        // Adding the received user to the UserList
        treatUser(userReceived);

        ois.close();

        return userReceived;
    }

    public MulticastSocket getSocket(){
        return socket;
    }

    @Override
    public void update(Observable o, Object arg) {
        System.out.println("User added");
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
