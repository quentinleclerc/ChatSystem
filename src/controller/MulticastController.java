package controller;

import model.MessageUser;
import network.MulticastClientThread;
import network.MulticastServerThread;

import java.net.InetAddress;

public class MulticastController implements Runnable {

    //
    private static final String ADR = "228.5.6.7";
    //
    private static final int PORT = 6789;
    //
    private static final int SLEEP_TIME = 5000;

    public MulticastController() {
        super();
        System.out.println("MulticastController started..");
    }

    public static String getAdr() {
		return ADR;
	}

	public void run() {
        MessageUser myUser = null;
        try {
            myUser = new MessageUser("Quentin", InetAddress.getByName("127.0.0.1"), 8080, MessageUser.typeConnect.CONNECTED);

            System.out.println("Main controller creating a thread for MulticastServer...");
            Thread mcsThread = new Thread(new MulticastServerThread("225.1.2.3", PORT, SLEEP_TIME, myUser));
            mcsThread.start();

            System.out.println("Main controller creating a thread for MulticastClient...");
            Thread mccThread = new Thread(new MulticastClientThread("225.1.2.3", PORT));
            mccThread.start();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

}
