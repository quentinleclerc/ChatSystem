package control;

import java.io.*;
import java.net.*;
import static java.lang.Thread.sleep;

public class MulticastServerThread implements Runnable {

    private int SLEEP_TIME;

    private InetAddress INET_ADDR;

    private int PORT;


    public MulticastServerThread(String adr, int port, int time) throws IOException {
        this.SLEEP_TIME = time;
        this.INET_ADDR = InetAddress.getByName(adr);
        this.PORT = port;
    }

    public void run() {
        try {
            DatagramSocket socket = new DatagramSocket();
            System.out.println("MulticastServer socket running at : "+ socket.getLocalSocketAddress());


            while (true) {
                byte[] msg = "Hello".getBytes();

                DatagramPacket packet = new DatagramPacket(msg, msg.length, INET_ADDR, PORT);

                socket.send(packet);
                System.out.println("Sent a message to group "+ INET_ADDR + " : " + msg );

                System.out.println("Sleep for : "+ SLEEP_TIME + "ms" );
                sleep(SLEEP_TIME);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }


    }
}
