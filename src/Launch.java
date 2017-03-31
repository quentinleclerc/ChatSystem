import controller.MulticastController;
import view.CommunicationView;

public class Launch {


    public static void main(String[] args) throws Exception {

        // Avoid the join error when using on Mac
        System.setProperty("java.net.preferIPv4Stack", "true");


        Thread tController = new Thread(new MulticastController());
        tController.start();

        CommunicationView.main(null);



    }


}
