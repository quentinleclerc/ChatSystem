package controller;


public class LazyCommunicationControllerListener implements CommunicationControllerListener {

    @Override
    public void sendMessage(String message, String selectedRecipient) {
        System.out.println(message + " to " + selectedRecipient);
    }
}
