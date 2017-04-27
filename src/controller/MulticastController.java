package controller;

import model.User;
import model.UserList;
import network.MulticastReceiver;
import network.MulticastSender;

import java.io.IOException;

public class MulticastController {

    // Adress used for the multicast communication
    private static final String ADR = "225.1.2.3";
    // Port used for the multicast communication
    private static final int PORT = 6789;
    // Sleep time between each message send on the channel
    private static final int SLEEP_TIME = 5000;
    // Instance of MulticastReceiver
    private static MulticastReceiver mReceiver;
    // Instance of MulticastSender
    private static MulticastSender mSender;

    // Thread used to start and stop the emission/reception of data on the channel
    private static Thread multiSenderThread;
    private static Thread multiReceiverThread;


    public MulticastController() {
        System.out.println("MulticastController initialized.");
    }

    public static void startAll(String user) {
        User localUser = UserList.getInstance().getLocalUser();
        try {

            System.out.println("Main controller creating a thread for MulticastServer...");

            multiSenderThread = new Thread(setmSender(new MulticastSender(ADR, PORT, SLEEP_TIME, localUser)));
            multiSenderThread.start();

            System.out.println("Main controller creating a thread for MulticastClient...");
            multiReceiverThread = new Thread(mReceiver = new MulticastReceiver(ADR, PORT));
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

	public static MulticastSender getmSender() {
		return mSender;
	}

	public static MulticastSender setmSender(MulticastSender mSender) {
		MulticastController.mSender = mSender;
		return mSender;
	}

}
