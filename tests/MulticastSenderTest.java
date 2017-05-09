import static org.junit.Assert.*;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import network.MulticastSender;

import org.junit.Test;

import communication.User;

public class MulticastSenderTest {
	
	@Test
	public void testSendDisconnect() {
		MulticastSender mS = null;
		User usr = mock(User.class);
		try {
			mS = new MulticastSender("225.1.2.3", 6789, 5000, usr);
		} catch (IOException e) {
			e.printStackTrace();
		}
		mS.sendDisconnect();
		assertTrue("User Disconnexion", usr.getEtat().equals(User.typeConnect.DECONNECTED));
	}
	

	private User mock(Class<User> class1) {
		User usr = null;
		try {
			usr = new User("Alex", InetAddress.getByName("127.0.0.1"), 5789, User.typeConnect.CONNECTED);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		return usr;
	}

}
