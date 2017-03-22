package control;


public class Controller {

    private static final String ADR = "228.5.6.7";

    private static final int PORT = 6789;

    private static final int SLEEP_TIME = 5000;

    public static void main(String[] args) throws Exception {

        System.out.println("Main controller creating a thread for MulticastServer...");
        Thread mcsThread = new Thread(new MulticastServerThread("225.1.2.3", PORT, SLEEP_TIME));
        mcsThread.start();


        System.out.println("Main controller creating a thread for MulticastClient...");
        Thread mccThread = new Thread(new MulticastClientThread("225.1.2.3", PORT));
        mccThread.start();
    }

}
