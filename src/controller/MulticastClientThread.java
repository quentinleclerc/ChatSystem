package controller;

import model.MessageUser;
import model.UserList;

import java.io.*;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class MulticastClientThread implements Runnable {

    // multicast group adress where the message is received
    private InetAddress INET_ADDR;
    // port to receive
    private int PORT;
    // MultiCast socket used to receive message
    private MulticastSocket socket;
    // List of received users
    private UserList users;


    public MulticastClientThread(String adr, int port) throws IOException {
        this.INET_ADDR = InetAddress.getByName(adr);
        this.PORT = port;
        this.socket = new MulticastSocket(port);
    }

    public void run() {
        MessageUser userReceived;

        System.out.println("MulticastClient socket running at : "+ socket.getLocalSocketAddress());

        try {
            socket.joinGroup(INET_ADDR);
            System.out.println("MulticastClient joined group : " + INET_ADDR);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        while(true) {
            userReceived = recvUser();
            System.out.println("User received : " + userReceived);
        }

    }

    private MessageUser recvUser() {
        MessageUser UserReceived = null;
        try {
            byte[] recvBuf = new byte[5000];
            DatagramPacket packet = new DatagramPacket(recvBuf, recvBuf.length);

            socket.receive(packet);

            // int byteCount = packet.getLength();
            ByteArrayInputStream byteStream = new ByteArrayInputStream(recvBuf);
            ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(byteStream));

            UserReceived = (MessageUser) ois.readObject();

            ois.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return UserReceived;
    }

}
