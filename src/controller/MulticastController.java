package controller;

import communication.*;

import model.UserDiscussionLink;
import network.MulticastReceiver;
import network.MulticastSender;

import javafx.collections.ObservableList;

import java.io.IOException;

public class MulticastController {

    // Adress used for the multicast communication
    private static final String ADR = "225.1.2.3";
    // Port used for the multicast communication
    private static final int PORT = 5002;
    // Sleep time between each message send on the channel
    private static final int SLEEP_TIME = 3000;

    // Instance of MulticastReceiver used to interrupt the thread
    private MulticastReceiver mReceiver;
    // Instance of MulticastSender thread used to interrupt it
    private Thread multiSenderThread;

    private ObservableList<User> model;

    private UserDiscussionLink userDiscLink;


    public MulticastController(ObservableList<User> model) {
        this.model = model;
        System.out.println("MulticastController initialized.");
    }

    public void setUserDiscLink(UserDiscussionLink userDiscLink) {
        this.userDiscLink = userDiscLink;
    }

    public void startAll(User localUser) {
        try {
            multiSenderThread = new Thread(new MulticastSender(ADR, PORT, SLEEP_TIME, localUser));
            multiSenderThread.start();

            mReceiver = new MulticastReceiver(ADR, PORT, model, userDiscLink);
            Thread multiReceiverThread = new Thread(mReceiver);
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
        }
        multiSenderThread.interrupt();
    }
}
