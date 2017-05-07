package network;

import communication.*;
import java.io.*;
import java.net.*;
import static java.lang.Thread.sleep;


public class MulticastSender implements Runnable {

    // in ms, time between each message sent by the server
    private int SLEEP_TIME;
    // multicast group adress where the message is sent
    private InetAddress INET_ADDR;
    // port where the message is sent
    private int PORT;
    // local datagram socket use to send
    private DatagramSocket socket;
    // user to be sent
    private User myUser;


    public MulticastSender(String adr, int port, int time, User user) throws IOException {
        this.SLEEP_TIME = time;
        this.INET_ADDR = InetAddress.getByName(adr);
        this.PORT = port;
        this.socket = new DatagramSocket();
        this.myUser = user;
    }

    public void run() {
        System.out.println("MulticastSender socket running..");
        sendPeriodic();
    }


    private void sendPeriodic() {
        System.out.println("MulticastSender sending periodic model every " + this.SLEEP_TIME/1000 + " seconds");
        while (true) {
            sendUnique();
            System.out.println("Sleep for : "+ SLEEP_TIME + "ms" );
            try {
                sleep(SLEEP_TIME);
            }
            catch (InterruptedException e) {
                System.out.println("Interrupted exception caught in sender");
                sendDisconnect();
                //Thread.currentThread().interrupt();
                break;
            }
        }
    }

    private void sendUnique() {
        try {
            ByteArrayOutputStream byteStream = new ByteArrayOutputStream(5000);
            ObjectOutputStream oos = new ObjectOutputStream(new BufferedOutputStream(byteStream));

            oos.flush();
            oos.writeObject(myUser);
            oos.flush();

            byte[] sendBuf = byteStream.toByteArray();
            DatagramPacket packet = new DatagramPacket(sendBuf, sendBuf.length, INET_ADDR, PORT);

            socket.send(packet);
            System.out.println("Sent a message to group "+ INET_ADDR + " : " + myUser);

            oos.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void sendDisconnect() {
        myUser.setEtat(User.typeConnect.DECONNECTED);
        sendUnique();
    }


}
