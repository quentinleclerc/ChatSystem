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
    private MulticastReceiver mReceiver;
    // Instance of MulticastSender
    private MulticastSender mSender;
    // Thread used to start and stop the emission/reception of data on the channel
    private Thread multiSenderThread;
    private Thread multiReceiverThread;

    private UserList model;


    public MulticastController(UserList model) {
        this.model = model;
        System.out.println("MulticastController initialized.");
    }

    public void startAll(User localUser) {
        try {
            System.out.println("Main controller creating a thread for MulticastServer...");


            multiSenderThread = new Thread(mSender = new MulticastSender(ADR, PORT, SLEEP_TIME, localUser));
            multiSenderThread.start();

            System.out.println("Main controller creating a thread for MulticastClient...");
            multiReceiverThread = new Thread(mReceiver = new MulticastReceiver(ADR, PORT, model));

            multiReceiverThread.start();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void stopAll() {
        try {
            mReceiver.close();
        } catch (IOException e) {
            e.printStackTrace();
            multiReceiverThread.interrupt();

        }
        multiSenderThread.interrupt();
    }

	public static MulticastSender setmSender(MulticastSender mSender) {
		MulticastController.mSender = mSender;
		return mSender;
	}

}
