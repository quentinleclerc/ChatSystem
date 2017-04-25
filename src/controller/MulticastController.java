package controller;

import model.User;
import network.MulticastReceiver;
import network.MulticastSender;

import java.io.IOException;
import java.net.InetAddress;

import static java.lang.Thread.sleep;

public class MulticastController {

    // Adress used for the multicast communication
    private static final String ADR = "228.5.6.7";
    // Port used for the multicast communication
    private static final int PORT = 6789;
    // Sleep time between each message send on the channel
    private static final int SLEEP_TIME = 5000;
    // Username entered by the user
    private static String username;
    // Instance of MulticastReceiver
    private static MulticastReceiver mReceiver ;
    // Instance of MulticastSender
    private static MulticastSender mSender ;

    // Thread used to start and stop the emission/reception of data on the channel
    private static Thread multiSenderThread;
    private static Thread multiReceiverThread;


    public MulticastController() {
        System.out.println("MulticastController initialized.");
    }

    public static String getAdr() {
		return ADR;
	}

    public static void startAll(String user) {
        username = user;
        User myUser = null;
        try {
            myUser = new User(username, InetAddress.getByName("127.0.0.1"), 8080, User.typeConnect.CONNECTED);

            System.out.println("Main controller creating a thread for MulticastServer...");

            multiSenderThread = new Thread(mSender = new MulticastSender("225.1.2.3", PORT, SLEEP_TIME, myUser));
            multiSenderThread.start();

            System.out.println("Main controller creating a thread for MulticastClient...");
            multiReceiverThread = new Thread(mReceiver = new MulticastReceiver("225.1.2.3", PORT));
            multiReceiverThread.start();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void stopAll() {
        try {
            mReceiver.close();
        } catch (IOException e) {
            e.printStackTrace();
            multiReceiverThread.interrupt();

        }
        multiSenderThread.interrupt();
    }

}
