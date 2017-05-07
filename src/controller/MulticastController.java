package controller;

import communication.*;
import model.UserDiscussionLink;
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
    // Threads used to start and stop the emission/reception of data on the channel
    private Thread multiSenderThread;
    private Thread multiReceiverThread;

    private UserList model;

    private UserDiscussionLink userDiscLink;


    public MulticastController(UserList model) {
        this.model = model;
        System.out.println("MulticastController initialized.");
    }

    public void startAll(User localUser) {
        try {
            System.out.println("Main controller creating a thread for MulticastServer...");

            multiSenderThread = new Thread(new MulticastSender(ADR, PORT, SLEEP_TIME, localUser));
            multiSenderThread.start();

            System.out.println("Main controller creating a thread for MulticastClient...");
            multiReceiverThread = new Thread(mReceiver = new MulticastReceiver(ADR, PORT, model, userDiscLink));

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

    public void setUserDiscLink(UserDiscussionLink userDiscLink) {
        this.userDiscLink = userDiscLink;
    }
}
