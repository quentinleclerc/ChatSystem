package network;

import model.MessageUser;
import java.io.*;
import java.net.*;
import static java.lang.Thread.sleep;


public class MulticastServerThread implements Runnable {

    // in ms, time between each message sent by the server
    private int SLEEP_TIME;
    // multicast group adress where the message is sent
    private InetAddress INET_ADDR;
    // port where the message is sent
    private int PORT;
    // local datagram socket use to send
    private DatagramSocket socket;
    // user to be sent
    private MessageUser myUser;

    public MulticastServerThread(String adr, int port, int time, MessageUser user) throws IOException {
        this.SLEEP_TIME = time;
        this.INET_ADDR = InetAddress.getByName(adr);
        this.PORT = port;
        this.socket = new DatagramSocket();
        this.myUser = user;
    }

    public void run() {
        System.out.println("MulticastServer socket running..");
        sendPeriodic(this.SLEEP_TIME);
    }


    private void sendPeriodic(int period) {
        System.out.println("MulticastServer sending periodic model every " + this.SLEEP_TIME/1000 + " seconds");
        while (true) {
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

                System.out.println("Sleep for : "+ SLEEP_TIME + "ms" );
                sleep(SLEEP_TIME);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


}
