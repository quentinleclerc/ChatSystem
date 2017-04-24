package network;

import javafx.application.Platform;
import model.MessageUser;
import model.UserList;

import java.io.*;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.SocketException;
import java.nio.channels.InterruptibleChannel;
import java.util.Observable;
import java.util.Observer;

public class MulticastReceiver implements Runnable, Observer, InterruptibleChannel {

    // multicast group adress where the message is received
    private InetAddress INET_ADDR;
    // port to receive
    private int PORT;
    // MultiCast socket used to receive message
    private MulticastSocket socket;
    // List of received users
    private UserList users;


    public MulticastReceiver(String adr, int port) throws IOException {
        this.INET_ADDR = InetAddress.getByName(adr);
        this.PORT = port;
        this.socket = new MulticastSocket(port);
    }

    public int getPort() {
        return PORT;
    }

    public void setPort(int port) {
        PORT = port;
    }

    public void run() {
        MessageUser userReceived;

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
                System.out.println("Interrupted exception caught in receiver");
                Thread.currentThread().interrupt();
                break;
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    private void treatUser(MessageUser userReceived)  {
        users = UserList.getInstance();

        MessageUser.typeConnect userState = userReceived.getEtat();

        if (userState == MessageUser.typeConnect.CONNECTED) {
            if (users.contains(userReceived)) {
                System.out.println("User already in connected users list");
            }
            else {

                Platform.runLater(() -> {
                    users.add(userReceived);
                });


                System.out.println("User "+ userReceived + " added to connected users list");
            }
        }
        else {
            int indexUser;
            if (users.contains(userReceived)) {
                indexUser = users.indexOf(userReceived);

                Platform.runLater(() -> {
                    users.remove(indexUser);
                }) ;

                System.out.println("User "+ userReceived + "successfully removed from connected users list");
            }
            else {
                System.out.println("User not removed because it is not in connected users list");
            }
        }
    }

    private MessageUser recvUser() throws Exception {
        MessageUser userReceived = null;

        byte[] recvBuf = new byte[5000];
        DatagramPacket packet = new DatagramPacket(recvBuf, recvBuf.length);

        socket.receive(packet);

        // int byteCount = packet.getLength();
        ByteArrayInputStream byteStream = new ByteArrayInputStream(recvBuf);
        ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(byteStream));

        userReceived = (MessageUser) ois.readObject();

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
