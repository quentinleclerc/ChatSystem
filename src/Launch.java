import controller.CommunicationController;
import controller.LogInController;
import controller.MulticastController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import sun.applet.Main;
import view.MainView;

public class Launch {


    public static void main(String[] args) throws Exception {

        // Avoid the join error when using on Mac
        System.setProperty("java.net.preferIPv4Stack", "true");


        MulticastController multiController = new MulticastController();

        (new Thread(){
            public void run(){
                MainView.launch((MainView.class));
            }
        }).start();

    }


}
