package control;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import static java.lang.Thread.sleep;

public class MulticastClientThread implements Runnable {

    private InetAddress INET_ADDR;
    private int PORT;


    public MulticastClientThread(String adr, int port) throws IOException {
        this.INET_ADDR = InetAddress.getByName(adr);
        this.PORT = port;
    }

    public void run() {

        try {

            MulticastSocket socket = new MulticastSocket(PORT);
            System.out.println("MulticastClient socket running at : "+ socket.getLocalSocketAddress());
            socket.joinGroup(INET_ADDR);
            System.out.println("MulticastClient joined group : " + INET_ADDR);

            DatagramPacket packet;

            while(true) {
                byte[] bytes = new byte[1024];
                packet = new DatagramPacket(bytes, bytes.length);

                System.out.println("MulticastClient waiting for a message...");
                socket.receive(packet);

                String msg = new String(packet.getData(), packet.getOffset(), packet.getLength());
                System.out.println("MulticastClient received : "+ msg);
            }

        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

}
